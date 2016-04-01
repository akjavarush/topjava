DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM user_meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;
ALTER SEQUENCE user_meals_meal_id_seq RESTART WITH 1;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO user_meals(date, description, calories, user_id)
VALUES('2015-05-30 10:00', 'Завтрак', 500, 100000),
('2015-05-30 13:00', 'Обед', 1000, 100000),
('2015-05-30 20:00', 'Ужин', 500, 100000),
('2015-05-31 10:00', 'Завтрак', 1000, 100000),
('2015-05-31 13:00', 'Обед', 500, 100000),
('2015-05-31 20:00', 'Ужин', 510, 100000),
('2015-05-30 10:00', 'Завтрак Админ', 500, 100001),
('2015-05-30 13:00', 'Обед Админ', 1000, 100001);
