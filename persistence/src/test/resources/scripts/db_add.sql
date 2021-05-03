CREATE TABLE IF NOT EXISTS tag (
             `id`   BIGINT      NOT NULL AUTO_INCREMENT,
            `name` VARCHAR(50) UNIQUE NOT NULL,
            primary key (id));
CREATE TABLE IF NOT EXISTS gift_certificate (
            id  BIGINT NOT NULL AUTO_INCREMENT,
            name VARCHAR(255)  NOT NULL ,
            description VARCHAR(250)  ,
            price  DECIMAL(6, 2) NOT NULL,
            duration       INT           NOT NULL,
            create_date      timestamp,
            last_update_date timestamp,
        primary key (id));
CREATE TABLE IF NOT EXISTS gift_certificate_tag (
            tag_id BIGINT,
            gift_certificate_id BIGINT,
            PRIMARY KEY (tag_id, gift_certificate_id),
            CONSTRAINT FK_tag_id FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE CASCADE ON UPDATE CASCADE,
            CONSTRAINT FK_gift_certificate_id FOREIGN KEY (gift_certificate_id)
            REFERENCES gift_certificate (id) ON DELETE CASCADE ON UPDATE CASCADE);