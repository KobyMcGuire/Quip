<template>
  <div class="edit-card-form">
    <h1>Edit Flashcard Fields</h1>

    <div class="input-fields">
      <label for="newFlashcardQuestion">Question </label>
      <input
        type="text"
        id="newFlashcardQuestion"
        name="newFlashcardQuestion"
        v-model="editedFlashcard.question"
      />

      <label for="newFlashcardAnswer">Answer </label>
      <input
        type="text"
        id="newFlashcardAnswer"
        name="newFlashcardAnswer"
        v-model="editedFlashcard.answer"
      />
      <label for="newFlashcardQuestion">Tags </label>
      <input
        type="text"
        id="newFlashcardQuestion"
        name="newFlashcardQuestion"
        v-model="editedFlashcard.tag"
      />

      <input class="submit-button" type="submit" v:on:click="updateCard" />
    </div>
  </div>
</template>

<script>
import DeckService from "../services/DeckService";

export default {
  data() {
    return {
      flashcard: {
        flashCardId: "",
        deckId: "",
        question: "",
        answer: "",
        tag: "",
        creator: "",
      },
      editedFlashcard: {
        flashCardId: "",
        deckId: "",
        question: "",
        answer: "",
        tag: "",
        creator: "",
      },
    };
  },

  // API call to set flashcard
  created() {
    DeckService.getCard(this.$route.params.id)
      .then((response) => {
        this.flashcard = response.data;
      })
      .catch((error) => {
        this.errorHandler(error, "fetching flashcard");
      });
  },

  methods: {
    // Make error handler display message to site.
    errorHandler(error, verb) {
      console.log(`There was an error ${verb}. The error was: ${error}`);
    },

    updateCard() {
        // Check what form fields are empty
        if (this.editedFlashcard.question === "") {
            this.editedFlashcard.question = this.flashcard.question;
        }

        if (this.editedFlashcard.answer === "") {
            this.editedFlashcard.answer = this.flashcard.answer;
        }

        if (this.editedFlashcard.tag === "") {
            this.editedFlashcard.tag = this.flashcard.tag;
        }
    },
  }
}
</script>

<style scoped>
.edit-card-form {
  margin-left: auto;
  margin-right: auto;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  width: 50%;
  padding: 10px;

  border-radius: 10px;

  background-color: #f5f5f4;
}

.input-fields {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.submit-button {
  margin-top: 10px;

  max-width: 125px;
}

.edit-card-form input {
  width: 200px;
}
</style>