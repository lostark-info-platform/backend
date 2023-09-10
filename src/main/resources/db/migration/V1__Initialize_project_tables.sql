create table user
(
    id       bigint       not null auto_increment,
    name     varchar(30)  not null,
    email    varchar(255) not null,
    password varchar(255) not null,
    primary key (id)
) engine=InnoDB;


alter table user
    add constraint UK_6iduje2h6ggdlnmevw2mvolfx unique (email);