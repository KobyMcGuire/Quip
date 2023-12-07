<template>
  <div class="flashcard-container">
    <div class="flashcard-text-content" v-on:click="flipCard">
      <div class="question" v-if="showFront">
        <h1>Question:</h1>
        <p>{{ flashcard.question }}</p>
      </div>

      <div class="answer" v-if="!showFront">
        <h1>Answer:</h1>
        <p>{{ flashcard.answer }}</p>
      </div>

      <div class="flashcard-tags" v-if="displayTags">
        <p>{{ flashcard.tag }}</p>
      </div>
    </div>

    <div class="flashcard-buttons">
      <button v-on:click="this.displayTags = !this.displayTags">
        <span class="material-symbols-outlined"> visibility </span>
      </button>

      <router-link
        v-bind:to="{
          name: 'edit-card',
          params: { id: this.flashcard.flashCardId },
        }"
      >
        <button>
          <span class="material-symbols-outlined"> edit </span>
        </button>
      </router-link>

      <button v-if="deleteButton" v-on:click="deleteFlashcard">
        <span class="material-symbols-outlined"> delete </span>
      </button>
      <!-- TO DO -->
      <button v-else>
        <span class="material-symbols-outlined"> add </span>
      </button>
    </div>
  </div>
</template>

<script>
import DeckService from "@/services/DeckService";

export default {
  props: ["flashcard", "deleteButton"],

  data() {
    return {
      showFront: true,
      editedFlashcard: {
        question: this.flashcard.question,
        answer: this.flashcard.answer,
        tag: this.flashcard.tag,
      },
      editFlashcardError: false,
      displayTags: false,
    };
  },
  computed: {},

  methods: {
    flipCard() {
      this.showFront = !this.showFront;
    },

    // Delete Flashcard
    deleteFlashcard() {
      DeckService.deleteFlashcard(this.flashcard.flashCardId)
        .then((response) => {
          this.$router.go();
        })
        .catch((error) => {
          this.errorHandler(error, "deleting flashcard");
        });
    },

    errorHandler(error, verb) {
      console.log(`There was an error ${verb}. The error was: ${error}`);
    },
  },
};
</script>

<style scoped>
.flashcard-container {
  min-width: 25%;
  min-height: 30%;

  border: 3px solid black;
  border-radius: 10px;

  text-align: center;

  padding: 10px;
}

.flashcard-container:hover {
  cursor: pointer;
}

h1 {
  font-size: large;
}

.flashcard-buttons {
  display: flex;
  justify-content: center;
  gap: 15px;
}

.submit-flashcard-edit {
  margin-top: 10px;
}
</style>