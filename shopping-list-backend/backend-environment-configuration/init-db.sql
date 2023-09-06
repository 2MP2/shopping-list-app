create table "user"
(
    name          varchar   not null,
    surname       varchar   not null,
    number        varchar   not null
        unique,
    uuid          uuid      not null
        constraint user_pk
            primary key,
    email         varchar
        unique,
    login         varchar
        unique,
    password      varchar   not null,
    creation_date timestamp not null,
    delete_date   timestamp
);

create table bill
(
    uuid    uuid    not null
        constraint bill_pk
            primary key,
    amount  integer not null,
    shop    varchar,
    comment varchar
);

create table organization
(
    uuid          uuid      not null
        constraint organization_pk
            primary key,
    name          varchar   not null,
    creation_date timestamp not null,
    delete_date   timestamp,
    owner_uuid    uuid      not null
        constraint organization_user_uuid_fk
            references "user"
);

create table shopping_list
(
    uuid              uuid      not null
        constraint shopping_list_pk
            primary key,
    name              varchar   not null,
    creation_date     timestamp not null,
    delete_date       timestamp,
    organization_uuid uuid      not null
        constraint shopping_list_organization_uuid_fk
            references organization
);

create table product
(
    uuid               uuid    not null
        constraint product_pk
            primary key,
    name               varchar not null,
    quantity           integer not null,
    status             varchar,
    shopping_list_uuid uuid    not null,
    bill_uuid          uuid
);

create table user_organization
(
    user_uuid         uuid not null
        constraint user_organization_user_uuid_fk
            references "user",
    status            varchar,
    organization_uuid uuid not null
        constraint user_organization_organization_uuid_fk
            references organization,
    constraint user_organization_pk
        primary key (user_uuid, organization_uuid)
);


