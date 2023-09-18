# CI/CD 문서
## 사용기술
Github Actions, Docker, EC2
## 동작 방식
1. 특정 브랜치에 푸쉬하면 Github Actions의 workflows가 동작합니다. [main-branch.yml](../.github/workflows/main-branch.yml)
2. Github Actions의 러너 환경에서 jdk와 gradle을 세팅합니다. 이 때 이전 workflows에서 캐싱한 gradle데이터를 사용합니다.
3. gradle wrapper를 사용해 러너 환경에서 빌드합니다.
4. 원하는 Dockerfile을 작성해 위에서 빌드한 jar파일을 이미지로 빌드 후 도커레포지토리로 푸쉬합니다. 
   1. 이 때 Dockerfile에 application의 profile도 함께 지정합니다. [Product.Dockerfile](../Product.Dockerfile)
5. Github Actions의 secret 파일로 관리중이던 application-product.properties 내용을 러너환경에 "application-product.properties"파일로 만듭니다. 
6. 5에서 만든 파일을 **서버의 특정 폴더**에 전송 합니다.
   1. 서버의 특정 폴더는 Docker가 마운트할 호스트의 볼륨입니다. [docker-compose.api.yml](../docker/docker-compose.api.yml)
   2. 추후 애플리케이션이 부트될 때 application-product.properties파일을 찾을 수 있게 env.properties를 정의합니다.
      ```properties
      spring.config.activate.on-profile=product
      spring.config.import=file:/root/app/secret/application-product.properties
      ```
7. 서버에서 docker-compose up 할 수 있게 yml 파일을 서버로 전송합니다.
8. 서버에서 실행할 쉘 파일을 서버로 전송합니다. [deploy.sh](../script/deploy.sh)
9. ssh를 이용해 도커레포지토리에 있는 이미지를 Pull 받습니다.
10. 8에서 전송한 쉘 파일을 전송합니다.
    1. blue, green의 컨테이너 상태를 확인합니다.
    2. 만약 두 개 모두 떠있지 않은 상태라면 blue 컨테이너를 올립니다.
    3. blue 컨테이너가 올라가 있다면 green 컨테이너를 올립니다.
    4. green 컨테이너를 healthcheck합니다.
    5. green 컨테이너가 올라왔다면 blue 컨테이너를 내립니다.
    6. nginx를 reload합니다.
    7. 반대로 green 컨테이너가 올라가 있다면 2~6 과정을 blue <-> green 대체하여 실행합니다. 