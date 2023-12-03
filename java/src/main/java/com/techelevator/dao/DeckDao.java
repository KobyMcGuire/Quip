package com.techelevator.dao;

import com.techelevator.model.Deck;

import java.util.List;

public interface DeckDao {

    List<Deck> getDecks();

    Deck getDeckById(int id);

    //createDeck() probably needs to pass in a Dto as an argument
    Deck createDeck(Deck deck);

    Deck updateDeck(Deck deck);

    int deleteDeck(int id);
}
