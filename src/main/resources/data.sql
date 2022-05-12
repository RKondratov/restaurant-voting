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

INSERT INTO vote (user_id, restaurant_id, registered)
VALUES (1, 3, date_trunc('day', current_date) + 0.3),
       (2, 3, date_trunc('day', current_date) + 0.3),
       (2, 2, date_trunc('day', current_date) + 0.33),
       (1, 1, date_trunc('day', current_date) - 1),
       (2, 2, date_trunc('day', current_date) - 1),
       (3, 3, date_trunc('day', current_date) - 1),
       (3, 2, date_trunc('day', current_date) - 0.9);

INSERT INTO dish (restaurant_id, name, price, creation_date)
VALUES (1, 'soup', 500, date_trunc('day', current_date)),
       (1, 'pancakes', 1000, date_trunc('day', current_date)),
       (1, 'soup2', 502, date_trunc('day', current_date - 2)),
       (2, 'soup3', 502, date_trunc('day', current_date - 2)),
       (3, 'soup4', 502, date_trunc('day', current_date - 2)),
       (3, 'fish', 500, date_trunc('day', current_date));