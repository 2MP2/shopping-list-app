create table "user"
(
    name          varchar   not null,
    surname       varchar   not null,
    number        varchar   not null
        unique,
    id            uuid      not null
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
    id      uuid    not null
        constraint bill_pk
            primary key,
    amount  integer not null,
    shop    varchar,
    comment varchar
);

create table organization
(
    id            uuid      not null
        constraint organization_pk
            primary key,
    name          varchar   not null,
    creation_date timestamp not null,
    delete_date   timestamp,
    owner_id      uuid      not null
        constraint organization_user_uuid_fk
            references "user" (uuid)
);

create table shopping_list
(
    id              uuid      not null
        constraint shopping_list_pk
            primary key,
    name            varchar   not null,
    creation_date   timestamp not null,
    delete_date     timestamp,
    organization_id uuid      not null
        constraint shopping_list_organization_uuid_fk
            references organization (uuid)
);

create table product
(
    id               uuid    not null
        constraint product_pk
            primary key,
    name             varchar not null,
    quantity         integer not null,
    status           varchar,
    shopping_list_id uuid    not null,
    bill_id          uuid
);

create table user_organization
(
    user_id         uuid not null
        constraint user_organization_user_uuid_fk
            references "user" (uuid),
    status          varchar,
    organization_id uuid not null
        constraint user_organization_organization_uuid_fk
            references organization (uuid),
    constraint user_organization_pk
        primary key (user_id, organization_id)
);
