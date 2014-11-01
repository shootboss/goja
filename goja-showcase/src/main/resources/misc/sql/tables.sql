CREATE TABLE users (
  id          INT AUTO_INCREMENT PRIMARY KEY    NOT NULL,
  name        VARCHAR(20)                       NULL,
  password    VARCHAR(64)                       NULL,
  salt        CHAR(16)                          NULL,
  nickname    VARCHAR(60)                       NULL,
  gender      SMALLINT DEFAULT 0                NOT NULL,
  address     NVARCHAR2(1200)                   NULL,
  phone       VARCHAR(26)                       NULL,
  birthday    DATE                              NULL,
  create_time DATETIME                          NULL
)