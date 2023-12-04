<template>
  <div class = 'deck-container'>

    <div class="new-deck-card">
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
            DeckService.addDeck(this.newDeck);
            this.newDeck = '';
        },

        // Make error handler display a message to the site
        errorHandler(error, verb) {
            console.log(`There was an error ${verb}. The error was: ${error}`);
        }
    },

    data() {
        return {
            newDeck:{
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
        .catch((error) => {
            this.errorHandler(error, 'fetching all decks');
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

        min-width: 10%;
    }

    .new-deck-card {
        display: flex;
        flex-direction: column;

        text-align: center;

        padding: 10px;
        background-color: aqua;

    }

    .new-deck-card {
        text-align: left;
    }

    #submitNewDeck {
        max-width: 30%;

        margin-top: 5px;
    }

    .new-deck-card input{
      max-width: 70%;
    }
</style>