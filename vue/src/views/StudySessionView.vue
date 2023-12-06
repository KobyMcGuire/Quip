<template>
  <div>
    <h1>Pick a deck and begin study session</h1>

    <select class="dropDown" v-model="selectedDeck">
      <option v-for="deck in decks" :key="deck.deckId" :value="deck">
        {{ deck.title }}
      </option>
    </select>

    <div v-if="selectedDeck">
      <div class="cardMovementButtons">
        <div class="previousButton" @click="currentCardIndex--">
          <button> Previous Card</button>
        </div>
        <div class="nextButton" @click="currentCardIndex++">
          <button>Next Card</button>
        </div>
      </div>

      <div>
        {{ cards[currentCardIndex].question }}
      </div>
    </div>

  </div>
</template>

<script>
import DeckService from "@/services/DeckService";
import DeckCard from "@/components/DeckCard.vue";

export default {
  components: DeckCard,
  data() {
    return {
      selectedDeck: null,
      decks: [],
      cards: [],
      currentCardIndex: 0,
    };
  },

  created() {
    DeckService.getDecks()
        .then((response) => {
          this.decks = response.data;
        })
        .catch((error) => {
          console.log(error, "Deck selection");
        });

    DeckService.getCardsByDeckId()
        .then((response) => {
          this.decks = response.data;
        })
  },

  watch: {
    selectedDeck(newDeck) {
      if (newDeck) {
        DeckService.getCardsByDeckId(newDeck.deckId)
            .then((response) => {
              this.cards = response.data;
            })
            .catch((error) => {
              console.log(error, "Card selection");
            });
      }
    }
  }
};
</script>

<style scoped>
.cardMovementButtons {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
  gap: 2px;
}
</style>
