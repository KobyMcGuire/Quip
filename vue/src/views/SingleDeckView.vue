<template>
  <div>
    <flash-card v-for="flashcard in flashcards" v-bind:key="flashcard.id" v-bind:flashcard="flashcard" />
  </div>
</template>

<script>
import FlashCard from "../components/FlashCard.vue";

export default {
  components: { FlashCard },

  computed: {
    flashcards() {
      return this.$store.state.flashcards.filter((card) => {
        return card.deckId === this.$route.params.id;
      })
    },
  },
};
</script>

<style scoped>
</style>