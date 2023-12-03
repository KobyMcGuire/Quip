package com.techelevator.dao;

import com.techelevator.model.Flashcard;
import com.techelevator.model.FlashcardDto;

import java.util.List;

public interface FlashcardDao {

    List<Flashcard> getFlashcards();

    Flashcard getFlashcardById(int id);

    Flashcard createFlashcard(FlashcardDto flashcardDto);
    FlashcardDto updateFlashcard(FlashcardDto flashcardDto, int id);

    int deleteFlashcard(int id);
}
