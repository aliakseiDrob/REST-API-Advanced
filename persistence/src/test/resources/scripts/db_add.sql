DROP TABLE IF EXISTS gift_certificate;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS gift_certificate_tag;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS orders;

CREATE TABLE  gift_certificate
(
    `id`               BIGINT        NOT NULL AUTO_INCREMENT,
    `name`             VARCHAR(50)   NOT NULL,
    `description`      VARCHAR(200)  NOT NULL,
    `price`            DECIMAL(6, 2) NOT NULL,
    `duration`         INT           NOT NULL,
    `is_available`        INT not null,
    `create_date`      timestamp,
    `last_update_date` timestamp,
    primary key (id)
);

CREATE TABLE  tag
(
    `id`   BIGINT      NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) unique NOT NULL CHECK (name <> ''),
    primary key (id)
);

CREATE TABLE  gift_certificate_tag
(
    `gift_certificate_id` BIGINT,
    `tag_id`              BIGINT,
    primary key (`gift_certificate_id`, `tag_id`),
    FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate (id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE CASCADE
) ;
CREATE TABLE  user
(
    `id`   BIGINT      NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) unique not null,
    primary key (id)
);
