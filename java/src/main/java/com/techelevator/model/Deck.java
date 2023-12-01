package com.techelevator.model;

public class Deck {

    private int deckId;
    private String title;
    private String description;

    public Deck(int deckId, String title, String description) {
        this.deckId = deckId;
        this.title = title;
        this.description = description;
    }

    public Deck() {
    }

    public int getDeckId() {
        return deckId;
    }

    public void setDeckId(int deckId) {
        this.deckId = deckId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
