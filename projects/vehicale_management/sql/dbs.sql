-- namespace VM (vehicale management)
DROP TABLE IF EXISTS vm_route;
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
  project_id integer REFERENCES dictionary_project(project_id),
  name varchar(50),
  delta_x float DEFAULT 0,
  delta_y float DEFAULT 0,
  reference_height integer DEFAULT 0,
  create_time timestamp DEFAULT CURRENT_TIMESTAMP,
  update_time timestamp DEFAULT CURRENT_TIMESTAMP
);
SELECT AddGeometryColumn ('vm_route', 'route_line', 4326, 'linestring', 2);
SELECT AddGeometryColumn ('vm_route', 'route_point', 4326, 'multipoint', 2);
SELECT AddGeometryColumn ('vm_route', 'start_point', 4326, 'point', 2);
SELECT AddGeometryColumn ('vm_route', 'end_point', 4326, 'point', 2);
SELECT AddGeometryColumn ('vm_route', 'reference_point', 4326, 'point', 2);

CREATE TABLE vm_car(
  car_id serial NOT NULL PRIMARY KEY,
  name varchar(255) NOT NULL UNIQUE,
  type varchar(255) NOT NULL UNIQUE,
  pwd varchar(255) NOT NULL,
  project_id integer REFERENCES dictionary_project(project_id),
  city_id integer REFERENCES dictionary_city(city_id),
  capacity SMALLINT CHECK (capacity < 10 AND capacity > -1),
  photo_path varchar(255),
  create_time timestamp DEFAULT CURRENT_TIMESTAMP,
  update_time timestamp DEFAULT CURRENT_TIMESTAMP
);
