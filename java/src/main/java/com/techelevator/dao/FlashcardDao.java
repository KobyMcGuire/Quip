package com.techelevator.dao;

import com.techelevator.model.DecksFlashcardsDto;
import com.techelevator.model.Flashcard;
import com.techelevator.model.FlashcardDto;

import java.util.List;

public interface FlashcardDao {

    List<Flashcard> getFlashcards();

    Flashcard getFlashcardById(int id);

    List<Flashcard> getFlashcardsByDeckId(int deckId);

    int putFlashcardInNewDeck(DecksFlashcardsDto decksFlashcards);

    List<Flashcard> getFlashcardsByTag(String tag, String flashcardIds, boolean useWildcard);

    List<Flashcard> getFlashcardsByQuestion(String question, String flashcardIds, boolean useWildcard);

    Flashcard createFlashcard(FlashcardDto flashcardDto);
    FlashcardDto updateFlashcard(FlashcardDto flashcardDto, int id);

    int deleteFlashcard(int flashcardId, int deckId);
}
