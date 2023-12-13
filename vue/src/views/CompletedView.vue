<template>
  <div class="main-results-container">
    <div class="inner-content">
      <h1>Results</h1>
      <h2>You got {{ correctAnswers }} correct!</h2>

      <div v-if="incorrectQuestions.length > 0">
        <h3>Here's a recap of the questions you got wrong</h3>
        <ul>
          <li v-for="question in incorrectQuestions" :key="question">
            {{ question }}
          </li>
        </ul>
      </div>

      <div v-else>
        <h3>You got every single question correct!</h3>
      </div>

      <router-link :to="{ name: 'decks' }">
        <button class="submit-button">Return Home</button>
      </router-link>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      correctAnswers: 0,
      incorrectQuestions: [],
    };
  },

  created() {
    // Set store data to local data before resetting it
    this.correctAnswers = this.$store.state.correctAnswers;
    this.incorrectQuestions = this.$store.state.incorrectQuestions;

    // Reset correct answers and incorrect answers
    this.$store.state.incorrectQuestions = [];
    this.$store.state.correctAnswers = 0;
  },
};
</script>

<style scoped>
.main-results-container {
  margin-left: auto;
  margin-right: auto;

  padding: 10px;

  text-align: center;

  border: 2px solid black;
  border-radius: 15px;
  
  background-color: rgb(251, 249, 249);

  max-width: 60%;
}

ul {
  padding-left: 0;

  list-style-type: none;
}

li {
  margin-top: 5px;
}

.submit-button {
  background-color: #86efac;
}
</style>