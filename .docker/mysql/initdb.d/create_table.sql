CREATE TABLE trip_style_entity
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    style_name VARCHAR(255),
    keyword1   VARCHAR(255),
    keyword2   VARCHAR(255),
    keyword3   VARCHAR(255)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;