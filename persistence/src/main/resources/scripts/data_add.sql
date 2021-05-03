INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
values ('first', 'for men', 100, 4, '2021-03-22 20:11:10', '2021-03-24 20:11:10'),
       ('second', 'for men', 100, 7, '2021-03-22 20:11:10', '2021-03-24 20:11:10'),
       ('third', 'for women', 230, 17, '2021-03-22 20:11:10', '2021-03-28 20:11:10'),
       ('first', 'for women', 340, 8, '2021-03-21 20:11:10', '2021-03-24 20:11:10'),
       ('fifth', 'for children', 25, 11, '2021-03-23 20:11:10', '2021-03-24 16:11:10'),
       ('sixth', 'for children', 50, 14, '2021-03-18 20:11:10', '2021-03-19 22:11:10');

INSERT INTO tag (name)
VALUES ('health'),
       ('games'),
       ('sport'),
       ('hobby'),
       ('reading'),
       ('quiz');

INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id)
VALUES (1, 1),
       (1, 4),
       (2, 6),
       (2, 1),
       (3, 3),
       (3, 4),
       (4, 1),
       (4, 5),
       (5, 2),
       (5, 5),
       (6, 6),
       (6, 3);

INSERT INTO user (name)
VALUES ('IVAN'),
       ('Petr'),
       ('Stepan'),
       ('Fedor'),
       ('Mike');

INSERT INTO `order` (date,order_cost,user_id)
VALUES ('2021-03-24 20:11:10',340,1),
       ('2021-03-30 20:11:10',100,1),
       ('2021-03-24 20:11:10',100,1),
       ('2021-03-29 20:11:10',230,1),
       ('2021-03-24 20:11:10',340,2),
       ('2021-03-24 20:11:10',25,3);

INSERT INTO gift_certificate_order (gift_certificate_id,order_id)
VALUES(4,1),
      (1,2),
      (2,3),
      (3,4),
      (4,5),
      (5,6);