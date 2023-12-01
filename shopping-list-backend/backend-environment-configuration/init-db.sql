create table "user"
(
    name     varchar(32)  not null,
    surname  varchar(32)  not null,
    number   varchar(9)   not null
        unique,
    id       uuid         not null
        constraint user_pk
            primary key,
    email    varchar(64)  not null
        unique,
    password varchar(255) not null,
    role     varchar(255) not null
        constraint check_name
            check ((role)::text = ANY (ARRAY [('USER'::character varying)::text, ('ADMIN'::character varying)::text]))
);

create table organization
(
    id   uuid        not null
        constraint organization_pk
            primary key,
    name varchar(64) not null
);

create table shopping_list
(
    id              uuid        not null
        constraint shopping_list_pk
            primary key,
    name            varchar(64) not null,
    organization_id uuid        not null
        constraint shopping_list_organization_uuid_fk
            references organization
);

create table bill
(
    id               uuid           not null
        constraint bill_pk
            primary key,
    amount           numeric(10, 2) not null,
    shop             varchar(64),
    comment          varchar(255),
    shopping_list_id uuid           not null
        constraint bill_shopping_list_id_fk
            references shopping_list,
    owner_id         uuid           not null
        constraint bill_user_id_fk
            references "user",
    update_time      timestamp      not null
);

create table product
(
    id               uuid        not null
        constraint product_pk
            primary key,
    name             varchar(64) not null,
    quantity         integer     not null,
    shopping_list_id uuid        not null,
    bill_id          uuid
        constraint product_bill_id_fk
            references bill,
    purchased        boolean     not null
);

create table user_organization
(
    user_id         uuid         not null
        constraint user_organization_user_uuid_fk
            references "user",
    status          varchar(255) not null
        constraint status_check
            check ((status)::text = ANY
                   (ARRAY [('OWNER'::character varying)::text, ('ADMIN'::character varying)::text, ('USER'::character varying)::text])),
    organization_id uuid         not null
        constraint user_organization_organization_uuid_fk
            references organization,
    id              uuid         not null
        constraint user_organization_pk
            primary key,
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


