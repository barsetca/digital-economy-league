DELETE
FROM prices;
DELETE
FROM products;



INSERT INTO products (name)
VALUES ('Product1'),
       ('Product2'),
       ('Product3');

INSERT INTO prices (price, date, product_id)
VALUES (0.01, '2020-11-13', 1),
       (0.144, '2020-11-15', 1),
       (1, '2020-11-19', 1),
       (99999.49, '2020-11-11', 2),
       (99999.99, '2020-11-14', 2),
       (100000.01, '2020-11-16', 2),
       (49.49, '2020-11-13', 3),
       (58.99, '2020-11-14', 3),
       (59.99, '2020-11-15', 3),
       (54.99, '2020-11-16', 3);



