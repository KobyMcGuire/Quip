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
      <label for="newFlashcardQuestion">Tags (Enter words separated by spaces) </label>
      <input
        type="text"
        id="newFlashcardQuestion"
        name="newFlashcardQuestion"
        v-model="editedFlashcard.tag"
      />

      <input class="submit-button" type="submit" v-on:click="updateCard"/>
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
        question: "",
        answer: "",
        tag: "",
        creator: "",
      },
      editedFlashcard: {
        flashCardId: "",
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

        // Setting fields that still need to be set
        this.editedFlashcard.flashCardId = this.flashcard.flashCardId;
        this.editedFlashcard.creator = this.flashcard.creator;


        DeckService.updateFlashcard(this.editedFlashcard.flashCardId, this.editedFlashcard)
        .then((response) => {
            // Push them back to deck page
            this.$router.go(-1);
        })
        .catch((error) => {
            this.errorHandler(error, "updating card")
        })
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