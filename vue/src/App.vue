<template>
  <div id="capstone-app">
    <nav id="nav">
      <ul>
        <li>
          <a href="#">
            <img src="/logo.png" alt="">
            <span class="nav-item">Quip</span>
          </a>
        </li>

        <li><a href="#">
          <i class="home"></i>
          <router-link v-bind:to="{ name: 'home' }">Home</router-link>
        </a></li>

        <li><a href="#">
          <i class="decks"></i>
          <router-link v-bind:to="{ name: 'decks' }">Decks</router-link>
        </a></li>

        <li><a href="#">
          <i class="study-session"></i>
          <router-link v-bind:to="{name: 'study-session'}">Study</router-link>
        </a></li>

        <li><a href="#">
          <i class="lightning-round-study-session"></i>
          <router-link v-bind:to="{name:'lightning-round-study-session'}">Lightning Round</router-link>
        </a></li>

        <li><a href="#">
          <i class="logout"></i>
          <router-link v-bind:to="{ name: 'logout' }" v-if="this.$store.state.token != ''">Logout</router-link>
        </a></li>

      </ul>
    </nav>

    <div id="main-container">
      <router-view/>
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
  display: flex;
  justify-content: center;

  margin-bottom: 3%;
  font-family: "Roboto Light";

  font-size: large;
}

#nav > a {
  text-decoration: none;

  color: black;
}


#main-container {
  padding-left: 5%;
  padding-right: 5%;
}

.error-message {
  text-align: center;

  padding: 5px;

  border-radius: 25px;

  background-color: rgb(234, 105, 105);
}

.cancel-button {
  background-color: rgb(234, 105, 105);
}


.material-symbols-outlined {
  font-variation-settings: 'FILL' 0,
  'wght' 400,
  'GRAD' 0,
  'opsz' 24
}

</style>
<script setup>
</script>