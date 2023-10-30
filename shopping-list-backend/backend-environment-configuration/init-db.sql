create table "user"
(
    name     varchar not null,
    surname  varchar not null,
    number   varchar not null
        unique,
    id       uuid    not null
        constraint user_pk
            primary key,
    email    varchar not null
        unique,
    password varchar not null,
    deleted  boolean not null,
    role     varchar not null
        constraint check_name
            check ((role)::text = ANY (ARRAY [('USER'::character varying)::text, ('ADMIN'::character varying)::text]))
);

create table organization
(
    id      uuid    not null
        constraint organization_pk
            primary key,
    name    varchar not null,
    deleted boolean not null
);

create table shopping_list
(
    id              uuid    not null
        constraint shopping_list_pk
            primary key,
    name            varchar not null,
    organization_id uuid    not null
        constraint shopping_list_organization_uuid_fk
            references organization
);

create table bill
(
    id               uuid    not null
        constraint bill_pk
            primary key,
    amount           integer not null,
    shop             varchar,
    comment          varchar,
    shopping_list_id uuid    not null
        constraint bill_shopping_list_id_fk
            references shopping_list,
    owner_id         uuid    not null
        constraint bill_user_id_fk
            references "user"
);

create table product
(
    id               uuid    not null
        constraint product_pk
            primary key,
    name             varchar not null,
    quantity         integer not null,
    status           varchar not null
        constraint status_check
            check ((status)::text = ANY
                   ((ARRAY ['UNPURCHASED'::character varying, 'PURCHASED'::character varying])::text[])),
    shopping_list_id uuid    not null,
    bill_id          uuid
        constraint product_bill_id_fk
            references bill
);

create table user_organization
(
    user_id         uuid    not null
        constraint user_organization_user_uuid_fk
            references "user",
    status          varchar not null
        constraint status_check
            check ((status)::text = ANY
                   ((ARRAY ['OWNER'::character varying, 'ADMIN'::character varying, 'USER'::character varying])::text[])),
    organization_id uuid    not null
        constraint user_organization_organization_uuid_fk
            references organization,
    id              uuid    not null
        constraint user_organization_pk
            primary key,
    deleted         boolean,
    constraint user_organization_pk2
        unique (status, organization_id, user_id)
);

create table invitation
(
    id              uuid not null
        constraint invitation_pk
            primary key,
    organization_id uuid not null
        constraint invitation_organization_id_fk
            references organization,
    user_id         uuid not null
        constraint invitation_user_id_fk
            references "user"
);


