package com.techelevator.model;

public class DecksFlashcardsDto {

    private int deckId;
    private int flashcardId;

    public DecksFlashcardsDto() {}

    public DecksFlashcardsDto(int deckId, int flashcardId) {
        this.deckId = deckId;
        this.flashcardId = flashcardId;
    }

    public int getDeckId() {
        return deckId;
    }

    public void setDeckId(int deckId) {
        this.deckId = deckId;
    }

    public int getFlashcardId() {
        return flashcardId;
    }

    public void setFlashcardId(int flashcardId) {
        this.flashcardId = flashcardId;
    }
}
