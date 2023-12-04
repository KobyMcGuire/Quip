<template>
  <div id="capstone-app">
    <div id="nav">
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
      <router-link v-bind:to="{ name: 'decks' }">Decks</router-link>&nbsp;|&nbsp;
      <router-link v-bind:to="{ name: 'logout' }" v-if="this.$store.state.token != ''">Logout</router-link>
    </div>
    <div id="main-container">
      <router-view />
    </div>
  </div>
</template>

<style>
  #nav {
    width: 10%;

    margin-left: auto;
    margin-right: auto;

    margin-bottom: 3%;

    font-size: large;
  }

  #main-container {
    padding-left: 5%;
    padding-right: 5%;
  }

  .error-message {
  padding-left: 5px;
  padding-right: 5px;

  border-radius: 5px;

  background-color: rgb(234, 105, 105);
}
</style>
