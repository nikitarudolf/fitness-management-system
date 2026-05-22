CREATE TABLE subscription_plans (
                                    id BIGSERIAL PRIMARY KEY,
                                    name VARCHAR(255) NOT NULL,
                                    price NUMERIC(10, 2) NOT NULL,
                                    duration_days INT NOT NULL,
                                    includes_trainer_access BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE user_subscriptions (
                                    id BIGSERIAL PRIMARY KEY,
                                    user_id BIGINT NOT NULL,
                                    plan_id BIGINT NOT NULL,
                                    start_date DATE NOT NULL,
                                    end_date DATE NOT NULL,
                                    is_active BOOLEAN NOT NULL DEFAULT TRUE,
                                    CONSTRAINT fk_sub_user FOREIGN KEY (user_id) REFERENCES users (id),
                                    CONSTRAINT fk_sub_plan FOREIGN KEY (plan_id) REFERENCES subscription_plans (id)
);