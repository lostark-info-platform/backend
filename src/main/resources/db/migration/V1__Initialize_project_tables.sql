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
    provider_user_id varchar(255) not null,
    provider         varchar(50)  not null,
    email            varchar(255) not null,
    nickname         varchar(255) not null,
    created_at       datetime,
    updated_at       datetime,
    primary key (id)
) engine=InnoDB;

create table schedule
(
    id         bigint       not null auto_increment,
    user_id    bigint       not null,
    title      varchar(255) not null,
    category   varchar(255) not null,
    state      varchar(50)  not null,
    memo       varchar(255),
    start_date datetime     not null,
    end_date   datetime     not null,
    alarm_date datetime,
    created_at datetime,
    updated_at datetime,
    primary key (id)
) engine=InnoDB;

alter table user
    add constraint UK_6iduje2h6ggdlnmevw2mvolfx unique (email);
