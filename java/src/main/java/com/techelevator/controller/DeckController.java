package com.techelevator.controller;

import com.techelevator.dao.DeckDao;
import com.techelevator.dao.FlashcardDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
//@PreAuthorize("isAuthenticated()")
@CrossOrigin
public class DeckController {
    private UserDao userDao;
    private DeckDao deckDao;
    private FlashcardDao flashcardDao;

    public DeckController(DeckDao deckDao, FlashcardDao flashcardDao, UserDao userDao) {
        this.deckDao = deckDao;
        this.flashcardDao = flashcardDao;
        this.userDao = userDao;
    }

    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public List<User> getUsers(){
        return userDao.getUsers();
    }

    @RequestMapping(path = "/user", method = RequestMethod.POST)
    public User createUser(RegisterUserDto user){
        return userDao.createUser(user);
    }

    @RequestMapping(path = "/decks", method = RequestMethod.GET)
    public List<Deck> getDecks() {
        return deckDao.getDecks();
    }
    @RequestMapping(path = "/decks/{id}", method = RequestMethod.GET)
    public Deck getDeckByDeckId(@PathVariable int id){
        return deckDao.getDeckById(id);
    }

    @RequestMapping(path = "/decks", method = RequestMethod.POST)
    public Deck createDeck(@RequestBody DeckDto deckDto) {
        return deckDao.createDeck(deckDto);
    }

    @RequestMapping(path="/decks/{id}", method = RequestMethod.PUT)
    public DeckDto updateDeck(@RequestBody DeckDto deckDto, @PathVariable int id){
        return deckDao.updateDeck(deckDto, id);
    }

    //Not sure if we should keep the deleteFlashcards() considering the fact
    //that it is not in the user stories...keeping this here to discuss with
    //team and product owner
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/decks/{id}", method = RequestMethod.DELETE)
    public void deleteDeck(@PathVariable int id){
        try{
            int numRowsDeleted = deckDao.deleteDeck(id);

        }catch(ResourceAccessException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(path = "/flashcards", method = RequestMethod.GET)
    public List<Flashcard> getFlashcards(@RequestParam(required = false) String tag, @RequestParam int deckId, @RequestParam(required = false) String question, @RequestParam(required = false, defaultValue = "true") boolean useWildcard) {
        if(tag != null && question == null){
            return flashcardDao.getFlashcardsByTag(tag, deckId, useWildcard);
        }
        if(tag == null && question != null){
            return flashcardDao.getFlashcardsByQuestion(question, deckId, useWildcard);
        }
        return flashcardDao.getFlashcards();
    }

    @GetMapping(path = "/flashcards/{id}")
    public Flashcard getFlashcardsById (@PathVariable int id) {
        return flashcardDao.getFlashcardById(id);
    }

    @RequestMapping(path = "/flashcards/decks/{deckId}", method = RequestMethod.GET)
    public List<Flashcard> getFlashcardsByDeckId(@PathVariable int deckId){
            return flashcardDao.getFlashcardsByDeckId(deckId);
    }

    @RequestMapping(path= "/flashcards/decks", method = RequestMethod.POST)
    public int putFlashcardInNewDeck(@RequestBody DecksFlashcardsDto decksFlashcards) {
        return flashcardDao.putFlashcardInNewDeck(decksFlashcards);
    }

    @RequestMapping(path = "/flashcards", method = RequestMethod.POST)
    public Flashcard createFlashcard(@RequestBody FlashcardDto flashcardDto) {
        return flashcardDao.createFlashcard(flashcardDto);
    }
    @RequestMapping(path="/flashcards/{id}", method = RequestMethod.PUT)
    public FlashcardDto updateFlashCard(@RequestBody FlashcardDto flashcardDto, @PathVariable int id){
        return flashcardDao.updateFlashcard(flashcardDto, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/flashcards/decks/{flashcardId}", method = RequestMethod.DELETE)
    public void deleteFlashcard(@PathVariable int flashcardId, @RequestParam int deckId){
        try{
            int numRowsDeleted = flashcardDao.deleteFlashcard(flashcardId, deckId);

        }catch(ResourceAccessException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
