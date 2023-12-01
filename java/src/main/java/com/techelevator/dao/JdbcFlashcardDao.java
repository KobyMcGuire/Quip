package com.techelevator.dao;

import com.techelevator.model.Deck;
import com.techelevator.model.Flashcard;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcFlashcardDao implements FlashcardDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcFlashcardDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Flashcard> getFlashcards() {
        return null;
    }

    @Override
    public Flashcard getFlashcardById(int id) {
        return null;
    }

    @Override
    public Flashcard createFlashcard(Flashcard flashcard) {
        return null;
    }

    @Override
    public Flashcard updateFlashcard(Flashcard flashcard, int id) {
        return null;
    }

    @Override
    public void deleteFlashcard(int id) {

    }
}
