package com.techelevator.dao;

import com.techelevator.model.Flashcard;
import com.techelevator.model.FlashcardDto;

import java.util.List;

public interface FlashcardDao {

    List<Flashcard> getFlashcards();

    Flashcard getFlashcardById(int id);

    List<Flashcard> getFlashcardsByDeckId(int deckId);

    int putFlashcardInNewDeck(int deckId, int flashcardId);

    List<Flashcard> getFlashcardsByTag(String tag, int deckId, boolean useWildcard);

    List<Flashcard> getFlashcardsByQuestion(String question, int deckId, boolean useWildcard);

    Flashcard createFlashcard(FlashcardDto flashcardDto);
    FlashcardDto updateFlashcard(FlashcardDto flashcardDto, int id);

    int deleteFlashcard(int id);
}
