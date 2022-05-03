CREATE SCHEMA CA2;
use CA2;
create table if not exists roles
(
    role_name varchar
(
    20
) not null
    primary key
    );

create table if not exists users
(
    user_name varchar
(
    25
) not null
    primary key,
    user_pass varchar
(
    255
) null
    );

create table if not exists user_roles
(
    role_name varchar
(
    20
) not null,
    user_name varchar
(
    25
) not null,
    primary key
(
    role_name,
    user_name
),
    constraint FK_user_roles_role_name
    foreign key
(
    role_name
) references roles
(
    role_name
),
    constraint FK_user_roles_user_name
    foreign key
(
    user_name
) references users
(
    user_name
)
    );

