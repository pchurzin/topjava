DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id) VALUES
  ('2018-07-14 08:00:00', 'Завтрак админа', 500, 100001),
  ('2018-07-14 13:00:00', 'Обед админа', 1000, 100001),
  ('2018-07-14 20:00:00', 'Ужин админа', 500, 100001),
  ('2018-07-15 08:00:00', 'Завтрак', 500, 100000),
  ('2018-07-15 13:00:00', 'Обед', 1000, 100000),
  ('2018-07-15 20:00:00', 'Ужин', 500, 100000),
  ('2018-07-14 08:00:00', 'Завтрак', 500, 100000),
  ('2018-07-14 13:00:00', 'Обед', 1000, 100000),
  ('2018-07-14 20:00:00', 'Ужин', 510, 100000);