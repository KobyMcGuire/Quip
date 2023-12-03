package com.techelevator.dao;

import com.techelevator.model.Flashcard;

import java.util.List;

public interface FlashcardDao {

    List<Flashcard> getFlashcards();

    Flashcard getFlashcardById(int id);

    Flashcard createFlashcard(Flashcard flashcard);
    Flashcard updateFlashcard(Flashcard flashcard);

    int deleteFlashcard(int id);
}
