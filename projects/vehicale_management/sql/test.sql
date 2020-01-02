DROP TABLE IF EXISTS car;
DROP TABLE IF EXISTS route;
DROP TABLE IF EXISTS carRoute;
DROP TABLE IF EXISTS carStatus;

CREATE TABLE car(
  id serial NOT NULL PRIMARY KEY,
  name varchar(255) NOT NULL UNIQUE,
  pwd varchar(255) NOT NULL,
  projectName varchar(255),
  capacity SMALLINT CHECK (capacity < 10 AND capacity > -1),
  createTime timestamp DEFAULT CURRENT_TIMESTAMP,
  updateTime timestamp DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO car(id,name,pwd,projectName,capacity) VALUES
( 1, '260', '260', 'park', 15),
( 2, 'test', 'test', 'test', 16),
( 3, 'itd', 'hmi', 'test', 17),
( 4, 'hmi', 'hmi', 'test', 18);


CREATE TABLE route( id serial PRIMARY key, name varchar(50) );
SELECT AddGeometryColumn ('route', 'route_line', 4326, 'linestring', 2);
SELECT AddGeometryColumn ('route', 'route_point', 4326, 'multipoint', 2);
SELECT AddGeometryColumn ('route', 'start_point', 4326, 'point', 2);
SELECT AddGeometryColumn ('route', 'end_point', 4326, 'point', 2);


CREATE TABLE carRoute(
  carId integer PRIMARY KEY REFERENCES car (id),
  routeId integer[]
);
INSERT INTO carRoute VALUES
( 1, '{1,2,3}'),
( 2, '{2,4}'),
( 3, '{2,3}');


CREATE TABLE carStatus(
  id serial PRIMARY key REFERENCES car (id),
  driveStatus SMALLINT,
  obstacleAvoid SMALLINT,
  locationStatus SMALLINT,
  gpsStatus SMALLINT,
  actuatorFailure SMALLINT,
  sensorFailure SMALLINT,
);
SELECT AddGeometryColumn ('carStatus', 'location', 4326, 'point', 2);

INSERT INTO carStatus () VALUES
( 1, 2, 3, 4, 5, 6, 7, ST_GeomFromText('point( '31.33087383286171' '121.54824680034356' )',4326) );

