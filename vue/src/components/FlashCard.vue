<template>
  <div class="flashcard-container">
    <div class="flashcard-text-content" v-on:click="flipCard">
      <div class="question" v-if="showFront">
        
          <img :src="image" alt="" />
     
        <p>{{ flashcard.question }}</p>
      </div>

      <div class="answer" v-if="!showFront">

        <img :src="backImage" alt="" />

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
        <button class="edit-button">
          <span class="material-symbols-outlined"> edit </span>
        </button>
      </router-link>

      <div class="image-upload">
        <button v-on:click="defineWidget()" id="upload_widget" class="cloudinary-button" v-if="showFront">
          <label for="image">
            <i class="fas fa-upload"></i>
            <!-- This is the upload icon -->
          </label>

          
        </button>

        <button v-on:click="defineWidget()" id="upload_widget" class="cloudinary-button" v-else>
          <label for="backImage">
            <i class="fas fa-upload"></i>
            <!-- This is the upload icon -->
          </label>

          
        </button>

        
      </div>

      <button v-if="deleteButton" v-on:click="deleteFlashcard">
        <span class="material-symbols-outlined"> delete </span>
      </button>

      <!-- TO DO -->
      <button v-else v-on:click="addFlashcardToDeck">
        <span class="material-symbols-outlined"> add </span>
      </button>

      <button v-if="showFront" v-on:click="textToSpeech(flashcard.question)">
        <span class="material-symbols-outlined"> volume_up </span>
      </button>

      <button v-if="!showFront" v-on:click="textToSpeech(flashcard.answer)">
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
      backImage: "",
      editedFlashcard: {
        question: this.flashcard.question,
        answer: this.flashcard.answer,
        tag: this.flashcard.tag,
      },
      editFlashcardError: false,
      displayTags: false,
      decksFlashcards: {
        deckId: "",
        flashcardId: "",
      },
      uploadedImage: null,
      myWidget: {},
    };
  },
  computed: {},

  methods: {
    flipCard() {
      this.showFront = !this.showFront;
    },

    handleImageUpload(event) {
      
      const file = event.target.files[0];
      

      if (file && file.type.startsWith("image/")) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.image = e.target.result;
          // console.log(e.target.result);
        };
        reader.readAsDataURL(file);
      } else {
        console.error("Invalid file type or no file selected");
      }
    },

    // Add flashcard to new deck
    addFlashcardToDeck() {
      this.decksFlashcards.deckId = this.$route.params.id;
      this.decksFlashcards.flashcardId = this.flashcard.flashCardId;

      DeckService.addFlashcardToDeck(this.decksFlashcards)
        .then((response) => {
          let indexOfRemovedCard =
            this.$store.state.currentSearchFlashcards.indexOf(this.flashcard);

          // Add flashcard to current decks flashcards
          this.$store.state.currentDeckFlashcards.push(
            this.$store.state.currentSearchFlashcards[indexOfRemovedCard]
          );

          // Remove the flashcard from the current search flashcards array
          this.$store.state.currentSearchFlashcards.splice(
            indexOfRemovedCard,
            1
          );
        })
        .catch((error) => {
          this.errorHandler(error, "putting card in to new deck");
        });
    },

    // Delete Flashcard
    deleteFlashcard() {
      DeckService.deleteFlashcard(
        this.flashcard.flashCardId,
        this.$route.params.id
      )
        .then((response) => {
          let indexOfRemovedCard =
            this.$store.state.currentDeckFlashcards.indexOf(this.flashcard);

          // Update search store flashcards array to include deleted card
          this.$store.state.currentSearchFlashcards.push(
            this.$store.state.currentDeckFlashcards[indexOfRemovedCard]
          );

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

    defineWidget() {
      const cloudName = "dz0w5cehu";
      const uploadPreset = "gooah3bb";
      const folder = "final-capstone";
      this.myWidget = window.cloudinary.createUploadWidget(
        {
          cloudName: cloudName,
          uploadPreset: uploadPreset,
          folder: folder,
          // ... other options
        },
        (error, result) => {
          if (!error && result && result.event === "success") {
            console.log("Done! Here is the image info: ", result.info);

            this.image = result.info.secure_url;
            // document
            //   .getElementById("uploadedimage")
            //   .setAttribute("src", result.info.secure_url);
            this.$emit("image-uploaded", result.info.secure_url);
          }
        }
      ).open();
    },
  },
};
</script>

<style scoped>
.image-upload input[type="file"] {
  margin-bottom: 10px;
  display: none;
}


.flashcard-container {
  min-width: 30%;
  min-height: 10vh;

  border: 3px solid black;
  border-radius: 10px;

  background-color: rgb(251, 249, 249);

  text-align: center;

  padding: 10px;

  transition: all .2s ease-in-out;
}

.flashcard-container:hover {
  cursor: pointer;

  border: 4px solid black;

  transform: scale(1.025);

  box-shadow: 10px 5px 5px rgb(198, 196, 196);
}

h1 {
  font-size: large;
}

.flashcard-buttons {
  display: flex;
  justify-content: center;
  gap: 15px;
}

.edit-button {
  min-height: 48px;
}

</style>