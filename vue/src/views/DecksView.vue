<template>
  <button
    class="show-new-deck"
    v-on:click="showCreateNewDeck = !showCreateNewDeck"
    v-bind:disabled="showCreateNewDeck">Create New Deck</button>

  <div class="new-deck-card" v-if="showCreateNewDeck">
    <h1>Create A New Deck</h1>

    <div class="error-message" v-if="error">
      <p>You must enter a title and a description</p>
    </div>

    <label for="deckTitle">Title: </label>
    <input
      type="text"
      id="deckTitle"
      name="deckTitle"
      v-model="newDeck.title"
    />

    <label for="deckDescription">Description: </label>
    <input
      type="text"
      id="deckDescription"
      name="deckDescription"
      v-model="newDeck.description"
    />

    <div class="new-deck-buttons">
      <label for="submitNewDeck"></label>
      <input type="submit" id="submitNewDeck" v-on:click="addDeck" />

      <button
        class="cancel-button"
        v-on:click="clearNewDeck(), showCreateNewDeck = !showCreateNewDeck"
      >
        Cancel
      </button>
    </div>
  </div>

  <div class="deck-container">
    <deck-card
      class="deck-card"
      v-for="deck in decks"
      v-bind:key="deck.deckId"
      v-bind:deck="deck"
    ></deck-card>
  </div>
</template>

<script>
import DeckCard from "../components/DeckCard.vue";
import DeckService from "../services/DeckService";

export default {
  components: {
    DeckCard,
  },

  methods: {

    clearNewDeck() {
      this.error = false;
      this.newDeck.title = "";
      this.newDeck.description = "";
    },

    // API call to add deck to DB
    addDeck() {
      // Check to make sure that the title and description fields are not empty.
      if (this.newDeck.title !== "" && this.newDeck.description !== "") {
        this.error = false;
        DeckService.addDeck(this.newDeck)
          .then((response) => {
            this.newDeck = {
              title: "",
              description: "",
            };
            this.decks.push(response.data);
          })
          .catch((error) => {
            this.errorHandler(error, "Adding deck");
          });
      } else {
        this.error = true;
      }
    },

    // Make error handler display a message to the site
    errorHandler(error, verb) {
      console.log(`There was an error ${verb}. The error was: ${error}`);
    },
  },

  data() {
    return {
      newDeck: {
        title: "",
        description: "",
      },

      decks: [],
      error: false,
      showCreateNewDeck: false,
    };
  },

  created() {
    DeckService.getDecks()
      .then((response) => {
        this.decks = response.data;
      })
      .catch((error) => {
        this.errorHandler(error, "fetching all decks");
      });
  },
};
</script>

<style scoped>
.show-new-deck {
  margin-bottom: 10px;
  margin-left: 17px;

  color: #F5EFDF;
  background-color: #11101d;

  transition: all .2s ease-in-out;
}

.show-new-deck:hover {
  border: 3px solid black;

  transform: scale(1.025);

  box-shadow: 10px 5px 5px rgb(198, 196, 196);
}


.show-new-deck[disabled] {
  border: none;
}



.deck-container {
  display: flex;
  justify-content:space-around;
  flex-wrap: wrap;
  gap: 15px;
}

.deck-card {
  min-width: 30%;
  min-height: 25%;
  text-align: center;

  color: #F5EFDF;
  background-color: #11101d;

  padding: 10px;

  transition: all .2s ease-in-out;
}

.deck-card:hover {
  transform: scale(1.025);

  box-shadow: 10px 5px 5px rgb(198, 196, 196);
}

.new-deck-card {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;

  width: 30%;

  margin-left: auto;
  margin-right: auto;
  margin-bottom: 10px;

  padding: 10px;
}

/* FIX THIS */
.new-deck-buttons {
  margin-left: 110px;
}
.new-deck-buttons input[type="submit"] {
  margin-right: 15px;

  display: inline-block;
  width: 200px;
}

#submitNewDeck {
  max-width: 30%;

  margin-top: 10px;
}

.new-deck-card input {
  max-width: 70%;
}
</style>