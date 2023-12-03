package com.techelevator.dao;

import com.techelevator.model.Deck;
import com.techelevator.model.DeckDto;

import java.util.List;

public interface DeckDao {

    List<Deck> getDecks();

    Deck getDeckById(int id);

    //createDeck() probably needs to pass in a Dto as an argument
    Deck createDeck(DeckDto deckDto);

    DeckDto updateDeck(DeckDto deckDto, int id);

    int deleteDeck(int id);
}
