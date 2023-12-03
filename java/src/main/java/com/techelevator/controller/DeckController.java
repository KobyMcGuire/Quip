package com.techelevator.controller;

import com.techelevator.dao.DeckDao;
import com.techelevator.dao.FlashcardDao;
import com.techelevator.model.Deck;
import com.techelevator.model.Flashcard;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class DeckController {

    private DeckDao deckDao;
    private FlashcardDao flashcardDao;

    public DeckController(DeckDao deckDao, FlashcardDao flashcardDao) {
        this.deckDao = deckDao;
        this.flashcardDao = flashcardDao;
    }

    @RequestMapping(path = "/decks", method = RequestMethod.GET)
    public List<Deck> getDecks() {
        return deckDao.getDecks();
    }
    @RequestMapping(path = "/deck/{deckId}", method = RequestMethod.GET)
    public Deck getDeckByDeckId(int id){
        return deckDao.getDeckById(id);
    }

    @RequestMapping(path = "/decks", method = RequestMethod.POST)
    public Deck createDeck(@RequestBody Deck deck) {
        return deckDao.createDeck(deck);
    }

    @RequestMapping(path = "/flashcards", method = RequestMethod.GET)
    public List<Flashcard> getFlashcards() {
        return flashcardDao.getFlashcards();
    }

    @RequestMapping(path = "/flashcards", method = RequestMethod.POST)
    public Flashcard createFlashcard(@RequestBody Flashcard flashcard) {
        return flashcardDao.createFlashcard(flashcard);
    }
}
