DROP DATABASE IF EXISTS GestionPlanning;
CREATE DATABASE IF NOT EXISTS GestionPlanning;

USE GestionPlanning;

-- création des 5 tables
-- création de la table Event_Table
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
    modification BOOLEAN
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
    admin BOOLEAN,
    picture VARCHAR(2048),
    id_planning INT,
    compte_actif BOOLEAN,
	CONSTRAINT FK_PlanningUserId FOREIGN KEY (id_planning)
    REFERENCES planningTable(id)
);

-- création de la table login
CREATE TABLE IF NOT EXISTS loginTable
(
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	login VARCHAR(100) NOT NULL,
    password VARCHAR(250) NOT NULL,
    id_user INT,
    CONSTRAINT FK_UserId FOREIGN KEY (id_user)
    REFERENCES userTable(id)
);

-- création de la table liste
CREATE TABLE IF NOT EXISTS List_userTable
(
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	id_user INT UNIQUE,
    CONSTRAINT FK_UserFirstId FOREIGN KEY (id_user)
    REFERENCES userTable(id)
);

-- création de la table contact
CREATE TABLE IF NOT EXISTS ContactTable
(
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	id_user INT,
    CONSTRAINT FK_User FOREIGN KEY (id_user)
    REFERENCES userTable(id),
    id_list_user INT,
    CONSTRAINT FK_ListUserId FOREIGN KEY (id_list_user)
    REFERENCES List_userTable(id)
);

-- création des données pour mise en place du site
-- création d'un planning par user
INSERT INTO planningTable (export, acces, modification)
 VALUES
 (true, true, true),
 (true, false, false),
 (false, false, false),
 (true, true, true);
 
-- création de 3 users dont 2 admins
DELETE FROM userTable;
INSERT INTO userTable (first_name, last_name, city, birthday_date, phone_number, email, admin, id_planning, compte_actif, picture)
 VALUES
 ('Charlotte', 'Marion', 'Lyon', '1989-05-09', '07 26 37 82 98', 'charlotte@m2ifomration.fr', true, 1, 1, 'https://source.unsplash.com/random/300×300'),
 ('Pierre-Henri', 'Dupont', 'Nantes', '1973-07-28', '06 37 65 89 01', 'pierre-henry@m2ifomration.fr', false, 2, 1, 'https://source.unsplash.com/user/wsanter'),
 ('Maurice', 'Ravel', 'Bayonne', '2012-12-03', '06 67 24 31 89', 'maurice@m2ifomration.fr', true, 3 , 0, '' ),
 ('Jeremy', 'Lao', 'Dijon', '1997-06-15', '11 22 33 44 55', 'jerem@m2ifomration.fr', true, 4 , 1,'' );
 
INSERT INTO loginTable (login, password, id_user)
VALUES
	('Charlotte', 'ee958440ac8c0d2314ffc09662155062', 1 ),
	('Pierre-Henri', '4634605706c2d0d15409930754aa598b', 2 ),
	('Maurice', '882d444b0d0d12f1bea970d98c6bb02a', 3 ),
    ('Jeremy', 'e4233fe954925b4ecd0d5b3c54a61482', 4 );

-- création de 2 relations dans la table list_user
INSERT INTO List_userTable (id_user)
 VALUES
 (1),
 (2),
 (3),
 (4);
 
 -- création de 2 relations dans la table list_user
INSERT INTO ContactTable (id_user, id_list_user)
 VALUES
 (1, 2),
 (1, 3),
 (2, 1),
 (2, 3),
 (3, 1),
 (4, 1),
 (4, 2);
 

-- création de 3 events
INSERT INTO eventTable (title, date_event, start_time, end_time, description)
 VALUES
 ('Présentation fil rouge', '2022-09-09', '09:00:00', '17:00:00', 'Présentation projet fil rouge'),
 ('Vacances', '2022-08-23', '08:30:00', '09:00:00', 'Fin des vacances'),
 ('Finish', '2022-10-15', '16:30:00', '17:00:00', 'Fin de formation'),
 ('Spring Security', '2022-09-06', '8:30:00', '17:00:00', 'Apprentissage de Spring Security'),
 ('Présentation blanc', '2022-09-07', '8:30:00', '17:00:00', 'On montre a Freddy'),
 ('Présentation Cap Gemini', '2022-09-09', '15:00:00', '17:00:00', 'On montre a Cap G'),
 ('Travail', '2022-09-19', '09:00:00', '17:00:00', 'Début du travail à Infotel');
 
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
 (3, 3),
 (3, 4),
 (4, 4),
 (5, 4),
 (6, 4),
 (7, 4),
 (7, 1);
 
