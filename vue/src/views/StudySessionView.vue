<template>
  <div class="main-quiz-container">
    <div class="header-content">
      <h1 v-if="!selectedDeck">Select a Deck to Begin Quiz</h1>

      <select
        class="dropDownButton"
        v-model="selectedDeck"
        v-if = "!selectedDeck"
      >
        <option v-for="deck in decks" :key="deck.deckId" :value="deck">
          {{ deck.title }}
        </option>
      </select>

      <h3 v-if="selectedDeck">Randomize:</h3>
      <div class="toggle-switch" v-if="selectedDeck">
        <input
          type="checkbox"
          id="shuffleToggle"
          v-model="shuffle"
          :disabled="!cards.length"
        />
        <label for="shuffleToggle" class="slider"></label>
      </div>
    </div>

    <div v-if="selectedDeck" class="selected-deck-content">
      <h5>
        Correct Answers: {{ this.$store.state.correctAnswers }} /
        {{ cards.length }}
      </h5>

      <div class="viewedQuestion">
        <p>{{ cards[currentCardIndex] && cards[currentCardIndex].question }}</p>

        <div class="voice-buttons">
          <button class="question-to-speech" v-on:click="textToSpeech(cards[currentCardIndex].question)">
            <span class="material-symbols-outlined"> volume_up </span>
          </button>

          <button class="intake-speech"  v-on:click="startVoiceAnswer">
            <span class="material-symbols-outlined"> mic </span>
          </button>
        </div>
      </div>

      <div class="answer-container">
        <button
          v-for="(answer, index) in randomAnswers"
          :key="index"
          class="answer-item"
          @click="
            changeUserAnswer($event);
            markAnswerSelected($event);
          "
        >
          {{ answer }}
        </button>
      </div>
    </div>

    <div class="study-quiz-buttons" v-if="selectedDeck">
      <router-link
        :to="{ name: 'completed-study-session' }"
        v-if="currentCardIndex === cards.length - 1"
      >
        <input
          type="submit"
          @click="
            submitAndMoveNext();
            clearSelectedAnswer();
          "
        />
      </router-link>

      <button
        class="submit-button"
        v-else
        @click="
          submitAndMoveNext();
          clearSelectedAnswer();
        "
      >
        Check Answer
      </button>

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
      shuffle: false,
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
    shuffleCards(array) {
      let cardLength = array.length,
        currentIndex = array.length;
      for (currentIndex = cardLength - 1; currentIndex > 0; currentIndex--) {
        let randomIndex = Math.floor(Math.random() * (currentIndex + 1));
        let temp = array[currentIndex];
        array[currentIndex] = array[randomIndex];
        array[randomIndex] = temp;
        localStorage.setItem("shuffle", JSON.stringify(this.shuffle));
      }
      this.randomAnswers = this.getRandomAnswers();
    },

    submitAndMoveNext() {
      if (this.selectedAnswer !== null) {
        this.checkAnswer(this.currentCardIndex);
        this.nextCard();
        this.selectedAnswer = null;
      }
    },

    nextCard() {
      if (this.currentCardIndex < this.cards.length - 1) {
        this.currentCardIndex++;
        this.randomAnswers = this.getRandomAnswers();
      }
    },

    getRandomAnswers() {
      const correctAnswer = this.cards[this.currentCardIndex].answer;

      const wrongAnswers = this.cards
        .filter((card) => card.answer !== correctAnswer)
        .map((card) => card.answer);

      const shuffleWrongAnswers = wrongAnswers.sort(() => Math.random() - 0.5);
      const selectedWrongAnswers = shuffleWrongAnswers.slice(0, 3);

      const allAnswers = [correctAnswer, ...selectedWrongAnswers];
      return allAnswers.sort(() => Math.random() - 0.5);
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

    markAnswerSelected(event) {
      // Clear previous selection
      let allButtons = document.querySelectorAll(".answer-item");
      allButtons.forEach((button) => {
        button.classList.remove("selected-answer");
      });

      let targetButton = event.target;

      // Add selected class to target button
      targetButton.classList.add("selected-answer");

      this.selectedAnswer = targetButton.innerText;
    },

    clearSelectedAnswer() {
      // Clear previous selection
      let allButtons = document.querySelectorAll(".answer-item");
      allButtons.forEach((button) => {
        button.classList.remove("selected-answer");
      });
    },

    handleLeaveEarly() {

      // Push remaining questions into incorrect questions array
      for (let i = this.currentCardIndex; i < this.cards.length; i++) {
        this.$store.state.incorrectQuestions.push(this.cards[i].question);
      }

    },

    textToSpeech(text) {
      const speechSynthesis = window.speechSynthesis;
      const utterance = new SpeechSynthesisUtterance(text);
      speechSynthesis.speak(utterance);
    },

    startVoiceAnswer() {
      if (
        "SpeechRecognition" in window ||
        "webkitSpeechRecognition" in window
      ) {
        const SpeechRecognition =
          window.SpeechRecognition || window.webkitSpeechRecognition;
        const recognition = new SpeechRecognition();

        recognition.onresult = (event) => {
          if (event.results && event.results[0]) {
            const voiceAnswer = event.results[0][0].transcript;
            console.log(event);

            if (voiceAnswer !== undefined) {
              console.log("Voice Answer:", voiceAnswer);

              this.markVoiceAnswerSelected(voiceAnswer);
            } else {
              console.error("Unable to retrieve voice answer.");
            }
          } else {
            console.error("Unexpected format of speech recognition results.");
          }
        };
        recognition.start();
      } else {
        console.error("Speech Recognition API not supported in this browser.");
      }
    },
    markVoiceAnswerSelected(voiceAnswer) {
      console.log("mark voice answer selected:", voiceAnswer);
      this.randomAnswers.forEach((answer) => {
        if (voiceAnswer == answer) {
          this.selectedAnswer = answer;

          let allButtons = document.querySelectorAll(".answer-item");
          allButtons.forEach((button) => {
            if (button.innerText == this.selectedAnswer) {
              button.classList.add("selected-answer");
            }
            console.log(button.innerText);
          });
        }
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

    // const savedState = localStorage.getItem("shuffle");
    // if (savedState !== null) {
    //   this.shuffle = JSON.parse(savedState);
    // }
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
    },
    shuffle(newShuffleState){
      if(newShuffleState){
        this.shuffleCards(this.cards)
      }
  }
  }
  }
</script>

<style scoped>

.main-quiz-container {
  padding-left: 15px;
  padding-right: 15px;
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

.viewedQuestion {
  width: 250px;
  height: 250px;
  min-width: 25%;
  max-width: 30%;
  min-height: 25%;
  max-height: 30%;
  margin-left: auto;
  margin-right: auto;

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  position: relative;

  border: 3px solid black;
  border-radius: 10px;

  background-color: rgb(251, 249, 249);

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

  background-color: rgb(251, 249, 249);
}

.study-quiz-buttons {
  margin-top: 10px;
  display: flex;
  justify-content: center;
  gap: 20px;
}

.submit-button {
  background-color: #86efac;
}

.toggle-switch {
  position: relative;
  display: inline-block;
  width: 60px;
  height: 34px;
}

.toggle-switch input {
  opacity: 0;
  width: 0;
  height: 0;
}

.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ccc;
  transition: 0.4s;
  border-radius: 34px;
}

.selected-answer {
  background-color: #d4d4d8;
  border: 2px solid black;
}


.slider:before {
  position: absolute;
  content: "";
  height: 26px;
  width: 26px;
  left: 4px;
  bottom: 4px;
  background-color: white;
  transition: 0.4s;
  border-radius: 50%;
}

input:checked + .slider {
  background-color: #2196f3;
}

input:checked + .slider:before {
  transform: translateX(26px);
}

.voice-buttons {
  position: absolute;
  bottom: 10px;
}

.voice-buttons > button {
  color:white;
  background-color: #11101d;
}

.question-to-speech {
  margin-right: 10px;
  background-color: #d4d4d8;

}

.intake-speech{
  background-color: #d4d4d8;
}
</style>
