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
    </div>
    <div>
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
    };
  },
  computed: {},

  methods: {
    flipCard() {
      this.showFront = !this.showFront;
    },
    deleteFlashcard() {
      console.log(this.flashcard);
      DeckService.deleteFlashcard(this.flashcard.flashCardId)
        .then((response) => {
          this.$router.go();
        })
        .catch((error) => {
          this.errorHandler(error, "Flashcard Deleting");
        });
    },
    errorHandler(error, verb) {
      console.log(`There was an error ${verb}. The error was: ${error}`);
    },
  },
};
</script>

<style scoped>
.flashcard-card {
  min-width: 25%;
  min-height: 20%;

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
</style>