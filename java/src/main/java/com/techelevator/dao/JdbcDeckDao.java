package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Deck;
import com.techelevator.model.DeckDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcDeckDao implements DeckDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcDeckDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public List<Deck> getDecks() {
        List<Deck> decks = new ArrayList<>();
        String sql = "SELECT deck_id, title, description, color FROM flashcard_decks;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while(results.next()){
                Deck deck = mapRowToDeck(results);
                decks.add(deck);
            }
        }catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        }catch(DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return decks;
    }

    @Override
    public Deck getDeckById(int id) {
        Deck deck = null;
        String sql = "SELECT deck_id, title, description, color FROM flashcard_decks WHERE deck_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if(results.next()){
                deck = mapRowToDeck(results);
            }
        }catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        }catch(DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return deck;
    }

    @Override
    public Deck createDeck(DeckDto deckDto) {
        Deck newDeck = null;
        String sql = "INSERT INTO flashcard_decks(title, description, color)\n" +
                "VALUES(?, ?, ?) RETURNING deck_id;";
        try{
            int newDeckId = jdbcTemplate.queryForObject(sql, int.class, deckDto.getTitle(), deckDto.getDescription(), deckDto.getColor());
            newDeck = getDeckById(newDeckId);
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return newDeck;
    }

    @Override
    public DeckDto updateDeck(DeckDto deckDto, int id) {
        String sql = "UPDATE flashcard_decks\n" +
                "SET title = ?, description = ?\n" +
                "WHERE deck_id = ?";
        try{
            int numOfRowsUpdated = jdbcTemplate.update(sql, deckDto.getTitle(), deckDto.getDescription(), id);
            if(numOfRowsUpdated == 0){
                throw new DaoException("No records were updated");
            }
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Couldn't connect to database. Please contact someone who cares.", e);
        }catch(DataIntegrityViolationException e){
            throw new DaoException("Your attempt to add data to this table violates data integrity.", e);
        }
        return deckDto;
    }

    //Not sure if we should keep the deleteFlashcards() considering the fact
    //that it is not in the user stories...keeping this here to discuss with
    //team and product owner
    @Override
    public int deleteDeck(int id){
        int numberOfRows = 0;
        String sql = "DELETE FROM flashcard_decks WHERE deck_id = ?;";
        try{
            numberOfRows = jdbcTemplate.update(sql, id);
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }

    private Deck mapRowToDeck(SqlRowSet results){
        Deck deck = new Deck();
        deck.setDeckId(results.getInt("deck_id"));
        deck.setTitle(results.getString("title"));
        deck.setDescription(results.getString("description"));
        deck.setColor(results.getString("color"));
        return deck;
    }
}
