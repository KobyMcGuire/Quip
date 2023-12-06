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
      <input type="submit" v-on:click="searchFlashCardsByTag" />
    </div>

    <div class="flashcard-results-container">
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
import DeckService from '../services/DeckService';
export default {
    components:{ FlashCard
    },

    data() {
    return {
      searchTerms: "",
      flashcards: [],
      searchByQuestion: "",
    };
  },
  methods: {
    searchFlashCardsByTag() {
      if (this.searchByQuestion === false) {
        DeckService.getCardsByTag(this.searchTerms, this.$route.params.id)
          .then((response) => {
            this.flashcards = response.data;
          })
          .catch((error) => {
            this.errorHandler(error, "fetching for searched flashcards");
          });
      } else {
        DeckService.getCardsByQuestion(this.searchTerms, this.$route.params.id)
          .then((response) => {
            this.flashcards = response.data;
          })
          .catch((error) => {
            this.errorHandler(error, "fetching for searched flashcards");
          });
      }
    },

    errorHandler(error, verb) {
      console.log(`There was an error ${verb}. The error was: ${error}`);
    },
  }

}
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