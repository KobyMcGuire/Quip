<template>
  <div>
    <div class="deck-info">
      <h1
        id="deck-header-title"
        v-bind:contenteditable="showEditDeck"
        @keydown.enter.prevent
      >
        {{ deck.title }}
      </h1>
      <h2
        id="deck-header-description"
        v-bind:contenteditable="showEditDeck"
        @keydown.enter.prevent
      >
        {{ deck.description }}
      </h2>
    </div>

    <div class="edit-deck-info" v-if="showEditDeck">
      <div class="form-buttons">
        <label for="submitEditedDeck"></label>
        <input
          type="submit"
          id="submitEditedDeck"
          v-on:click="
            editDeck();
            this.showEditDeck = false;
          "
        />

        <button v-on:click="this.showEditDeck = false" class="cancel-button">
          Cancel
        </button>
      </div>
    </div>

    <div class="form-action-buttons">
      <button class="create-flashcard-button"
        v-on:click="this.showCreateFlashcard = !this.showCreateFlashcard"
        v-bind:disabled="showCreateFlashcard || showEditDeck"
      >
        Create Flashcard
      </button>
      <button class="edit-flashcard-button"
        v-on:click="this.showEditDeck = !this.showEditDeck"
        v-bind:disabled="showCreateFlashcard || showEditDeck"
      >
        Edit Deck
      </button>
    </div>

    <div class="new-flashcard-card" v-if="showCreateFlashcard">
      <div class="error-message" v-if="newCardError">
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

      <div class="form-buttons">
        <label for="submitNewFlashcard"></label>
        <input
          type="submit"
          id="submitNewFlashcard"
          v-on:click="addFlashcard()"
        />

        <button
          v-on:click="clearNewFlashcard(), (showCreateFlashcard = false)"
          class="cancel-button"
        >
          Cancel
        </button>
      </div>
    </div>

    <div
      class="flash-cards-container"
      v-on:drop="onDrop($event)"
      @dragover.prevent
      @dragenter.prevent
    >
      <flash-card
        draggable="true"
        v-on:dragstart="handleDragStart($event)"
        v-on:dragend="handleDragEnd($event)"
        v-for="flashcard in this.$store.state.currentDeckFlashcards"
        v-bind:key="flashcard.cardId"
        v-bind:flashcard="flashcard"
        v-bind:deleteButton="deleteButton"
      />
    </div>

    <hr class="rounded" />

    <search-bar></search-bar>
  </div>
</template>

<script>
import FlashCard from "../components/FlashCard.vue";
import DeckService from "../services/DeckService";
import SearchBar from "../components/SearchBar.vue";

export default {
  components: { FlashCard, SearchBar },

  data() {
    return {
      deck: {},
      // flashcard: {},
      decksFlashcards: {
        deckId: "",
        flashcardId: "",
      },
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
      newCardError: false,
      showCreateFlashcard: false,
      isLoaded: false,

      deleteButton: true,
    };
  },

  methods: {
    clearNewFlashcard() {
      // Clear new flashcard
      this.newFlashcard.question = "";
      this.newFlashcard.answer = "";

      // Reset any errors
      this.newCardError = false;
    },

    handleDragStart(event) {
      event.target.style.opacity = "0.4";
    },

    handleDragEnd(event) {
      event.target.style.opacity = "1";
    },

    onDrop(event) {
      let flashcardId = event.dataTransfer.getData('flashCardId');
      this.findFlashcard(flashcardId);
    },

    findFlashcard(flashcardId) {
      DeckService.getCard(flashcardId)
      .then((response) => {
        this.flashcard = response.data;
        this.addFlashcardToDeckByDragAndDrop();
      })
      .catch((error) => {
        this.errorHandler(error, "fetching card");
      })
    },  

    editDeck() {
      // Grab User input
      let editedTitle = document.getElementById("deck-header-title").innerText;
      let editedDescription = document.getElementById(
        "deck-header-description"
      ).innerText;

      // If either fields are empty, set the empty field to whatever the current deck value is
      if (editedTitle === "") {
        editedTitle = this.deck.title;
      }
      if (editedDescription === "") {
        editedDescription = this.deck.description;
      }

      // Put values in to an object to send back to DB
      this.editedDeck = { title: editedTitle, description: editedDescription };

      DeckService.editDeck(this.$route.params.id, this.editedDeck)
        .then((response) => {
          // Force reactivity without refreshing
          this.deck.title = this.editedDeck.title;
          this.deck.description = this.editedDeck.description;

          // Reset edited deck
          this.editedDeck = {
            title: "",
            description: "",
          };
        })
        .catch((error) => {
          this.errorHandler(error, "Edited Deck");
        });
    },

    addFlashcard() {
      this.newFlashcard.deckId = this.$route.params.id;

      // Creating tags for card
      this.newFlashcard.tag =
        this.deck.title.split(" ")[0] +
        " " +
        this.deck.description.split(" ")[0] +
        " " +
        this.newFlashcard.question.split(" ")[0];

      // TO DO: AFTER LOGIN AND REGISTER IS DONE, SET THIS UP
      this.newFlashcard.creator = "TO-DO";

      // Check to make sure that the question and answer fields are not empty
      if (
        this.newFlashcard.answer !== "" &&
        this.newFlashcard.question !== ""
      ) {
        // Make form disappear and clear new card error
        this.showCreateFlashcard = false;
        this.newCardError = false;

        DeckService.addFlashcard(this.newFlashcard)
          .then((response) => {
            this.newFlashcard = {
              deckId: "",
              question: "",
              answer: "",
              tag: "",
              creator: "",
            };
            this.$store.state.currentDeckFlashcards.push(response.data);
          })
          .catch((error) => {
            this.errorHandler(error, "Adding flashcard");
          });
      } else {
        this.newCardError = true;
      }
    },

    addFlashcardToDeckByDragAndDrop() {
      this.decksFlashcards.deckId = this.$route.params.id;
      this.decksFlashcards.flashcardId = this.flashcard.flashCardId;

      DeckService.addFlashcardToDeck(this.decksFlashcards)
        .then((response) => {
          let indexOfRemovedCard = "";
          for (let i = 0; i < this.$store.state.currentSearchFlashcards.length; i++) {
            if (this.$store.state.currentSearchFlashcards[i].flashCardId === this.flashcard.flashCardId) {
              indexOfRemovedCard = i;
            }
          }

          // Add flashcard to current decks flashcards
          this.$store.state.currentDeckFlashcards.push(
            this.$store.state.currentSearchFlashcards[indexOfRemovedCard]
          );

          // Remove the flashcard from the current search flashcards array
          this.$store.state.currentSearchFlashcards.splice(
            indexOfRemovedCard,
            1
          );
        })
        .catch((error) => {
          this.errorHandler(error, "putting card in to new deck");
        });
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
    DeckService.getCardsByDeckId(this.$route.params.id)
      .then((response) => {
        this.$store.state.currentDeckFlashcards = response.data;
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
.form-container {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;

  padding: 5px;

  margin-top: 10px;
  margin-left: auto;
  margin-right: auto;

  max-width: 25%;
}

.new-flashcard-card {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.new-flashcard-card input {
  width: 150px;
}

#submitNewFlashcard {
  width: 75px;

  margin-top: 10px;
}

.flash-cards-container {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  gap: 20px;

  margin-top: 10px;
}

.deck-info {
  margin-left: 15px;
}

.edit-deck-info {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.edit-deck-info input {
  width: 150px;
}

#submitEditedDeck {
  margin-top: 5px;

  width: 75px;
}

.form-action-buttons {
  display: flex;
  gap: 10px;

  margin-left: 15px;
}

.cancel-button {
  margin-left: 10px;
}

.rounded {
  border-top: 3px solid #bbb;
  border-radius: 5px;

  width: 70%;

  margin-top: 3%;
  margin-bottom: 3%;
}

.edit-flashcard-button{
  background-color: #f3f6f4;

}

.create-flashcard-button{
  background-color: #f3f6f4;

}

</style>