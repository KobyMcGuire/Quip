<template>
  <div class='flashcard-card' v-on:click="flipCard">
    <div class='question' v-if="showFront">
        <h1>Question</h1>
        <p>{{ flashcard.question }} </p>
    </div>

    <div class='answer' v-if="!showFront">
        <h1>Answer</h1>
        <p>{{ flashcard.answer }} </p>
    </div>

  </div>
</template>

<script>
export default {
    props : [
        'flashcard'
    ],

    data () {
        return {
            showFront : true,
        }
    },

    methods : {
        flipCard() {
            this.showFront = !this.showFront;
        }
    }
}
</script>

<style scoped>
    .flashcard-card {
        border: 1px solid black;
        border-radius: 5px;

        text-align: center;

        padding: 10px;
    }

    h1 {
        font-size: large;
    }


</style>