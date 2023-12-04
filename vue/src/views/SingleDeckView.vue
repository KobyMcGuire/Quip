<template>
  <div>
    <div class="deck-info">
        <h1> {{ deck.title }}</h1>
        <h2> {{ deck.description }}</h2>
    </div>

    <button v-on:click="this.showEditDeck = !this.showEditDeck">Edit Deck</button>
    
    <div class="edit-deck-info" v-if="showEditDeck">
        <label for="deckTitle">Title: </label>
        <input type="text" id="deckTitle" name="deckTitle" v-model="deck.title">

        <label for="deckDescription">Description: </label>
        <input type="text" id="deckDescription" name="deckDescription" v-model="deck.description">

        <label for="submitEditedDeck"></label>
        <input type="submit" id="submitEditedDeck" v-on:click="editDeck">
    </div>

    <div class = 'flash-cards-container'>
        <flash-card v-for="flashcard in flashcards" v-bind:key="flashcard.cardId" v-bind:flashcard="flashcard" />
    </div>
  </div>
</template>

<script>
import FlashCard from "../components/FlashCard.vue";
import DeckService from '../services/DeckService';

export default {
  components: { FlashCard },

  data () {
    return {
        flashcards : [],
        deck : {},
        showEditDeck: false,
        editedDeck : {
            title : '',
            description : ''
        }
    }
  },

  methods : {
    editDeck() {
        this.deck.title = this.editedDeck.title;
        this.deck.description = this.editedDeck.description;
    }
  },

  computed : {
  },

  created() {
    // API call to grab the deck by id, using the id from the url
    DeckService.getDeck(this.$route.params.id)
    .then((response) => {
      this.deck = response.data;
    })
    // Fill out this catch with an error handler
    .catch((error) => {
      console.log(error)
    })

    // API Call to grab all flashcards associated with deck
    DeckService.getCards()
    .then((response) => {
      this.flashcards = response.data.filter((flashcard) => flashcard.deckId === this.deck.deckId);
    })
    // Fill out catch with an error handler
    .catch((error) => {
      console.log(error);
    })

  },
};
</script>

<style scoped>

    .flash-cards-container {
        display: flex;
        flex-wrap: wrap;
        gap: 20px;
    }
    
</style>