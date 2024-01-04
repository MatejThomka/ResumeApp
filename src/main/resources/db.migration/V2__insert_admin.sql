-- db/migration/V2__insert_admin.sql

INSERT INTO user (name, email, password, role)
VALUE ('Matej', 'matej@thomka.sk', '123456', 1)