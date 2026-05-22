CREATE TABLE trainer_schedules (
                                   id BIGSERIAL PRIMARY KEY,
                                   trainer_id BIGINT NOT NULL,
                                   working_date DATE NOT NULL,
                                   start_time TIME NOT NULL,
                                   end_time TIME NOT NULL,
                                   CONSTRAINT fk_schedule_trainer FOREIGN KEY (trainer_id) REFERENCES trainer_profiles (id) ON DELETE CASCADE
);

CREATE TABLE bookings (
                          id BIGSERIAL PRIMARY KEY,
                          user_id BIGINT NOT NULL,
                          schedule_id BIGINT NOT NULL,
                          status VARCHAR(50) NOT NULL,
                          CONSTRAINT fk_booking_user FOREIGN KEY (user_id) REFERENCES users (id),
                          CONSTRAINT fk_booking_schedule FOREIGN KEY (schedule_id) REFERENCES trainer_schedules (id)
);