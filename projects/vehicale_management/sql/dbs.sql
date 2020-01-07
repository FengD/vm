-- namespace VM (vehicale management)
DROP TABLE IF EXISTS vm_caradu;
DROP TABLE IF EXISTS vm_car_route_relation;
DROP TABLE IF EXISTS vm_car_status;
DROP TABLE IF EXISTS vm_route;
DROP TABLE IF EXISTS vm_car_profile;
DROP TABLE IF EXISTS vm_car;
DROP TABLE IF EXISTS dictionary_city;
DROP TABLE IF EXISTS dictionary_project;

CREATE TABLE dictionary_city(
  city_id serial NOT NULL PRIMARY KEY,
  name varchar(255),
  create_time timestamp DEFAULT CURRENT_TIMESTAMP,
  update_time timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE dictionary_project(
  project_id serial NOT NULL PRIMARY KEY,
  name varchar(255),
  create_time timestamp DEFAULT CURRENT_TIMESTAMP,
  update_time timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE vm_route(
  route_id serial NOT NULL PRIMARY KEY,
  name varchar(50),
  create_time timestamp DEFAULT CURRENT_TIMESTAMP,
  update_time timestamp DEFAULT CURRENT_TIMESTAMP
);
SELECT AddGeometryColumn ('vm_route', 'route_line', 4326, 'linestring', 2);
SELECT AddGeometryColumn ('vm_route', 'route_point', 4326, 'multipoint', 2);
SELECT AddGeometryColumn ('vm_route', 'start_point', 4326, 'point', 2);
SELECT AddGeometryColumn ('vm_route', 'end_point', 4326, 'point', 2);

CREATE TABLE vm_car(
  car_id serial NOT NULL PRIMARY KEY,
  name varchar(255) NOT NULL UNIQUE,
  pwd varchar(255) NOT NULL,
  create_time timestamp DEFAULT CURRENT_TIMESTAMP,
  update_time timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE vm_car_status(
  car_status_id serial NOT NULL PRIMARY KEY,
  car_id integer REFERENCES vm_car(car_id),
  is_login bool DEFAULT false,
  create_time timestamp DEFAULT CURRENT_TIMESTAMP,
  update_time timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE vm_car_profile(
  car_profile_id serial NOT NULL PRIMARY KEY,
  car_id integer REFERENCES vm_car(car_id),
  project_id integer REFERENCES dictionary_project(project_id),
  city_id integer REFERENCES dictionary_city(city_id),
  capacity SMALLINT CHECK (capacity < 10 AND capacity > -1),
  photo_path varchar(255),
  create_time timestamp DEFAULT CURRENT_TIMESTAMP,
  update_time timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE vm_car_route_relation(
  id serial NOT NULL PRIMARY KEY,
  car_id integer REFERENCES vm_car(car_id),
  route_id integer REFERENCES vm_route(route_id),
  create_time timestamp DEFAULT CURRENT_TIMESTAMP,
  update_time timestamp DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE vm_caradu(
  car_adu_id serial NOT NULL PRIMARY KEY,
  car_id integer REFERENCES vm_car(car_id),
  create_time timestamp DEFAULT CURRENT_TIMESTAMP,
  update_time timestamp DEFAULT CURRENT_TIMESTAMP
);
