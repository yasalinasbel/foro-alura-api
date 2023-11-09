DROP DATABASE foroAlura;
CREATE DATABASE foroAlura;

use foroAlura;

CREATE TABLE user_data (

	id INT AUTO_INCREMENT,
	name VARCHAR(100) NOT NULL,
	username VARCHAR(50) NOT NULL,
	password VARCHAR(50)NOT NULL,
	email VARCHAR(50)NOT NULL,
	PRIMARY KEY(id)

)Engine=InnoDB;

CREATE TABLE topic (

	id INT AUTO_INCREMENT,
	id_user INT,
	title VARCHAR(50) NOT NULL,
	message VARCHAR (200) NOT NULL,
	creation_date TIMESTAMP NOT NULL,
	topic_status VARCHAR (20) NOT NULL,
	course VARCHAR(30) NOT NULL,
	deleted TINYINT NOT NULL,
	FOREIGN KEY(id_user) REFERENCES user_data(id),
	PRIMARY KEY(id)
)Engine=InnoDB;

CREATE TABLE reply (

	id INT AUTO_INCREMENT,
	id_user INT,
	reply VARCHAR (200) NOT NULL,
    id_topic INT,
    creation_date TIMESTAMP NOT NULL,
	FOREIGN KEY(id_topic) REFERENCES topic(id),
	FOREIGN KEY(id_user) REFERENCES user_data(id),
	PRIMARY KEY(id)
)Engine=InnoDB;

INSERT INTO user_data (name,username,password, email)
VALUES('Aurora','abeltran','1234','abeltran@gmail.com'),
	  ('Berta','bbeltran','5678','bbeltran@gmail.com'),
	  ('Camilo','cbeltran','9876','camilobeltran@gmail.com');
