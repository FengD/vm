DROP DATABASE IF EXISTS itd_hmi;
CREATE DATABASE itd_hmi;

DROP TABLE IF EXISTS car;
CREATE TABLE car (
  id serial NOT NULL,
  name varchar(255) NOT NULL UNIQUE,
  pwd varchar(255) NOT NULL,
  projectName varchar(255),
  createTime timestamp DEFAULT CURRENT_TIMESTAMP,
  updateTime timestamp DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO car (name, pwd, projectName)
VALUES ('260', '260', 'park');

INSERT INTO car (name, pwd, projectName)
VALUES ('test', 'test', 'test');

INSERT INTO car (name, pwd, projectName)
VALUES ('itd', 'hmi', 'test');
