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

    // Add get all cards by ID back-end method?
    getCards() {
        return http.get('/flashcards')
    },

    getCard(cardId) {
        return http.get(`/flashcards/${cardId}`);
    }

}