DROP DATABASE IF EXISTS GestionPlanning;
CREATE DATABASE IF NOT EXISTS GestionPlanning;

USE GestionPlanning;

-- création des 5 tables
-- création de la table event
CREATE TABLE IF NOT EXISTS eventTable
(
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    date_event DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    description VARCHAR(1000) NOT NULL
);

-- création de la table planning
CREATE TABLE IF NOT EXISTS planningTable
(
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    export BOOLEAN,
    acces BOOLEAN,
    modification BOOLEAN,
    id_user int
    
);

-- création de la table planning_event
CREATE TABLE IF NOT EXISTS planning_eventTable
(
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    id_event INT,
    CONSTRAINT FK_EventId FOREIGN KEY (id_event)
    REFERENCES eventTable(id),
	id_planning INT,
    CONSTRAINT FK_PlanningId FOREIGN KEY (id_planning)
    REFERENCES PlanningTable(id)
);

-- création de la table user
CREATE TABLE IF NOT EXISTS userTable
(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    city VARCHAR(255) NOT NULL,
    birthday_date DATE NOT NULL,
    phone_number VARCHAR(15) NOT NULL UNIQUE,
	email VARCHAR(255) NOT NULL,
    login VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    admin BOOLEAN,
    picture VARCHAR(2048),
    id_planning INT,
	CONSTRAINT FK_PlanningUserId FOREIGN KEY (id_planning)
    REFERENCES planningTable(id)
);

-- modification table polanning pour ajouter l'id en FK de user
ALTER TABLE planningTable
ADD
CONSTRAINT FK_UserId FOREIGN KEY (id_user)
REFERENCES userTable(id);
    
    
-- création de la table liste
CREATE TABLE IF NOT EXISTS List_userTable
(
	id_first_user INT,
    CONSTRAINT FK_UserFirstId FOREIGN KEY (id_first_user)
    REFERENCES userTable(id),
    id_second_user INT,
    CONSTRAINT FK_UserSecondId FOREIGN KEY (id_second_user)
    REFERENCES userTable(id)
);

-- création des données pour mise en place du site
-- création d'un planning par user
INSERT INTO planningTable (export, acces, modification)
 VALUES
 (true, true, true),
 (true, false, false),
 (false, false, false);
 
-- création de 3 users dont 2 admins
DELETE FROM userTable;
INSERT INTO userTable (first_name, last_name, city, birthday_date, phone_number, email, login, password, admin, id_planning)
 VALUES
 ('Toto', 'TEST', 'Lyon', '1900-05-10', '07 26 37 82 98', 'test@m2ifomration.fr', 'test', '123456', true, 1 ),
 ('Tata', 'TEST2', 'Paris', '1965-12-21', '06 37 65 89 01', 'test2@m2ifomration.fr', 'test2', '123456', false, 2 ),
 ('Titi', 'TEST3', 'Nantes', '2012-12-03', '06 67 24 31 89', 'test3@m2ifomration.fr', 'test3', '123456', true, 3 );

-- ajout FK user id
UPDATE planningTable
SET id_user = 1
WHERE id = 1;

UPDATE planningTable
SET id_user = 2
WHERE id = 2;

UPDATE planningTable
SET id_user = 3
WHERE id = 3;

-- création de 2 relations dans la table list_user
INSERT INTO List_userTable (id_first_user, id_second_user)
 VALUES
 (1, 2),
 (2, 3);

-- création de 3 events
INSERT INTO eventTable (title, date_event, start_time, end_time, description)
 VALUES
 ('Présentation fil rouge', '2022-09-09', '09:00:00', '17:00:00', 'Présentation projet fil rouge'),
 ('Vacances', '2022-08-23', '08:30:00', '09:00:00', 'Fin des vacances'),
 ('Finish', '2022-09-15', '16:30:00', '17:00:00', 'Fin de formation');
 
-- création de 9 planning event
INSERT INTO planning_eventTable (id_event, id_planning)
 VALUES
 (1, 1),
 (1, 2),
 (1, 3),
 (2, 1),
 (2, 2),
 (2, 3),
 (3, 1),
 (3, 2),
 (3, 3);
 
