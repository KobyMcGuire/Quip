<template>
  <div class="flashcard-container">
    <div class="flashcard-text-content" v-on:click="flipCard">
      <div class="question" v-if="showFront">

        <p v-if="image && image.length">
          <img :src="image" alt="">
        </p>
        <p v-else>{{ flashcard.question }}</p>

      </div>

      <div class="answer" v-if="!showFront">
        <p>{{ flashcard.answer }}</p>
      </div>

      <div class="flashcard-tags" v-if="displayTags">
        <p>{{ flashcard.tag }}</p>
      </div>
    </div>

    <div class="flashcard-buttons">
      <button v-on:click="this.displayTags = !this.displayTags">
        <span class="material-symbols-outlined"> visibility </span>
      </button>

      <router-link
        v-bind:to="{
          name: 'edit-card',
          params: { id: this.flashcard.flashCardId },
        }"
      >
        <button>
          <span class="material-symbols-outlined"> edit </span>
        </button>
      </router-link>

        <div class="image-upload">
          <label for="image">
            <i class="fas fa-upload"></i> <!-- This is the upload icon -->
          </label>
          <input id="image" type="file" accept="image/*" @change="handleImageUpload">
        </div>


      <button v-if="deleteButton" v-on:click="deleteFlashcard">
        <span class="material-symbols-outlined"> delete </span>
      </button>

      <!-- TO DO -->
      <button v-else v-on:click="addFlashcardToDeck">
        <span class="material-symbols-outlined"> add </span>
      </button>

      <button v-on:click="textToSpeech(flashcard.question)">
      <span class="material-symbols-outlined"> volume_up </span>
    </button>
    
    <button v-on:click="textToSpeech(flashcard.answer)">
      <span class="material-symbols-outlined"> volume_up </span>
    </button>
    </div>
  </div>
</template>

<script>
import DeckService from "@/services/DeckService";

export default {
  props: ["flashcard", "deleteButton"],

  data() {
    return {
      showFront: true,
      image: "",
      editedFlashcard: {
        question: this.flashcard.question,
        answer: this.flashcard.answer,
        tag: this.flashcard.tag,

      },
      editFlashcardError: false,
      displayTags: false,
      decksFlashcards: {
        deckId: "",
        flashcardId: ""
      }
    };
  },
  computed: {},

  methods: {
    flipCard() {
      this.showFront = !this.showFront;
    },

    handleImageUpload(event) {
      console.log(event.target.files)
      const file = event.target.files[0];
      console.log(file);

      if (file && file.type.startsWith('image/')) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.image = e.target.result;
          // console.log(e.target.result);
        };
        reader.readAsDataURL(file);
      } else {
        console.error('Invalid file type or no file selected');
      }
    },

    // Add flashcard to new deck
    addFlashcardToDeck() {
      this.decksFlashcards.deckId = this.$route.params.id;
      this.decksFlashcards.flashcardId = this.flashcard.flashCardId;

      DeckService.addFlashcardToDeck(this.decksFlashcards)
      .then((response) => {
        let indexOfRemovedCard = this.$store.state.currentSearchFlashcards.indexOf(this.flashcard);

        // Add flashcard to current decks flashcards
        this.$store.state.currentDeckFlashcards.push(this.$store.state.currentSearchFlashcards[indexOfRemovedCard]);

        // Remove the flashcard from the current search flashcards array
        this.$store.state.currentSearchFlashcards.splice(indexOfRemovedCard, 1);
      })
      .catch((error) => {
        this.errorHandler(error, 'putting card in to new deck')
      })      

    },

    // Delete Flashcard
    deleteFlashcard() {
      DeckService.deleteFlashcard(this.flashcard.flashCardId, this.$route.params.id)
        .then((response) => {
          let indexOfRemovedCard = this.$store.state.currentDeckFlashcards.indexOf(this.flashcard)

          // Update search store flashcards array to include deleted card
          this.$store.state.currentSearchFlashcards.push(this.$store.state.currentDeckFlashcards[indexOfRemovedCard]);

          // Remove card from the stores current deck flashcards array 
          this.$store.state.currentDeckFlashcards.splice(indexOfRemovedCard, 1);
        })
        .catch((error) => {
          this.errorHandler(error, "deleting flashcard");
        });
    },

    errorHandler(error, verb) {
      console.log(`There was an error ${verb}. The error was: ${error}`);
    },

    textToSpeech(text) {
      const speechSynthesis = window.speechSynthesis;
      const utterance = new SpeechSynthesisUtterance(text);
      speechSynthesis.speak(utterance);
    },
  },
};
</script>

<style scoped>

.image-upload input[type="file"]{
  margin-bottom: 10px;
  display: none;
}
.viewedQuestion img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.flashcard-container {
  min-width: 25%;
  min-height: 30%;

  border: 3px solid black;
  border-radius: 10px;

  text-align: center;

  padding: 10px;
}

.flashcard-container:hover {
  cursor: pointer;
}

h1 {
  font-size: large;
}

.flashcard-buttons {
  display: flex;
  justify-content: center;
  gap: 15px;
}

.submit-flashcard-edit {
  margin-top: 10px;
}
</style>