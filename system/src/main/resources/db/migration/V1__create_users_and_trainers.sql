CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       first_name VARCHAR(255) NOT NULL,
                       last_name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       phone VARCHAR(50),
                       password_hash VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL
);

CREATE TABLE trainer_profiles (
                       id BIGSERIAL PRIMARY KEY,
                       user_id BIGINT NOT NULL UNIQUE,
                       bio TEXT,
                       specialty VARCHAR(255),
                       CONSTRAINT fk_trainer_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);