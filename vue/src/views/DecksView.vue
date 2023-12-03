<template>
  <div class = 'deck-container'>
    <deck-card class="deck-card" v-for="deck in decks" v-bind:key="deck.id" v-bind:deck="deck"></deck-card>
  </div>
</template>

<script>
import DeckCard from "../components/DeckCard.vue"

export default {

    components : {
        DeckCard
    },

    data() {
        return {
            decks:[]
        }
    },

    created() {
        // API call to update data
        this.decks = this.$store.state.decks;
    }

}
</script>

<style scoped>
    .deck-container { 
        display: flex;
        flex-wrap: wrap;
        gap : 15px;
    }
    
    .deck-card {
        text-align: center;

        padding: 10px;
        background-color: aqua;

    }
</style>