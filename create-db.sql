create table tour.tour (id uuid not null, estimated_time time, favorite boolean default false not null, goal varchar(255) not null, name varchar(255) not null, route_information varchar(255), start varchar(255) not null, tour_description varchar(255), tour_distance float8 not null, transport_type int4 not null, primary key (id));
create table tour.tour_log (id uuid not null, comment varchar(255), date_time timestamp not null, difficulty int4 not null, rating float8 not null, total_time time not null, tour_id uuid not null, primary key (id));
alter table tour.tour_log add constraint FKt11l2e3v3crdm88yscgb15ipq foreign key (tour_id) references tour.tour;
create table tour.tour (id uuid not null, estimated_time time, favorite boolean default false not null, goal varchar(255) not null, name varchar(255) not null, route_image_name varchar(255), route_information varchar(255), start varchar(255) not null, tour_description varchar(255), tour_distance int8 not null, transport_type int4 not null, primary key (id));
create table tour.tour_log (id uuid not null, comment varchar(255), date_time timestamp, difficulty int4, rating float8, total_time time, tour_id uuid, primary key (id));
alter table tour.tour_log add constraint FKt11l2e3v3crdm88yscgb15ipq foreign key (tour_id) references tour.tour;
create table tour.tour (id uuid not null, estimated_time time, favorite boolean default false not null, goal varchar(255) not null, name varchar(255) not null, route_image_name varchar(255), route_information varchar(255), start varchar(255) not null, tour_description varchar(255), tour_distance int8 not null, transport_type int4 not null, primary key (id));
create table tour.tour_log (id uuid not null, comment varchar(255), date_time timestamp, difficulty int4, rating float8, total_time time, tour_id uuid, primary key (id));
alter table tour.tour_log add constraint FKt11l2e3v3crdm88yscgb15ipq foreign key (tour_id) references tour.tour;