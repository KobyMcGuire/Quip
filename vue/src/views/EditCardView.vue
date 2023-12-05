<template>
  <h1> hello, the card question is {{ flashcard.question }} </h1>
</template>

<script>
import DeckService from "../services/DeckService";

export default {
  data() {
    return {
      flashcard: {
        flashCardId : "",
        deckId: "",
        question: "",
        answer: "",
        tag: "",
        creator: ""
      },
    };
  },

  // API call to set flashcard
  created() {
    DeckService.getCards()
    .then((response) => {
        let flashcards = response.data;

    })
    .catch((error) => {
        this.errorHandler(error, "fetching flashcard");
    })
  },

  methods: {
    // Make error handler display message to site.
    errorHandler(error, verb) {
      console.log(`There was an error ${verb}. The error was: ${error}`);
    },
  },
};
</script>

<style>
</style>