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

export default {
  components: { FlashCard },

  data () {
    return {
        flashcards : [],
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
    deck() {
        // Subtract one because arrays are zero indexed
        return this.$store.state.decks[this.$route.params.id - 1];
    }
  },

  created() {
    // Make API call here to get flashcards that pertain to a specific deck
      for (let i = 0; i < this.$store.state.flashcards.length; i++) {
        let currentFlashCard = this.$store.state.flashcards[i];
        if (currentFlashCard.deckId == this.$route.params.id) {
            this.flashcards.push(currentFlashCard);
        }
      }
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