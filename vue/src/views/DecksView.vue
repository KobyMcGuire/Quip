<template>
  <div class = 'deck-container'>

    <div class="deck-card">
        <h1>Create a new Deck</h1>

        <label for="deckTitle">Title: </label>
        <input type="text" id="deckTitle" name="deckTitle" v-model="newDeck.title">

        <label for="deckDescription">Description: </label>
        <input type="text" id="deckDescription" name="deckDescription" v-model="newDeck.description">

        <label for="submitNewDeck"></label>
        <input type="submit" id="submitNewDeck" v-on:click="addDeck">
    </div>

    <deck-card class="deck-card" v-for="deck in decks" v-bind:key="deck.deckId" v-bind:deck="deck"></deck-card>
  </div>
</template>

<script>
import DeckCard from "../components/DeckCard.vue"
import DeckService from "../services/DeckService";

export default {

    components : {
        DeckCard
    },

    methods : {
        // API call to add deck to DB
        addDeck() {
            this.$store.state.decks.push(this.newDeck)
            this.newDeck = '';
        }
    },

    data() {
        return {
            newDeck:{
                deckId : 4,
                title: "",
                description:""
            },

            decks : []

        }
    },

    created() {
        
        DeckService.getDecks()
        .then((response) => {
            this.decks = response.data;
        })
        // Fill out this catch with an error handler
        .catch((error) => {
            console.log(error);
        })
    
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