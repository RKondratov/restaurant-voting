INSERT INTO users (email, first_name, last_name, password, registered)
VALUES ('useradmin@mail.ru', 'Rustam', 'Kondratov', '{noop}pass', '2020-01-30 10:00:00'),
       ('user@yandex.ru', 'User', 'Userov', '{noop}pass', '2020-03-30 10:00:00'),
       ('admin@gmail.com', 'Admin', 'Adminov', '{noop}pass', '2022-01-30 10:00:00');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 1),
       ('USER', 2),
       ('ADMIN', 3);

INSERT INTO restaurant (name)
VALUES ('restaurant1'),
       ('restaurant2'),
       ('restaurant3');

INSERT INTO meal (restaurant_id, meal_name, price, registered)
VALUES (1, 'Борщ', 500, '2020-01-30 10:00:01'),
       (2, 'Блины', 1000, '2020-01-30 11:01:00'),
       (3, 'Плов', 500, '2020-01-30 12:12:12');

INSERT INTO voting_result (user_id, restaurant_id, registered)
VALUES (1, 3, '2022-04-26 10:00:00'),
       (2, 3, '2022-04-26 10:00:01'),
       (2, 2, '2022-04-26 10:59:00'),
       (3, 2, '2022-01-30 10:01:00');