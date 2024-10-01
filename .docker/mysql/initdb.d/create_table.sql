CREATE TABLE trip_style_entity
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    style_name VARCHAR(127),
    keyword1   VARCHAR(15),
    keyword2   VARCHAR(15),
    keyword3   VARCHAR(15)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;