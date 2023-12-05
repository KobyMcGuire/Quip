<template>
  <div id="capstone-app">
    <div id="nav">
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
      <router-link v-bind:to="{ name: 'decks' }">Decks</router-link>&nbsp;|&nbsp;
      <!-- <router-link v-bind:to="{name: 'study-session'}">Study Session</router-link> -->
      <router-link
        v-bind:to="{ name: 'logout' }"
        v-if="this.$store.state.token != ''"
        >Logout</router-link
      >
    </div>
<<<<<<< HEAD
    
=======
>>>>>>> dc11ead8e99c3aefcf11736138b9a92a7b0089d3
    <div id="main-container">
      <router-view />
    </div>
  </div>
</template>

<style>
* {
  font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
  box-sizing: border-box;
}

button {
  border: none;
  border-radius: 15px;

  padding: 10px;
}

button:hover {
  border: 1px solid black;

  cursor: pointer;
}

input[type="submit"] {
  border: none;
  border-radius: 15px;

  padding: 10px;

  background-color: #a7f3d0;
}

input[type="submit"]:hover {
  border: 1px solid black;

  cursor: pointer;
}

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
  text-align: center;

  padding: 5px;

  border-radius: 5px;

  background-color: rgb(234, 105, 105);
}

.cancel-button {
  background-color: rgb(234, 105, 105);
}
</style>
