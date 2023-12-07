<template>
  <div>
    <h1>Pick a deck and begin study session</h1>

    <select class="dropDownButton" v-model="selectedDeck" v-bind:disabled="selectedDeck">
      <option v-for="deck in decks" :key="deck.deckId" :value="deck">
        {{ deck.title }}
      </option>
    </select>
    <button class="resetButton" v-if="selectedDeck" @click="resetStudySession">
      Cancel Study Session
    </button>

    <div v-if="selectedDeck">
      <h5>Correct Answers: {{ correctAnswers }} / {{cards.length}}</h5>

      <div class="viewedQuestion">
        {{ cards[currentCardIndex] && cards[currentCardIndex].question }}
      </div>

      <div class="answer-container">
        <button
            v-for="(answer, index) in randomAnswers" :key="index" class="answer-item" @click="changeUserAnswer($event)">
          <div>
            {{ answer }}
          </div>
        </button>
      </div>
    </div>

    <div class="study-submit-button">
      <input type="submit" @click="submitAndMoveNext">
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
      selectedAnswer: null,
      decks: [],
      cards: [],
      randomAnswers: [],
      currentCardIndex: 0,
      correctAnswers: 0,
      wrongAnswers: 0,
    };
  },

  methods: {
    submitAndMoveNext() {
      if (this.selectedAnswer !== null) {
        this.checkAnswer(this.currentCardIndex);
        this.nextCard();
        this.selectedAnswer = null;
      }
    },

    nextCard() {
      if (this.currentCardIndex < this.cards.length - 1) {
        this.currentCardIndex++
        this.randomAnswers = this.getRandomAnswers();
      }
    },

    getRandomAnswers() {
      console.log(this.cards[this.currentCardIndex].answer)
      const correctAnswer = this.cards[this.currentCardIndex].answer;

      const wrongAnswers = this.cards
          .filter(card => card.answer !== correctAnswer)
          .map(card => card.answer);

      const shuffleWrongAnswers = wrongAnswers.sort(() => Math.random() - 0.5);
      const selectedWrongAnswers = shuffleWrongAnswers.slice(0, 3);

      const allAnswers = [correctAnswer, ...selectedWrongAnswers];
      return allAnswers.sort(() => Math.random() - 0.5);
    },

    checkAnswer(index) {
      const correctAnswer = this.cards[index].answer;
      if (this.selectedAnswer === correctAnswer) {
        this.correctAnswers++;
      }
    },

    changeUserAnswer(event) {
      this.selectedAnswer = event.target.innerText;
    },

    resetStudySession() {
      this.selectedDeck = null;
      this.correctAnswers = 0;

    }

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
              console.log(response.data)
              this.currentCardIndex = 0;
              this.randomAnswers = this.getRandomAnswers();
            })
            .catch((error) => {
              console.log(error, "Card selection");
            });
      }
    }
  }
}

</script>

<style scoped>
.cardMovementButtons {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
  gap: 2px;
}

.viewedQuestion {
  width: 250px;
  height: 250px;
  min-width: 25%;
  max-width: 30%;
  min-height: 25%;
  max-height: 30%;
  margin-left: 35%;

  border: 3px solid black;
  border-radius: 10px;
  display: flex;

  text-align: center;
  justify-content: center;
  align-items: center;

  padding: 10px;
}

.answer-container {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 10px;
  padding-top: 20px;
}

.answer-item {
  border: 1px solid black;
  padding: 10px;
  text-align: center;
}

</style>
