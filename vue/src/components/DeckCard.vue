<template>
  <div>
    <!-- Get rid of in-line css styling??? -->
    <router-link style="text-decoration: none" v-bind:to="{ name: 'deck-cards', params: { id: deck.id } }">
      <div class="deck-card">
        <h1>{{ deck.title }}</h1>
        <p>{{ deck.description }}</p>
      </div>
    </router-link>
  </div>
</template>

<script>
export default {
    props : [
        'deck'
    ]
};
</script>

<style scoped>

</style>