<template>
  <div class="header-content">
    <h1 class="study-header" v-if="!selectedDeck">Pick a Deck to Study</h1>
    <select class="dropDownButton" v-model="selectedDeck" v-if="!selectedDeck">
      <option v-for="deck in decks" :key="deck.deckId" :value="deck">
        {{ deck.title }}
      </option>
    </select>
  </div>

  <div class="buttons">
    <button @click="currentIndex > 0 && currentIndex--">Previous</button>
    <button @click="currentIndex < cards.length - 1 && currentIndex++">Next</button>
    <button @click="selectedDeck = false">Back to Decks</button>
  </div>

  <div class="main-study-container">
    <div class="cardBox" @click="flipCard()" v-if="selectedDeck && cards.length > 0">
        <div class="card1" v-for="(card, index) in cards" :key="index">
          <h2 v-if="!flipped">{{ cards[currentIndex].question }}</h2>
          <p v-else>{{ cards[currentIndex].answer }}</p>
        </div>
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
  }
};
</script>


<style scoped>

.header-content {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.header-content > h1 {
  margin-top: 0;
}

.dropDownButton {
  width: 20%;
  height: 30px;
  border-radius: 15px;
}

.main-study-container {
  height: 100vh;
}

.cardBox {
  width: 50%;
  height: 100%;
  display: flex;
  align-items: flex-start;
  justify-items: flex-start;

}

.card1 {
  height: 100%;
  text-align: center;
}


</style>