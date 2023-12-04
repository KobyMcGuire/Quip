import axios from "axios";

const http = axios.create({
    baseURL: "http://localhost:9000"
})

export default{ 

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

    // Add get all cards by ID back-end method?
    getCards() {
        return http.get('/flashcards')
    },

    addFlashcard(flashCard) {
        return http.post('/flashcards', flashCard)
    },

    getCard(cardId) {
        return http.get(`/flashcards/${cardId}`);
    },

    deleteFlashcard(cardId) {
        return http.delete(`/flashcards/${cardId}`)
    }
}