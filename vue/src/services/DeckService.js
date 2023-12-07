import axios from "axios";

const http = axios.create({
    baseURL: "http://localhost:9000"
})

export default{ 

    // Deck Services
    getDecks(){
        return http.get('/decks');
    },

    getDeck(deckId) {
        return http.get(`/decks/${deckId}`);
    },

    addDeck(deck) {
        return http.post(`/decks`, deck);
    },

    editDeck(deckId, deck) {
        return http.put(`/decks/${deckId}`, deck);
    },

    // Flashcard Services
    getCardsByTag(tag, deckId) {
        return http.get('/flashcards', {params : {tag : tag, deckId : deckId}})
    },

    getCardsByQuestion(question, deckId) {
        return http.get('/flashcards', {params: {question : question, deckId : deckId}})
    },

    getCardsByDeckId(deckId) {
        return http.get(`/flashcards/decks/${deckId}`)
    },

    getCards() {
        return http.get('/flashcards')
    },

    addFlashcard(flashCard) {
        return http.post('/flashcards', flashCard)
    },

    addFlashcardToDeck(deckId, flashcardId) {
        return http.post('/flashcards/decks', {params : {deckId : deckId , flashcardId : flashcardId}})
    },

    getCard(cardId) {
        return http.get(`/flashcards/${cardId}`);
    },

    deleteFlashcard(cardId) {
        return http.delete(`/flashcards/${cardId}`)
    },

    updateFlashcard(cardId, flashCard) {
        return http.put(`/flashcards/${cardId}`, flashCard);
    }
}