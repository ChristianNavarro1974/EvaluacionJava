create table USER_DATA
(
    ID         INTEGER auto_increment,
    NAME       CHARACTER VARYING(255) not null,
    PASSWORD   CHARACTER VARYING(255) not null,
    EMAIL      CHARACTER VARYING(255) not null
        unique,
    LAST_LOGIN TIMESTAMP              not null,
    MODIFIED   TIMESTAMP,
    CREATED    TIMESTAMP,
    TOKEN      UUID,
    ISACTIVE   BOOLEAN,
    constraint USER_PK
        primary key (ID)
);

create table USER_PHONE
(
    ID         INTEGER auto_increment,
    NUMBER     CHARACTER VARYING(255) not null,
    CITYCODE   CHARACTER VARYING(255) not null,
    CONTRYCODE CHARACTER VARYING(255) not null,
    USER_ID    INTEGER                not null,
    constraint PHONE_PK
        primary key (ID),
    constraint USER_FK
        foreign key (USER_ID) references USER_DATA
);