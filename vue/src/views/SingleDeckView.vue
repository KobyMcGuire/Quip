<template>
  <div class = 'flash-cards-container'>
    <flash-card v-for="flashcard in flashcards" v-bind:key="flashcard.cardId" v-bind:flashcard="flashcard" />
  </div>
</template>

<script>
import FlashCard from "../components/FlashCard.vue";

export default {
  components: { FlashCard },

  data () {
    return {
        flashcards : []
    }
  },

  created() {
    // Make API call here to get flashcards that pertain to a specific deck
      for (let i = 0; i < this.$store.state.flashcards.length; i++) {
        let currentFlashCard = this.$store.state.flashcards[i];
        if (currentFlashCard.deckId == this.$route.params.id) {
            this.flashcards.push(currentFlashCard);
        }
      }
  },
};
</script>

<style scoped>

    .flash-cards-container {
        display: flex;
        flex-wrap: wrap;
        gap: 20px;
    }
    
</style>