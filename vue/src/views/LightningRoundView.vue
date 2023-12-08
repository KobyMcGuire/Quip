<template>
    <div>
        <h1>Pick a deck and begin lightning Round</h1>
        <select class="dropDownButton" v-model="selectedDeck" v-bind:disabled="selectedDeck">
            <option v-for="deck in decks" :key="deck.deckId" :value="deck">
                {{ deck.title }}
            </option>
        </select>
        <div v-if="selectedDeck">
            <h5>Correct Answers: {{ this.$store.state.correctAnswers }} / {{ cards.length }}</h5>

            <div class="viewedQuestion">
                {{ cards[currentCardIndex] && cards[currentCardIndex].question }}
            </div>

            <div class="answer-container">
                <button v-for="(answer, index) in randomAnswers" :key="index" class="answer-item"
                    @click="changeUserAnswer($event); markAnswerSelected($event)">

                    {{ answer }}

                </button>
            </div>
        </div>
        <div class="study-submit-button" v-if="selectedDeck">
            <router-link :to="{ name: 'completed-study-session' }" v-if="currentCardIndex === cards.length - 1">
                <input type="submit" @click="submitAndMoveNext(); clearSelectedAnswer()">
            </router-link>

            <input type="submit" v-else @click="submitAndMoveNext(); clearSelectedAnswer()">
        </div>
        <div v-if="timerVisible">
            <p>Time remaining: {{ remainingTime }} seconds</p>
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
            roundDuration: 1,
            timerId: null,
            remainingTime: 0,
            timerVisible: false
        };
    },
    methods: {
        submitAndMoveNext() {
            if (this.selectedAnswer !== null) {
                this.checkAnswer(this.currentCardIndex);
                this.nextCard();
                this.selectedAnswer = null;
                this.startTimer();
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
        startTimer() {
            clearTimeout(this.timerId);
            // Set a new timer for the next card
            this.remainingTime = this.roundDuration; // Reset the remaining time
            this.timerVisible = true; // Show the timer
            this.timerId = setInterval(() => {
                this.remainingTime--;
                if (this.remainingTime <= 0) {
                    this.timerVisible = false; // Hide the timer
                    this.$store.state.incorrectQuestions.push(this.cards[this.currentCardIndex].question)
                    this.nextCard();

                    // Check if the lightning round is completed
                    if (this.currentCardIndex >= this.cards.length) {
                        this.navigateToCompletedView();
                    } else {
                        this.startTimer(); // Continue to the next card after the duration
                    }
                }
            }, 1000); // Update the timer every second
        },

        navigateToCompletedView() {
            this.$router.push({ name: 'completed-study-session' });

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
        },
        clearSelectedAnswer() {
            // Clear previous selection
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
        }
    }
}

</script>
<style scoped>
.selected-answer {
    background-color: rgb(159, 159, 159);
}
</style>