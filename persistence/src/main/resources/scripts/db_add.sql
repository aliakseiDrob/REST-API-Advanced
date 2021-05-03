CREATE SCHEMA `db_certificates` DEFAULT CHARACTER SET utf8;

CREATE TABLE IF NOT EXISTS `db_certificates`.`gift_certificate`
(
    `id`               BIGINT        NOT NULL AUTO_INCREMENT,
    `name`             VARCHAR(50)   NOT NULL,
    `description`      VARCHAR(200)  NOT NULL,
    `price`            DECIMAL(6, 2) NOT NULL,
    `duration`         INT           NOT NULL,
    `create_date`      timestamp,
    `last_update_date` timestamp,
    primary key (id)
)
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `db_certificates`.`tag`
(
    `id`   BIGINT      NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(50) unique NOT NULL,
    primary key (id)
)
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `db_certificates`.`gift_certificate_tag`
(
    `gift_certificate_id` BIGINT,
    `tag_id`              BIGINT,
    primary key (`gift_certificate_id`, `tag_id`),
    FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate (id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE CASCADE
) ENGINE = InnoDB;
CREATE TABLE IF NOT EXISTS `db_certificates`.`user`
(
    `id`   BIGINT      NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) unique not null,
    primary key (id))
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `db_certificates`.`order`
(
    `id`   BIGINT      NOT NULL AUTO_INCREMENT,
    `date` timestamp NOT NULL,
    `order_cost` DECIMAL(6,2) NOT NULL,
    `user_id` BIGINT,
    `certificate_id` bigint,
    primary key (id),
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (certificate_id) references gift_certificate (id)
)
    ENGINE = InnoDB;
