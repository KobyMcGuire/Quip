<template>
  <div class="main-container">
    <div class="search-field">
      <label for="flashcardSearch"> Search </label>
      <input
        type="text"
        id="flashcardSearch"
        name="flashcardSearch"
        v-model="searchTerms"
      />

      <div class="radioButtons">
        <label for="question">Question </label>
        <input
          type="radio"
          id="question"
          name="SearchByQuestionOrTag"
          v-on:click="searchByQuestion = true"
          checked="true"
        />
        <label for="tag">Tag </label>
        <input
          type="radio"
          id="tag"
          name="SearchByQuestionOrTag"
          v-on:click="searchByQuestion = false"
        />
      </div>
      <input type="submit" v-on:click="searchFlashCards" />
    </div>

    <div
      class="flashcard-results-container"
      v-on:drop="onDrop($event)"
      @dragover.prevent
      @dragenter.prevent
    >
      <flash-card
        draggable="true"
        v-on:dragstart="handleDragStart($event, flashcard)"
        v-on:dragend="handleDragEnd($event)"
        v-for="flashcard in this.$store.state.currentSearchFlashcards"
        v-bind:key="flashcard.cardId"
        v-bind:flashcard="flashcard"
        v-bind:deleteButton="deleteButton"
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
      searchTerms: "",
      searchByQuestion: "",
      deleteButton: false,
    };
  },

  methods: {
    searchFlashCards() {
      // Grab an array of current flashcard ids
      let currentFlashcardIds = "";
      for (let i = 0; i < this.$store.state.currentDeckFlashcards.length; i++) {
        if (i === this.$store.state.currentDeckFlashcards.length - 1) {
          currentFlashcardIds =
            currentFlashcardIds +
            `${this.$store.state.currentDeckFlashcards[i].flashCardId}`;
        } else {
          currentFlashcardIds = currentFlashcardIds =
            currentFlashcardIds +
            `${this.$store.state.currentDeckFlashcards[i].flashCardId}` +
            ",";
        }

        console.log(this.$store.state.currentSearchFlashcards);
      }

      if (this.searchByQuestion === false) {
        DeckService.getCardsByTag(this.searchTerms, currentFlashcardIds)
          .then((response) => {
            console.log("Data from search", response.data);
            this.$store.state.currentSearchFlashcards = response.data;
          })
          .catch((error) => {
            this.errorHandler(error, "fetching for searched flashcards");
          });
      } else {
        DeckService.getCardsByQuestion(this.searchTerms, currentFlashcardIds)
          .then((response) => {
            this.$store.state.currentSearchFlashcards = response.data;
          })
          .catch((error) => {
            this.errorHandler(error, "fetching for searched flashcards");
          });
      }
    },

    handleDragStart(event, flashcard) {
      event.target.style.opacity = "0.4";

      event.dataTransfer.dropEffect = "move";
      event.dataTransfer.effectAllowed = "move";
      event.dataTransfer.setData("flashCardId", flashcard.flashCardId);

      let flashcardsContainer = document.querySelector(
        ".flash-cards-container"
      );
      flashcardsContainer.style.border = "1px solid black";
    },

    handleDragEnd(event) {
      event.target.style.opacity = "1";

      let flashcardsContainer = document.querySelector(
        ".flash-cards-container"
      );
      flashcardsContainer.style.border = "none";
    },

    onDrop(event) {
      let flashcardId = event.dataTransfer.getData('flashCardId');
      this.findFlashcard(flashcardId);
    },

    findFlashcard(flashcardId) {
      DeckService.getCard(flashcardId)
      .then((response) => {
        this.flashcard = response.data;
        // Delete card from current Deck
        this.deleteFlashcardByDragAndDrop();
      })
      .catch((error) => {
        this.errorHandler(error, "fetching card");
      })
    },

    deleteFlashcardByDragAndDrop() {
      DeckService.deleteFlashcard(
        this.flashcard.flashCardId,
        this.$route.params.id
      )
        .then((response) => {
          let indexOfRemovedCard = "";
          for (let i = 0; i < this.$store.state.currentDeckFlashcards.length; i++) {
            if (this.$store.state.currentDeckFlashcards[i].flashCardId === this.flashcard.flashCardId) {
              indexOfRemovedCard = i;
            }
          }

          // Update search store flashcards array to include deleted card
          this.$store.state.currentSearchFlashcards.push(
            this.$store.state.currentDeckFlashcards[indexOfRemovedCard]
          );

          // Remove card from the stores current deck flashcards array
          this.$store.state.currentDeckFlashcards.splice(indexOfRemovedCard, 1);
        })
        .catch((error) => {
          this.errorHandler(error, "deleting flashcard");
        });
    },

    errorHandler(error, verb) {
      console.log(`There was an error ${verb}. The error was: ${error}`);
    },
  },

  created() {
    // Reset current search flashcards array in store
    this.$store.state.currentSearchFlashcards = [];
  },
};
</script>

<style scoped>
.main-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 15px;
}

.flashcard-results-container {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;

  gap: 20px;
}

.radioButtons {
  display: flex;
  justify-content: center;
}

.radioButtons input[id="question"] {
  margin-right: 5%;
}

.search-field label {
  margin-right: 5px;
}

.search-field input[type="text"] {
  width: 200px;
}

.search-field input[type="submit"] {
  display: block;

  margin-top: 10px;
  margin-left: auto;
  margin-right: auto;
}
</style>