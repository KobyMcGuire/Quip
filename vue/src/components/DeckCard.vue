<template>
  <!-- Get rid of in-line css styling??? -->
  <router-link
    style="text-decoration: none"
    v-bind:to="{ name: 'deck-cards', params: { id: deck.deckId } }"
  >
    <div>
      <div class="deck-card">
        <h1>{{ deck.title }}</h1>
        <p>{{ deck.description }}</p>
      </div>
    </div>
  </router-link>
</template>

<script>
export default {
  props: ["deck"],

  created() {

  }
};
</script>

<style scoped>
.deck-card {
  border-radius: 15px;
}
</style>