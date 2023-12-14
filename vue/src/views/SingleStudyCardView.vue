<template>
  <div class="main-study-container">
    <div class="header-content">
      <h1 class="study-header">Pick a Deck to Study</h1>

      <select
        class="dropDownButton"
        v-model="selectedDeck"
      >
        <option v-for="deck in decks" :key="deck.deckId" :value="deck">
          {{ deck.title }}
        </option>
      </select>
    </div>

    <div @click="flipCard()" v-if="selectedDeck && cards.length > 0" class="flashcard-container">
      <div class="flashcard">
        <p v-if="!flipped">{{ cards[currentIndex].question }}</p>
        <p v-else>{{ cards[currentIndex].answer }}</p>
      </div>
    </div>

    <div class="buttons" v-if="selectedDeck">
      <button @click="currentIndex > 0 && currentIndex--">Previous</button>
      <button @click="currentIndex < cards.length - 1 && currentIndex++">
        Next
      </button>
    </div>
  </div>

</template>

<script>
import DeckService from "../services/DeckService";

export default {
  components: {},

  data() {
    return {
      selectedDeck: null,
      showCards: false,
      flipped: false,
      decks: [],
      cards: [],
      currentIndex: 0,
    };
  },

  methods: {
    flipCard() {
      this.flipped = !this.flipped;
    },

    errorHandler(error, verb) {
      console.log(`There was an error ${verb}. The error was: ${error}`);
    },
  },

  created() {
    DeckService.getDecks()
      .then((response) => {
        this.decks = response.data;
      })
      .catch((error) => {
        console.log(error, "Deck selection");
      });
  },

  watch: {
    selectedDeck(newDeck) {
      if (newDeck) {
        DeckService.getCardsByDeckId(newDeck.deckId)
          .then((response) => {
            this.cards = response.data;
            this.showCards = true;
          })
          .catch((error) => {
            console.log(error, "Card selection");
          });
      }
    },
  },
};
</script>


<style scoped>

.main-study-container {

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  min-width: 80%;
  min-height: 80%;
}
.header-content {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.flashcard-container {
  margin-top: 15px;

  min-width: 100%;
  min-height: 100%;
}

.flashcard {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  max-width: 40%;
  min-height: 350px;

  border: 3px solid black;
  border-radius: 10px;

  background-color: rgb(251, 249, 249);

  text-align: center;

  padding: 10px;

  margin-left: auto;
  margin-right: auto;
}

.buttons {
  margin-top: 15px;

  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
}

.buttons > button {
  color: rgb(251, 249, 249);
  background-color: #11101d;
}

.header-content > h1 {
  margin-top: 0;
}

.dropDownButton {
  min-width: 200px;
  height: 30px;
  border-radius: 15px;
}
p {
  font-size: large;
}
</style>