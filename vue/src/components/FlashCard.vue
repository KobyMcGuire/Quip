<template>
  <div class="flashcard-card">
    <div v-on:click="flipCard">
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
        View Tags
      </button>
      <router-link
        v-bind:to="{
          name: 'edit-card',
          params: { id: this.flashcard.flashCardId },
        }"
      >
        <button>Edit Card</button>
      </router-link>
      <button v-on:click="deleteFlashcard">Delete Card</button>
    </div>
  </div>
</template>

<script>
import DeckService from "@/services/DeckService";

export default {
  props: ["flashcard"],

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

    editFlashcard() {
      console.log(this.flashcard.flashCardId);

      // Check fields to make sure none are empty
      if (
        this.editedFlashcard.question === "" ||
        this.editedFlashcard.answer === "" ||
        this.editedFlashcard.tag === ""
      ) {
        this.editFlashcardError = true;
        console.log(this.flashcard.flashCardId);
        return;
      }

      // Apply "still-needed" fields to edited flashcard
      // this.editedFlashcard.flashCardId = this.flashcard.flashCardId;
      // this.editedFlashcard.deckId = this.flashcard.deckId;
      // this.editedFlashcard.creator = this.flashcard.creator;

      // API call to update flashcard
      DeckService.updateFlashcard(
        // this.flashcard.flashCardId,
        this.editedFlashcard.flashCardId,
        this.editedFlashcard
      )
        .then((response) => {
          // Close modal
          this.closeModal();

          // Reset edited flashcard fields
          // this.editedFlashcard = {
          //   flashCardId: "",
          //   deckId: "",
          //   question: "",
          //   answer: "",
          //   tag: "",
          //   creator: "",
          // };
        })
        .catch((error) => {
          this.errorHandler(error, "updating flashcard");
        });
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
/* The Modal */
.modal {
  display: none;
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: rgb(0, 0, 0);
  background-color: rgba(0, 0, 0, 0.4);
}

/* Modal Content/Box */
.modal-content {
  background-color: #fefefe;
  margin: 15% auto; /* 15% from the top and centered */
  padding: 20px;
  border: 1px solid #888;
  width: 30%; /* Could be more or less, depending on screen size */
}

.inner-modal-content {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.inner-modal-content label {
  margin-top: 10px;
}

/* The Close Button */
.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}

.flashcard-card {
  min-width: 25%;
  min-height: 30%;

  border: 3px solid black;
  border-radius: 10px;

  text-align: center;

  padding: 10px;
}

.flashcard-card:hover {
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