BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');

INSERT INTO flashcard_decks (title, description) VALUES ('Biology', 'Human Anatomy');
INSERT INTO flashcard_decks (title, description) VALUES ('Science', 'Periodic Table Elements');
INSERT INTO flashcard_decks (title, description) VALUES ('Math', 'Geometry Formulas');

INSERT INTO flashcards (deck_id, question, answer, tags, creator) VALUES(1, 'Test Biology Question 1', 'Test Biology Answer 1', 'Test Tags', 'Test Creator 1');
INSERT INTO flashcards (deck_id, question, answer, tags, creator) VALUES(1, 'Test Biology Question 2', 'Test Biology Answer 2', 'Test Tags', 'Test Creator 2');
INSERT INTO flashcards (deck_id, question, answer, tags, creator) VALUES(1, 'Test Biology Question 3', 'Test Biology Answer 3', 'Test Tags', 'Test Creator 3');

INSERT INTO flashcards (deck_id, question, answer, tags, creator) VALUES(2, 'Test Science Question 1', 'Test Science Answer 1', 'Test Tags', 'Test Creator 1');
INSERT INTO flashcards (deck_id, question, answer, tags, creator) VALUES(2, 'Test Science Question 2', 'Test Science Answer 2', 'Test Tags', 'Test Creator 2');
INSERT INTO flashcards (deck_id, question, answer, tags, creator) VALUES(2, 'Test Science Question 3', 'Test Science Answer 3', 'Test Tags', 'Test Creator 3');

INSERT INTO flashcards (deck_id, question, answer, tags, creator) VALUES(3, 'Test Math Question 1', 'Test Math Answer 1', 'Test Tags', 'Test Creator 1');
INSERT INTO flashcards (deck_id, question, answer, tags, creator) VALUES(3, 'Test Math Question 2', 'Test Math Answer 2', 'Test Tags', 'Test Creator 2');
INSERT INTO flashcards (deck_id, question, answer, tags, creator) VALUES(3, 'Test Math Question 3', 'Test Math Answer 3', 'Test Tags', 'Test Creator 3');

COMMIT TRANSACTION;
