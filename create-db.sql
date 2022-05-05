create table tour.tour (id  serial not null, estimated_time time, goal varchar(255) not null, name varchar(255) not null, route_information varchar(255), start varchar(255) not null, tour_description varchar(255), tour_distance int8 not null, transport_type int4 not null, primary key (id));
create table tour.tour_log (id int8 not null, comment varchar(255), date_time timestamp, difficulty int4 not null, rating float8 not null, total_time time, primary key (id));
