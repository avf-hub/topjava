DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES ('2020-01-30 10:00', 'Завтрак', 500, 100000),
       ('2020-01-30 13:00', 'Обед', 1000, 100000),
       ('2020-01-30 20:00', 'Ужин', 500, 100000),
       ('2020-01-31 00:00', 'Еда на граничное значение', 100, 100000),
       ('2020-01-31 10:00', 'Завтрак', 1000, 100000),
       ('2020-01-31 13:00', 'Обед', 500, 100000),
       ('2020-01-31 20:00', 'Ужин', 410, 100000),
       ('2022-10-02 11:00', 'Завтрак вместе с админом', 710, 100000),
       ('2015-06-01 14:00', 'Админ ланч', 510, 100001),
       ('2015-06-01 21:00', 'Админ ланч', 1500, 100001),
       ('2022-10-02 11:00', 'Завтрак вместе с пользователем', 710, 100001),
       ('2022-10-01 21:00', 'new meal', 1234, 100001);
