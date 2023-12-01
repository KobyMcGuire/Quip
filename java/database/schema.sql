BEGIN TRANSACTION;

DROP TABLE IF EXISTS users, flashcard_decks, flashcards;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);
	
CREATE TABLE flashcard_decks(
	deck_id SERIAL,
	title varchar(50) NOT NULL,
	description varchar(100),
	CONSTRAINT PK_flashcard_deck PRIMARY KEY(deck_id)
);

CREATE TABLE flashcards(
	flashcard_id SERIAL,
	deck_id int NOT NULL,
	question text NOT NULL,
	answer text NOT NULL,
	tags varchar(50) NOT NULL,
	creator varchar(50) NOT NULL,
	CONSTRAINT PK_flashcard PRIMARY KEY(flashcard_id),
	CONSTRAINT FK_flashcard_deck_flashcard FOREIGN KEY(deck_id) REFERENCES flashcard_decks(deck_id)
);

COMMIT TRANSACTION;
