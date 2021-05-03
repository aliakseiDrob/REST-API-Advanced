INSERT INTO TAG (name)
VALUES ('first'),
       ('second');
INSERT INTO gift_certificate
            (name, description, price, duration, create_date, last_update_date) VALUES
            ('first', 'for men', 128.01, 11,'2021-03-21 20:11:10','2021-03-24 20:11:10'),
            ('second', 'children', 250.20, 7,'2021-03-06 20:11:10','2021-03-11 20:11:10'),
            ('third', 'everybody', 48.50, 3,'2021-03-26 19:11:10','2021-03-28 20:11:10'),
            ('first', 'children', 48.50, 3,'2021-03-20 19:11:10','2021-03-28 20:11:10');
INSERT INTO gift_certificate_tag
    (tag_id, gift_certificate_id)
VALUES (1, 1),
       (1,2),
       (2,2),
       (1,4);