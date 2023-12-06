<template>
  <div><search-bar/></div>
</template>

<script>
import SearchBar from "../components/SearchBar.vue";

export default {
  components: { SearchBar },


  methods: {
    errorHandler(error, verb) {
      console.log(`There was an error ${verb}. The error was: ${error}`);
    },
  },
};
</script>

<style></style>