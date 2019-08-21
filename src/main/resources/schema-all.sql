DROP TABLE IF EXISTS log;

CREATE TABLE log (
  id          int(11) NOT NULL AUTO_INCREMENT,
  access_date datetime NOT NULL,
  access_ip   varchar(20) NOT NULL,
  request_type varchar(20) NOT NULL,
  status      varchar(20) NOT NULL,
  user_agent  varchar(450) NOT NULL,
  PRIMARY KEY (id)
);
