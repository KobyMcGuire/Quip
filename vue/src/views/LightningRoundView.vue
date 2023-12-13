<template>
  <div class="main-lightning-round-container">
    <div class="header-content">
      <h1 class="lightning-round-header" v-if="!selectedDeck">
        Pick a Deck to Begin a Lightning Round
      </h1>

      <select
        class="dropDownButton"
        v-model="selectedDeck"
        v-if="!selectedDeck"
      >
        <option v-for="deck in decks" :key="deck.deckId" :value="deck">
          {{ deck.title }}
        </option>
      </select>
    </div>

    <div v-if="selectedDeck">
      <h5>
        Correct Answers: {{ this.$store.state.correctAnswers }} /
        {{ cards.length }}
      </h5>
      <div v-if="timerVisible">
        <p>Time remaining: {{ remainingTime }} seconds</p>
      </div>

      <div
        class="viewedQuestion"
        :draggable="!dragged"
        @dragstart="handleDragStart"
        @dragend="handleDragEnd"
      >
        {{ cards[currentCardIndex] && cards[currentCardIndex].question }}
      </div>

      <div class="answer-container">
        <button
          tabindex="0"
          v-for="(answer, index) in randomAnswers"
          :key="index"
          class="answer-item"
          @click="
            changeUserAnswer($event);
            markAnswerSelected($event);
          "
          @keyup="handleKeyPress($event)"
          @drop="handleDrop"
          @dragover="handleDragOver"
        >
          {{ answer }}
        </button>
      </div>
    </div>

    <div class="lightning-round-buttons" v-if="selectedDeck">
      <router-link
        v-bind:correctAnswers="correctAnswers"
        :to="{ name: 'completed-study-session' }"
      >
        <button class="cancel-button" v-if="selectedDeck" v-on:click="handleLeaveEarly">
          Leave Quiz
        </button>
      </router-link>
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
      dragged: false,
      selectedAnswer: null,
      decks: [],
      cards: [],
      randomAnswers: [],
      currentCardIndex: 0,
      correctAnswers: 0,
      wrongAnswers: 0,
      roundDuration: 5,
      timerId: null,
      remainingTime: 0,
      timerVisible: false,
    };
  },
  methods: {
    handleDragStart(event) {
      event.dataTransfer.setData("text/plain", "question-card");
      this.dragged = true;
      console.log("HandleDragStart");
    },
    handleDragEnd() {
      this.dragged = false;
    },

    handleDrop(event) {
      event.preventDefault();
      if (this.dragged) {
        const data = event.dataTransfer.getData("text/plain");
        if (data === "question-card") {
          this.submitAndMoveNext();
          this.clearSelectedAnswer();
        }
      }
    },
    handleDragOver(event) {
      event.preventDefault();
      if (this.dragged) {
        const rect = event.currentTarget.getBoundingClientRect();
        const offsetX = event.clientX - rect.left;
        const offsetY = event.clientY - rect.top;

        const isOverAnswer =
          offsetX > 0 &&
          offsetX < rect.width &&
          offsetY > 0 &&
          offsetY < rect.height;
        if (isOverAnswer) {
          this.selectedAnswer = event.currentTarget.innerText;
          this.markAnswerSelected();
        } else {
          this.clearSelectedAnswer();
        }
      }
    },

    handleKeyPress(event) {
      switch (event.key) {
        case "ArrowLeft":
          this.selectedAnswer = this.randomAnswers[0];
          this.markAnswerSelected();
          this.submitAndMoveNext();
          break;
        case "ArrowRight":
          this.selectedAnswer = this.randomAnswers[1];
          this.markAnswerSelected();
          this.submitAndMoveNext();
          break;
      }
      this.clearSelectedAnswer();
    },

    submitAndMoveNext() {
      if (this.selectedAnswer !== null) {
        this.checkAnswer(this.currentCardIndex);

        if (this.currentCardIndex < this.cards.length - 1) {
          // Move to the next card
          this.nextCard();
        } else {
          // Navigate to the CompletedView
          this.navigateToCompletedView();
        }

        this.selectedAnswer = null;
        this.startTimer();
      }
    },

    nextCard() {
      if (this.currentCardIndex < this.cards.length - 1) {
        this.currentCardIndex++;
        this.randomAnswers = this.getRandomAnswers();
      }
    },

    getRandomAnswers() {
      console.log(this.cards[this.currentCardIndex].answer);
      const correctAnswer = this.cards[this.currentCardIndex].answer;

      const wrongAnswers = this.cards
        .filter((card) => card.answer !== correctAnswer)
        .map((card) => card.answer);

      const shuffleWrongAnswers = wrongAnswers.sort(() => Math.random() - 0.5);
      const selectedWrongAnswers = shuffleWrongAnswers.slice(0, 1);

      const allAnswers = [correctAnswer, ...selectedWrongAnswers];
      return allAnswers.sort(() => Math.random() - 0.5);
    },

    shuffleCards(array) {
      let cardLength = array.length,
        currentIndex;
      for (currentIndex = cardLength - 1; currentIndex > 0; currentIndex--) {
        let randomIndex = Math.floor(Math.random() * (currentIndex + 1));
        let temp = array[currentIndex];
        array[currentIndex] = array[randomIndex];
        array[randomIndex] = temp;
      }
    },

    checkAnswer(index) {
      const correctAnswer = this.cards[index].answer;
      if (this.selectedAnswer === correctAnswer) {
        this.$store.state.correctAnswers++;
      }
      // Push incorrect question to the store
      else {
        this.$store.state.incorrectQuestions.push(this.cards[index].question);
      }
    },

    changeUserAnswer(event) {
      this.selectedAnswer = event.target.innerText;
    },

    handleLeaveEarly() {
      // Push remaining questions into incorrect questions array
      for (let i = this.currentCardIndex; i < this.cards.length; i++) {
        this.$store.state.incorrectQuestions.push(this.cards[i].question);
      }
    },

    startTimer() {
      clearTimeout(this.timerId);
      // Set a new timer for the next card
      this.remainingTime = this.roundDuration; // Reset the remaining time
      this.timerVisible = true; // Show the timer
      this.timerId = setInterval(() => {
        this.remainingTime--;

        if (this.remainingTime <= 0) {
          this.timerVisible = false; // Hide the timer
          this.pushIncorrectQuestions();
          this.nextCard();

          // Check if the lightning round is completed
          if (this.currentCardIndex >= this.cards.length - 1) {
            this.pushIncorrectQuestions();
            this.navigateToCompletedView();
          } else {
            this.startTimer(); // Continue to the next card after the duration
          }
        }
      }, 1000); // Update the timer every second
    },

    navigateToCompletedView() {
      this.$router.push({ name: "completed-study-session" });
    },

    pushIncorrectQuestions() {
      this.$store.state.incorrectQuestions.push(
        this.cards[this.currentCardIndex].question
      );
    },

    markAnswerSelected() {
      if (this.selectedAnswer) {
        let allButtons = document.querySelectorAll(".answer-item");
        allButtons.forEach((button) => {
          button.classList.remove("selected-answer");
        });

        let targetButton = Array.from(allButtons).find(
          (button) => button.innerText === this.selectedAnswer
        );
        if (targetButton) {
          targetButton.classList.add("selected-answer");
        }
      }
    },

    clearSelectedAnswer() {
      let allButtons = document.querySelectorAll(".answer-item");
      allButtons.forEach((button) => {
        button.classList.remove("selected-answer");
      });
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

  mounted() {
    document.addEventListener("keyup", this.handleKeyPress);
  },

  beforeUnmount() {
    clearInterval(this.timerId);
    document.removeEventListener("keyup", this.handleKeyPress);
  },

  watch: {
    selectedDeck(newDeck) {
      if (newDeck) {
        DeckService.getCardsByDeckId(newDeck.deckId)
          .then((response) => {
            this.cards = response.data;
            this.shuffleCards(this.cards);
            this.currentCardIndex = 0;
            this.randomAnswers = this.getRandomAnswers();
            this.startTimer();
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
.main-lightning-round-container {
  padding-left: 15px;
  padding-right: 15px;
}

.viewedQuestion[draggable="true"]:active {
  cursor: move;
  opacity: 0.5;
}

.lightning-round-header {
  margin-top: 0;
}
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

.dropDownButton:focus {
  border: 2px solid black;
}

.submit-button {
  background-color: #86efac;
}

h1 {
  text-align: center;
}

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

  margin-left: auto;
  margin-right: auto;

  border: 3px solid black;
  border-radius: 10px;

  display: flex;
  justify-content: center;
  align-items: center;

  text-align: center;

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

.selected-answer {
  background-color: #d4d4d8;
  border: 2px solid black;
}

.lightning-round-buttons {
  margin-top: 10px;
  display: flex;
  justify-content: center;
}

.submit-button {
}

.cancel-button {
  margin-top: 10px;
}

input[type="submit"] {
}
</style>