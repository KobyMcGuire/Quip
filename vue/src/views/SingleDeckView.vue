<template>
  <div>
    <div class="deck-info">
      <h1>{{ deck.title }}</h1>
      <h2>{{ deck.description }}</h2>
    </div>

    <button v-on:click="this.showEditDeck = !this.showEditDeck">
      Edit Deck
    </button>

    <div class="edit-deck-info" v-if="showEditDeck">
      <label for="deckTitle">Title: </label>
      <input
        type="text"
        id="deckTitle"
        name="deckTitle"
        v-model="editedDeck.title"
      />

      <label for="deckDescription">Description: </label>
      <input
        type="text"
        id="deckDescription"
        name="deckDescription"
        v-model="editedDeck.description"
      />

      <label for="submitEditedDeck"></label>
      <input type="submit" id="submitEditedDeck" v-on:click="editDeck" />
    </div>

    <div class="new-flashcard-card">
      <h2>Create new Flashcard</h2>

      <div class="error-message" v-if="error">
        <p>You must enter a question and an answer</p>
      </div>

      <label for="newFlashcardQuestion">Question: </label>
      <input
        type="text"
        id="newFlashcardQuestion"
        name="newFlashcardQuestion"
        v-model="newFlashcard.question"
      />

      <label for="newFlashcardAnswer">Answer: </label>
      <input
        type="text"
        id="newFlashcardAnswer"
        name="newFlashcardAnswer"
        v-model="newFlashcard.answer"
      />

      <label for="submitNewFlashcard"></label>
      <input type="submit" id="submitNewFlashcard" v-on:click="addFlashcard" />
    </div>

    <div class="flash-cards-container">
      <flash-card
        v-for="flashcard in flashcards"
        v-bind:key="flashcard.cardId"
        v-bind:flashcard="flashcard"
      />
    </div>
  </div>
</template>

<script>
import FlashCard from "../components/FlashCard.vue";
import DeckService from "../services/DeckService";

export default {
  components: { FlashCard },

  data() {
    return {
      flashcards: [],
      deck: {},
      showEditDeck: false,
      editedDeck: {
        title: "",
        description: "",
      },
      newFlashcard: {
        deckId: "",
        question: "",
        answer: "",
        tag: "",
        creator: "",
      },
      error: false,
    };
  },

  methods: {
    editDeck() {
      this.deck.title = this.editedDeck.title;
      this.deck.description = this.editedDeck.description;

      DeckService.editDeck(this.$route.params.id, this.editedDeck)
        .then((response) => {
          this.editedDeck = "";
        })
        .catch((error) => {
          this.errorHandler(error, "Edited Deck");
        });
    },

    addFlashcard() {
      this.newFlashcard.deckId = this.$route.params.id;
      this.newFlashcard.tag = "UniqueTag";
      this.newFlashcard.creator = "Christian";

      // Check to make sure that the question and answer fields are not empty
      if (
        this.newFlashcard.answer !== "" &&
        this.newFlashcard.question !== ""
      ) {
        this.error = false;
        DeckService.addFlashcard(this.newFlashcard)
          .then((response) => {
            this.newFlashcard = {
              deckId: "",
              question: "",
              answer: "",
              tag: "",
              creator: "",
            };
            this.flashcards.push(response.data);
          })
          .catch((error) => {
            this.errorHandler(error, "Adding flashcard");
          });
      } else {
        this.error = true;
      }
      //TODO add creator info (principle)
    },

    // Make error handler display message to site.
    errorHandler(error, verb) {
      console.log(`There was an error ${verb}. The error was: ${error}`);
    },
  },

  computed: {},

  created() {
    // API call to grab the deck by id, using the id from the url
    DeckService.getDeck(this.$route.params.id)
      .then((response) => {
        this.deck = response.data;
      })
      .catch((error) => {
        this.errorHandler(error, "fetching the current deck");
      });

    // API Call to grab all flashcards associated with deck
    DeckService.getCards()
      .then((response) => {
        this.flashcards = response.data.filter(
          (flashcard) => flashcard.deckId === this.deck.deckId
        );
      })
      .catch((error) => {
        this.errorHandler(
          error,
          "fetching the flashcards associated with the current deck"
        );
      });
  },
};
</script>

<style scoped>
.new-flashcard-card {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;

  padding: 5px;

  margin-top: 10px;
  margin-left: auto;
  margin-right: auto;

  border: 1px solid black;
  border-radius: 5px;

  max-width: 25%;
}

.new-flashcard-card input {
  max-width: 50%;
}

#submitNewFlashcard {
  margin-top: 10px;
}

.flash-cards-container {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;

  margin-top: 10px;
}
</style>