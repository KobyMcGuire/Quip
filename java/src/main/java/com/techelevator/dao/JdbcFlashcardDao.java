package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Deck;
import com.techelevator.model.Flashcard;
import com.techelevator.model.FlashcardDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcFlashcardDao implements FlashcardDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcFlashcardDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Flashcard> getFlashcards() {
        List<Flashcard> flashcards = new ArrayList<>();
        String sql = "SELECT flashcard_id, deck_id, question, answer, tags, creator\n" +
                "FROM flashcards;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while(results.next()){
                Flashcard flashcard = mapRowToFlashcard(results);
                flashcards.add(flashcard);
            }
        }catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        }catch(DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return flashcards;
    }

    @Override
    public Flashcard getFlashcardById(int id) {
        Flashcard flashcard = null;
        String sql = "SELECT flashcard_id, deck_id, question, answer, tags, creator\n" +
                "FROM flashcards WHERE flashcard_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()){
                flashcard = mapRowToFlashcard(results);
            }
        }catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        }catch(DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return flashcard;
    }

    @Override
    public List<Flashcard> getFlashcardsByTag(String tag, boolean useWildcard){
        List<Flashcard> flashcards = new ArrayList<>();
        String sql = "SELECT flashcard_id, deck_id, question, answer, tags, creator FROM flashcards WHERE tags ILIKE ?;";
        if(useWildcard){
            tag = "%" + tag + "%";
        }
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, tag);
            while(results.next()){
                Flashcard flashcard = mapRowToFlashcard(results);
                flashcards.add(flashcard);
            }
        }catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        }catch(DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return flashcards;
    }

    @Override
    public Flashcard createFlashcard(FlashcardDto flashcardDto) {
        Flashcard newFlashcard = null;
        String sql = "INSERT INTO flashcards(deck_id, question, answer, tags, creator)\n" +
                "VALUES(?, ?, ?, ?, ?) RETURNING flashcard_id;";
        try{
            int newFlashcardId = jdbcTemplate.queryForObject(sql, int.class, flashcardDto.getDeckId(), flashcardDto.getQuestion(), flashcardDto.getAnswer(), flashcardDto.getTag(), flashcardDto.getCreator());
            newFlashcard = getFlashcardById(newFlashcardId);
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newFlashcard;
    }

    @Override
    public FlashcardDto updateFlashcard(FlashcardDto flashcardDto, int id) {
        String sql = "UPDATE flashcards\n" +
                "SET question = ?, answer = ?, tags = ?\n" +
                "WHERE flashcard_id = ?";
//        String sql = "UPDATE flashcards\n" +
//                "SET deck_id = ?, question = ?, answer = ?, tags = ?, creator = ?\n" +
//                "WHERE flashcard_id = ?";
        try {
            int numOfRowsUpdated = jdbcTemplate.update(sql, flashcardDto.getQuestion(), flashcardDto.getAnswer(), flashcardDto.getTag(), id);
            if(numOfRowsUpdated == 0){
                throw new DaoException("No records were updated");
            }
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Couldn't connect to database. Please contact someone who cares.", e);
        }catch(DataIntegrityViolationException e){
            throw new DaoException("Your attempt to add data to this table violates data integrity.", e);
        }
        return flashcardDto;
    }


    @Override
    public int deleteFlashcard(int id) {
        int numberOfRows = 0;
        String sql = "DELETE FROM flashcards WHERE flashcard_id = ?;";
        try{
            numberOfRows = jdbcTemplate.update(sql, id);
        }catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }

    private Flashcard mapRowToFlashcard(SqlRowSet results){
        Flashcard flashcard = new Flashcard();
        flashcard.setFlashCardId(results.getInt("flashcard_id"));
        flashcard.setDeckId(results.getInt("deck_id"));
        flashcard.setQuestion(results.getString("question"));
        flashcard.setAnswer(results.getString("answer"));
        flashcard.setTag(results.getString("tags"));
        flashcard.setCreator(results.getString("creator"));
        return flashcard;
    }
}
