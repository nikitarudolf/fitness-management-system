INSERT INTO users (first_name, last_name, email, phone, password_hash, role)
VALUES ('Главный', 'Админ', 'admin@gym.com', '+375123456789', 'Password', 'ADMIN');

INSERT INTO subscription_plans (name, price, duration_days, includes_trainer_access)
VALUES
    ('Базовый месяц', 3000.00, 30, FALSE),
    ('С тренером', 7000.00, 30, TRUE),
    ('Годовой VIP', 25000.00, 365, TRUE);