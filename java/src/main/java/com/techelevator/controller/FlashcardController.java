//package com.techelevator.controller;
//
//import com.techelevator.dao.FlashcardDao;
//import com.techelevator.model.Deck;
//import com.techelevator.model.Flashcard;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@PreAuthorize("isAuthenticated()")
//public class FlashcardController {
//
//    private FlashcardDao flashcardDao;
//
//    public FlashcardController(FlashcardDao flashcardDao){
//        this.flashcardDao = flashcardDao;
//    }
//
//    @RequestMapping(path = "/flashcards", method = RequestMethod.GET)
//    public List<Flashcard> getFlashcards() {
//        return flashcardDao.getFlashcards();
//    }
//
//    @RequestMapping(path = "/flashcards", method = RequestMethod.POST)
//    public Flashcard createFlashcard(@RequestBody Flashcard flashcard) {
//        return flashcardDao.createFlashcard(flashcard);
//    }
//}
