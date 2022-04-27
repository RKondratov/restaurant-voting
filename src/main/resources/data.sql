INSERT INTO users (email, first_name, last_name, password, registered)
VALUES ('useradmin@mail.ru', 'Rustam', 'Kondratov', '{noop}pass', '2022-01-30 10:00:00'),
       ('user@yandex.ru', 'User', 'Userov', '{noop}pass', '2022-03-30 10:00:00'),
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
VALUES (1, 'soup', 500, date_trunc('day', sysdate)),
       (1, 'pancakes', 1000, date_trunc('day', sysdate)),
       (3, 'fish', 500, date_trunc('day', sysdate));

INSERT INTO voting_result (user_id, restaurant_id, registered)
VALUES (1, 3, date_trunc('day', sysdate) + 0.3),
       (2, 3, date_trunc('day', sysdate) + 0.3),
       (2, 2, date_trunc('day', sysdate) + 0.33),
       (3, 2, date_trunc('day', sysdate) - 0.9);