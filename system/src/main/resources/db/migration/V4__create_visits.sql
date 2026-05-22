CREATE TABLE visits (
                        id BIGSERIAL PRIMARY KEY,
                        user_id BIGINT NOT NULL,
                        check_in_time TIMESTAMP NOT NULL,
                        check_out_time TIMESTAMP,
                        CONSTRAINT fk_visit_user FOREIGN KEY (user_id) REFERENCES users (id)
);