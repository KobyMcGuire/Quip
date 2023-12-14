BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');

INSERT INTO flashcard_decks (title, description, color) VALUES ('Biology', 'General Science', '#6495ED');
INSERT INTO flashcard_decks (title, description, color) VALUES ('Science', 'Periodic Table Elements', '#228B22');
INSERT INTO flashcard_decks (title, description, color) VALUES ('Math', 'Geometry Formulas', '#B22222');

INSERT INTO flashcards (question, answer, tags, creator) VALUES('Which is the most diverse and inclusive taxon?', 'Kingdom', 'Biology General', 'Koby');
INSERT INTO flashcards (question, answer, tags, creator) VALUES('As part of the scientific method, a scientist proposes and tests ____', 'Hypotheses', 'Biology General', 'Koby');
INSERT INTO flashcards (question, answer, tags, creator) VALUES('A Scientist is investigating the results of varying temps on the growth rate of bacterial culture. In this experiment, temp is the ____', 'Independent Variable', 'Biology General', 'Koby');
INSERT INTO flashcards (question, answer, tags, creator) VALUES('Fundamental building block of all matter', 'Atom', 'Biology General', 'Koby');
INSERT INTO flashcards (question, answer, tags, creator) VALUES('Two or more atoms', 'Molecule', 'Biology General', 'Koby');
INSERT INTO flashcards (question, answer, tags, creator) VALUES('A grouping of tissues engaged in a collective task', 'Organ', 'Biology Independent', 'Koby');

INSERT INTO flashcards (question, answer, tags, creator) VALUES('H', 'Hydrogen', 'Science Element', 'Koby');
INSERT INTO flashcards (question, answer, tags, creator) VALUES('Au', 'Gold', 'Science Element', 'Abdul');
INSERT INTO flashcards (question, answer, tags, creator) VALUES('Li', 'Lithium', 'Science Element', 'Christian');
INSERT INTO flashcards (question, answer, tags, creator) VALUES('He', 'Helium', 'Science Element', 'Koby');
INSERT INTO flashcards (question, answer, tags, creator) VALUES('Ti', 'Titanium', 'Science Element', 'Koby');
INSERT INTO flashcards (question, answer, tags, creator) VALUES('Al', 'Aluminum', 'Science Element', 'Christian');

INSERT INTO flashcards (question, answer, tags, creator) VALUES('Area of a triangle', '(1/2) * base * height', 'Math Geometry', 'Leul');
INSERT INTO flashcards (question, answer, tags, creator) VALUES('Circumference of a circle', '2 * pi * radius', 'Math Geometry', 'Koby');
INSERT INTO flashcards (question, answer, tags, creator) VALUES('Area of a circle', 'pi * radius^2', 'Math Geometry', 'Christian');
INSERT INTO flashcards (question, answer, tags, creator) VALUES('Volume of a cone', '(1/3) * (pi * radius^2 * height))', 'Math Geometry', 'Koby');
INSERT INTO flashcards (question, answer, tags, creator) VALUES('Surface Area of a sphere', '4 * pi * radius^2', 'Math Geometry', 'Abdul');
INSERT INTO flashcards (question, answer, tags, creator) VALUES('Volume of a sphere', '(4/3) * (pi * radius^3)', 'Math Geometry', 'Abdul');

INSERT INTO decks_flashcards(deck_id , flashcard_id) VALUES (1, 1);
INSERT INTO decks_flashcards(deck_id , flashcard_id) VALUES (1, 2);
INSERT INTO decks_flashcards(deck_id , flashcard_id) VALUES (1, 3);
INSERT INTO decks_flashcards(deck_id , flashcard_id) VALUES (1, 4);
INSERT INTO decks_flashcards(deck_id , flashcard_id) VALUES (1, 5);
INSERT INTO decks_flashcards(deck_id , flashcard_id) VALUES (1, 6);

INSERT INTO decks_flashcards(deck_id , flashcard_id) VALUES (2, 7);
INSERT INTO decks_flashcards(deck_id , flashcard_id) VALUES (2, 8);
INSERT INTO decks_flashcards(deck_id , flashcard_id) VALUES (2, 9);
INSERT INTO decks_flashcards(deck_id , flashcard_id) VALUES (2, 10);
INSERT INTO decks_flashcards(deck_id , flashcard_id) VALUES (2, 11);
INSERT INTO decks_flashcards(deck_id , flashcard_id) VALUES (2, 12);

INSERT INTO decks_flashcards(deck_id , flashcard_id) VALUES (3, 13);
INSERT INTO decks_flashcards(deck_id , flashcard_id) VALUES (3, 14);
INSERT INTO decks_flashcards(deck_id , flashcard_id) VALUES (3, 15);
INSERT INTO decks_flashcards(deck_id , flashcard_id) VALUES (3, 16);
INSERT INTO decks_flashcards(deck_id , flashcard_id) VALUES (3, 17);
INSERT INTO decks_flashcards(deck_id , flashcard_id) VALUES (3, 18);

COMMIT TRANSACTION;
