create table user
(
    id         bigint       not null auto_increment,
    name       varchar(30)  not null,
    email      varchar(255) not null,
    password   varchar(255) not null,
    created_at datetime,
    updated_at datetime,
    primary key (id)
) engine=InnoDB;

create table oauth2user
(
    id               bigint       not null auto_increment,
    user_id          bigint       not null,
    provider_user_id bigint       not null,
    provider         varchar(50)  not null,
    email            varchar(255) not null,
    nickname         varchar(255) not null,
    created_at       datetime,
    updated_at       datetime,
    primary key (id)
) engine=InnoDB;

alter table user
    add constraint UK_6iduje2h6ggdlnmevw2mvolfx unique (email);
