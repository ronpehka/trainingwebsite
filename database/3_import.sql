INSERT INTO training.weekday (id, short, number) VALUES (default, 'E', 1);
INSERT INTO training.weekday (id, short, number) VALUES (default, 'T', 2);
INSERT INTO training.weekday (id, short, number) VALUES (default, 'K', 3);
INSERT INTO training.weekday (id, short, number) VALUES (default, 'N', 4);
INSERT INTO training.weekday (id, short, number) VALUES (default, 'R', 5);
INSERT INTO training.weekday (id, short, number) VALUES (default, 'L', 6);
INSERT INTO training.weekday (id, short, number) VALUES (default, 'P', 7);

INSERT INTO training.role (role_id, name) VALUES (default, 'admin');
INSERT INTO training.role (role_id, name) VALUES (default, 'coach');
INSERT INTO training.role (role_id, name) VALUES (default, 'customer');

INSERT INTO training.sport (id, name) VALUES (default, 'korvpall');
INSERT INTO training.sport (id, name) VALUES (default, 'jalgpall');
INSERT INTO training.sport (id, name) VALUES (default, 'pesapall');

INSERT INTO training."user" (id, role_id, email, password, status) VALUES (default, 1, 'admin@admin.ee', '123', 'A');
INSERT INTO training."user" (id, role_id, email, password, status) VALUES (default, 2, 'coach@coach.ee', '123', 'A');
INSERT INTO training."user" (id, role_id, email, password, status) VALUES (default, 3, 'client@client.ee', '123', 'A');
INSERT INTO training."user" (id, role_id, email, password, status) VALUES (default, 3, 'client2@client.ee', '123', 'D');

INSERT INTO training.profile (
    id, user_id, first_name, last_name, description, email, phone, date_of_birth, gender
) VALUES (
             default, 3, 'Jaan', 'Uss', null, 'client@client.ee', '44444433', '1998-11-20', 'M'
         );

INSERT INTO training.profile (
    id, user_id, first_name, last_name, description, email, phone, date_of_birth, gender
) VALUES (
             default, 2, 'Nipi', 'Tiri', 'Ma olen ülikõva treener', 'coach@coach.ee', '112', '1993-06-20', 'N'
         );


INSERT INTO training.district (id, name) VALUES (default, 'Haabersti');
INSERT INTO training.district (id, name) VALUES (default, 'Kesklinn');
INSERT INTO training.district (id, name) VALUES (default, 'Viimsi');
INSERT INTO training.district (id, name) VALUES (default, 'Tabasalu');

INSERT INTO training.location (id, name, district_id, address, lat, long, openinghours) VALUES (default, 'Saku suurhall', 1, 'Paldiski mnt 104b, 13522 Tallinn', null, null, 'E-R 11-22 L,P 9-22');
INSERT INTO training.location (id, name, district_id, address, lat, long, openinghours) VALUES (default, 'Forus Spordikeskus', 3, 'Karulaugu tee 13, Haabneeme, 74001 Harju maakond', null, null, 'E-R 10-23');
INSERT INTO training.location (id, name, district_id, address, lat, long, openinghours) VALUES (default, 'Tabasalu spordikompleks', 4, 'Kooli tn 1c, Tabasalu, 76901 Harju maakond', null, null, 'E-P 9-21');

INSERT INTO training.training (id, coach_user_id, name, description, gender, start_date, end_date, start_time, end_time, sport_id, status, max_limit) VALUES (default, 2, 'PU10 korvpall', 'Poiste korvpall', 'M', '2025-05-16', '2026-09-10', '14:30:00', '16:00:00', 1, 'A', 20);
INSERT INTO training.training (id, coach_user_id, name, description, gender, start_date, end_date, start_time, end_time, sport_id, status, max_limit) VALUES (default, 2, 'TU11 pesapall', 'Tüdrukute pesapall', 'N', '2025-05-20', '2026-11-11', '12:00:00', '13:30:00', 3, 'A', 20);
INSERT INTO training.training (id, coach_user_id, name, description, gender, start_date, end_date, start_time, end_time, sport_id, status, max_limit) VALUES (default, 2, 'PU10 jalgpall', 'Poiste jalgpall', 'M', '2025-06-11', '2026-08-04', '16:30:00', '18:00:00', 2, 'A', 20);

INSERT INTO training.coach_sport (id, coach_user_id, sport_id) VALUES (default, 2, 1);
INSERT INTO training.coach_sport (id, coach_user_id, sport_id) VALUES (default, 2, 2);
INSERT INTO training.coach_sport (id, coach_user_id, sport_id) VALUES (default, 2, 3);

INSERT INTO training.training_location (id, training_id, location_id, status) VALUES (default, 1, 1, 'A');
INSERT INTO training.training_location (id, training_id, location_id, status) VALUES (default, 2, 2, 'A');
INSERT INTO training.training_location (id, training_id, location_id, status) VALUES (default, 3, 3, 'A');

INSERT INTO training.training_weekday (id, training_id, weekday_id) VALUES (default, 1, 2);
INSERT INTO training.training_weekday (id, training_id, weekday_id) VALUES (default, 1, 4);
INSERT INTO training.training_weekday (id, training_id, weekday_id) VALUES (default, 2, 3);
INSERT INTO training.training_weekday (id, training_id, weekday_id) VALUES (default, 2, 5);
INSERT INTO training.training_weekday (id, training_id, weekday_id) VALUES (default, 3, 6);
INSERT INTO training.training_weekday (id, training_id, weekday_id) VALUES (default, 3, 7);

INSERT INTO training.training_date (id, training_id, date) VALUES (default, 1, '2025-05-20');
INSERT INTO training.training_date (id, training_id, date) VALUES (default, 1, '2025-05-22');

INSERT INTO training.register (id, training_id, user_id, status, date) VALUES (1, 1, 3, 'A', '2025-05-16');
INSERT INTO training.register (id, training_id, user_id, status, date) VALUES (2, 2, 3, 'A', '2025-05-16');
INSERT INTO training.register (id, training_id, user_id, status, date) VALUES (3, 3, 3, 'A', '2025-05-16');

