package com.techelevator.dao;

import com.techelevator.model.Deck;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcDeckDao implements DeckDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcDeckDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Deck> getDecks() {
        return null;
    }

    @Override
    public Deck getDeckById(int id) {
        return null;
    }

    @Override
    public Deck createDeck(Deck deck) {
        return null;
    }

    @Override
    public Deck updateDeck(Deck deck, int id) {
        return null;
    }

    @Override
    public void deleteDeck(int id){

    }
}
