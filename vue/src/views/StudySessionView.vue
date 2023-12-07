<template>
  <div>
    <h1>Pick a deck and begin study session</h1>

    <select class="dropDownButton" v-model="selectedDeck">
      <option v-for="deck in decks" :key="deck.deckId" :value="deck">
        {{ deck.title }}
      </option>
    </select>

    <div v-if="selectedDeck">
      <div class="cardMovementButtons">
        <div class="previousButton" @click="previousCard">
          <button> Previous Card</button>
        </div>
        <div class="nextButton" @click="nextCard">
          <button>Next Card</button>
        </div>
      </div>

      <h5>Correct Answers: {{ correctAnswers }} </h5>

      <div class="viewedQuestion">
        {{ cards[currentCardIndex].question }}
      </div>

      <div class="answer-container">
        <div
            v-for="(answer, index) in randomAnswers" :key="index" class="answer-item">
          {{ answer }}
          <button>
            <input type="radio" :name="'answerButton'">
          </button>
        </div>
      </div>
      <div class="study-submit-button">
          <input type="submit" v-on:click="checkAnswer">
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
      randomAnswers: [],
      currentCardIndex: 0,
      correctAnswers: 0,
      incorrectAnswers: 0
    };
  },

  methods: {
    nextCard() {
      if (this.currentCardIndex < this.cards.length - 1) {
        this.currentCardIndex++
        this.randomAnswers = this.getRandomAnswers();
      }
    },

    previousCard() {
      if (this.currentCardIndex < this.cards.length - 1) {
        this.currentCardIndex--;
      }
      this.randomAnswers = this.getRandomAnswers();
    },

    getRandomAnswers() {
      return this.cards
          .sort(() => Math.random() - 0.5)
          .map(card => card.answer)
          .slice(0, 4);
    },
    checkAnswer(index){
      const userAnswer = this.selectedAnswer[index];
      const correctAnswer = this.cards[this.currentCardIndex].answer;
      console.log(userAnswer);
      if(userAnswer === correctAnswer){
        this.correctAnswers++;
        this.moveToNextCard();
      }
    }
    //   }
    // else {
    //   // The answer is incorrect
    //   this.handleIncorrectAnswer();
    // }

    // handleIncorrectAnswer(){
    // this.incorrectAnswers++;
    // this.feedbackMessage = `Sorry, that is incorrect. The correct answer is: ${this.cards[this.currentCardIndex].answer}`;
    // },
    // moveToNextCard(){
      // if (this.currentCardIndex < this.cards.length - 1) {
      //   this.currentCardIndex++;
      //   this.selectedAnswer = [];
    // }else{
      // showSessionSummary() {
    // Implement logic to show a summary of the session
    // For example, display the number of correct answers
  },

  created() {
    this.randomAnswers = this.getRandomAnswers();

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
              this.currentCardIndex = 0;
              this.randomAnswers = this.getRandomAnswers();
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

  text-align: center;
  justify-content: center;

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
