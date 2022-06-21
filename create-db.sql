create table tour.tour
(
    id                uuid                  not null,
    estimated_time    time,
    favorite          boolean default false not null,
    goal              varchar(255),
    name              varchar(255)          not null,
    route_image_name  varchar(255),
    route_information varchar(255),
    start             varchar(255),
    tour_description  varchar(255),
    tour_distance     float8  default 0     not null,
    transport_type    integer default 0     not null,
    primary key (id)
);
create table tour.tour_log
(
    id         uuid not null,
    comment    varchar(255),
    date_time  timestamp,
    difficulty int4,
    distance   float8,
    rating     float8,
    total_time time,
    tour_id    uuid,
    primary key (id)
);
alter table tour.tour_log
    add constraint FKt11l2e3v3crdm88yscgb15ipq foreign key (tour_id) references tour.tour on delete cascade;
