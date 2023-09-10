# 로스트아크 정보제공 플랫폼 Server
로스트아크에서 제공하는 open api를 사용해 개인화된 정보를 제공함과 동시에 일정관리를 돕는 앱 서버입니다.<br>
유저는 파편화된 게임의 정보를 직업 찾아다니지 않고 앱으로서 손쉽게 게임의 컨텐츠를 즐길 수 있습니다.

깃허브에서 제공하는 issue, project, actions 기능을 적극 이용하였으며 해당 레포지토리의 issue 페이지와 organization의 project 페이지에서 내용 확인이 가능합니다.

## 사용기술
### 애플리케이션
- Kotlin, Springboot3, Kotest, Mockk
### 인프라
- Mysql8, Redis, Flyway, Docker, Github Actions, Aws
## 프로젝트 진행시 관심사
- 모든 작업은 issue task를 통해 진행하며 협업에 집중합니다.
- 브랜치 커버리지를 80% 이상으로 유지합니다.(추후 100%도전)
- 한 메서드의 라인수가 15줄을 넘기지 않습니다.
- else문을 지양합니다.
- 지속적으로 리팩터링 합니다.

## CI/CD
Github Actions를 통해 pr이 올라오면 통합테스트를 진행합니다.<br>
이 때 Jacoco를 통해 커버리지에 의한 빌드 성공/실패를 결정합니다.<br>
빌드가 성공한다면 Github Actions를 통해 빌드된 결과물을 서버에 배포합니다.(중단배포로 먼저 설정, 이 후 nginx를 통한 무중단 배포 설정)

## 시작하기
```shell
# 로컬 컨테이너 세팅
cd docker
docker-compose up -d
```

## http 사용해보기
1. intellij 접속 후 http 폴더로 이동
2. 테스트해보고 싶은 http 실행


