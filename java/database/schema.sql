BEGIN TRANSACTION;

DROP TABLE IF EXISTS users, flashcard_decks, flashcards, decks_flashcards;

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
	color varchar(50) NULL,
	description varchar(100),
	CONSTRAINT PK_flashcard_decks PRIMARY KEY(deck_id)
);

CREATE TABLE flashcards(
	flashcard_id SERIAL,
	question text NOT NULL,
	answer text NOT NULL,
	tags varchar(50) NOT NULL,
	image varchar(100) NULL,
	creator varchar(50) NOT NULL,
	CONSTRAINT PK_flashcards PRIMARY KEY(flashcard_id)
);

CREATE TABLE decks_flashcards(
	deck_id int NOT NULL,
	flashcard_id int NOT NULL,
	CONSTRAINT PK_decks_flashcards PRIMARY KEY(deck_id, flashcard_id),
	CONSTRAINT FK_decks_flashcards_flashcard_decks FOREIGN KEY(deck_id) REFERENCES flashcard_decks(deck_id),
	CONSTRAINT FK_decks_flashcards_flashcards FOREIGN KEY(flashcard_id) REFERENCES flashcards(flashcard_id)
);

COMMIT TRANSACTION;
