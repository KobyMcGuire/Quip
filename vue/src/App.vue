<template>
  <head>
    <title>Quip</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css"/>
  </head>

  <nav>
    <div class="sidebar close">
      <div class="logo">
        <i class="fab fa-quora"></i>
        <span class="logo-name">Quip</span>
      </div>

      <ul class="nav-list">
        <li>
          <a href="/">
            <i class="fa fa-home"></i>
            <router-link v-bind:to="{ name: 'home' }"><span class="link-name">Home</span></router-link>
          </a>

          <ul class="sub-menu blank">
            <li><a href="#" class="link-name">
              <router-link v-bind:to="{ name: 'home' }">Home</router-link>
            </a></li>
          </ul>
        </li>

        <li>
          <a href="/decks">
            <i class="fa fa-book"></i>
            <span class="link-name"><router-link class="deck-link" v-bind:to="{ name: 'decks' }">Decks</router-link></span>
          </a>
          <ul class="sub-menu blank">
            <li><a href="#" class="link-name">
              <router-link v-bind:to="{ name: 'decks' }">Decks</router-link>
            </a></li>
          </ul>
        </li>

        <li>
          <div class="icon-link">
            <a href="#">
              <i class="fa fa-spinner"></i>
              <span class="link-name">Studies</span>
            </a>
            <i class="fa fa-caret-down arrow"></i>
          </div>

          <ul class="sub-menu">
            <li><a href="#">
              <router-link v-bind:to="{ name: 'single-card-view' }">Study</router-link>
            </a></li>
            <li><a href="#">
              <router-link v-bind:to="{ name: 'study-session' }">Quiz</router-link>
            </a></li>
            <li><a href="#">
              <router-link v-bind:to="{ name: 'lightning-round-study-session' }">Lightning Round</router-link>
            </a></li>
          </ul>
        </li>

        <li>
          <div class="profile-details">

            <router-link v-if="$store.state.token" v-bind:to="{ name: 'user' }">
              <div class="profile-content">
                <img src="../src/assets/pfp.jpg" alt=""/>
              </div>
            </router-link>

            <div class="name-job">
              <div class="firstNameLoggedOut" v-if="!$store.state.token">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Please Sign in ---></div>
              <div class="firstName">{{ this.$store.state.user.username }}</div>
            </div>

            <router-link v-if="!$store.state.token" v-bind:to="{ name: 'login' }"><i class="fa fa-right-to-bracket"></i>
            </router-link>
            <router-link v-if="$store.state.token" v-bind:to="{name: 'logout'}"><i class="fa fa-right-from-bracket"></i>
            </router-link>

          </div>
        </li>
      </ul>
    </div>

    <div class="home-section">
      <div class="home-content">
        <i class="fas fa-bars"></i>
        <span class="text"></span> <!-- Drop Down Button here. If we wanna name it... -->
      </div>
      <router-view/>
    </div>

  </nav>
</template>

<style>

.fas.fa-bars {
  /* Was causing an issue with upload image icons */
  position: fixed;
  z-index: 1000;
}

.sidebar {
  position: fixed;
  top: 0;
  left: 0;
  height: 100%;
  width: 260px;
  background-color: #11101d;
  z-index: 100;
  transition: all 0.5s ease;
}

.sidebar.close {
  width: 78px;
}

.sidebar .logo {
  height: 60px;
  display: flex;
  width: 100%;
  align-items: center;
}

.sidebar .logo i {
  font-size: 30px;
  color: #fff;
  height: 50px;
  min-width: 78px;
  text-align: center;
  line-height: 50px;
}

.sidebar .logo .logo-name {
  font-size: 22px;
  color: #fff;
  font-weight: 600;
  transition: all 0.3s ease;
  transition-delay: 0.1s;
}

.sidebar.close .logo .logo-name {
  opacity: 0;
  pointer-events: none;
}

.sidebar .nav-list {
  height: 100%;
  padding: 30px 0 150px 0;
  overflow: auto;
}

.sidebar.close .nav-list {
  overflow: visible;
}

.sidebar .nav-list::-webkit-scrollbar {
  display: none;
}

.sidebar .nav-list li {
  position: relative;
  list-style: none;
  transition: all 0.4s ease;
}

.sidebar .nav-list li:hover {
  background-color: #1d1b31;
}

.sidebar .nav-list li .icon-link {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.sidebar.close .nav-list li .icon-link {
  display: block;
}

.sidebar .nav-list li i {
  height: 50px;
  min-width: 78px;
  text-align: center;
  line-height: 50px;
  color: #fff;
  font-size: 20px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.sidebar.close .nav-list li i.arrow {
  display: none;
}

.sidebar.close .nav-list .fa.fa-right-to-bracket {
  display: none;
}

.sidebar.close .nav-list .fa.fa-right-from-bracket {
  display: none;
}

.sidebar .nav-list li.show i.arrow {
  transform: rotate(-180deg);
}

.sidebar .nav-list li a {
  display: flex;
  align-items: center;
  text-decoration: none;
}

.sidebar .nav-list li a .link-name {
  color: #fff;
  font-size: 18px;
  font-weight: 400;
  transition: all 0.4s ease;
}

.sidebar.close .nav-list li a .link-name {
  opacity: 0;
  pointer-events: none;
}

.sidebar .nav-list li .sub-menu {
  padding: 6px 6px 14px 80px;
  margin-top: -10px;
  background: #1d1b31;
  display: none;
  transition: all 0.3s ease;
}

.sidebar .nav-list li.show .sub-menu {
  display: block;
}

.sidebar.close .nav-list li .sub-menu {
  position: absolute;
  left: 100%;
  top: -10px;
  margin-top: 0;
  padding: 10px 20px;
  border-radius: 0 6px 6px 0;
  opacity: 0;
  display: block;
  pointer-events: none;
  transition: 0s;
}

.sidebar.close .nav-list li:hover .sub-menu {
  top: 0;
  opacity: 1;
  pointer-events: auto;
  transition: all 0.4s ease;
}

.sidebar .nav-list li .sub-menu a {
  color: #fff;
  font-size: 15px;
  padding: 5px 0;
  white-space: nowrap;
  opacity: 0.6;
  transition: all 0.3s ease;
}

.sidebar .nav-list li .sub-menu a:hover {
  opacity: 1;
}

.sidebar .nav-list li .sub-menu .link-name {
  display: none;
}

.sidebar.close .nav-list li .sub-menu .link-name {
  font-size: 18px;
  opacity: 1;
  display: block;
}

.sidebar .nav-list li .sub-menu.blank {
  padding: 3px 20px 6px 16px;
  opacity: 0;
  pointer-events: none;
}

.sidebar .nav-list li:hover .sub-menu.blank {
  top: 50%;
  transform: translateY(-50%);
}

.profile-details {
  position: fixed;
  bottom: 0;
  width: 260px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  background-color: #1d1b31;
  padding: 12px 0;
  transition: all 0.5s ease;
}

.sidebar.close .profile-details {
  background-color: transparent;
  width: 78px;
}

.profile-details .profile-content {
  display: flex;
  align-items: center;
}

.profile-details .profile-content img {
  height: 51px;
  width: 51px;
  border-radius: 50%;
  object-fit: cover;
  margin: 0 14px 0 12px;
  background-color: #1d1b31;
  transition: all 0.5s ease;
}

.sidebar.close .profile-details .profile-content img {
  padding: 10px;
}

.firstNameLoggedOut {
  color: #fff;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 18px;
  white-space: nowrap;
}

.profile-details .name-job .firstName {
  color: #fff;
  font-size: 18px;
  white-space: nowrap;
}

.sidebar.close .profile-details i,
.sidebar.close .profile-details .name-job .firstName,
.sidebar.close .profile-details .name-job .firstNameLoggedOut {
  opacity: 0;
  pointer-events: none;
}

.home-section {
  position: relative;
  height: 100vh;
  width: calc(100% - 260px);
  left: 260px;
  transition: all 0.5s ease;
}

.sidebar.close ~ .home-section {
  left: 78px;
  width: calc(100% - 78px);
}

.home-section .home-content {
  display: flex;
  height: 60px;
  align-items: center;
}

.home-section .home-content i,
.home-section .home-content .text {
  color: #11101d;
  font-size: 35px;
}

.home-section .home-content i {
  margin: 0 15px;
  cursor: pointer;
}

.home-section .home-content .text {
  white-space: nowrap;
  font-size: 20px;
  font-weight: 600;
}

.deck-link {
  color: white;
}

button {
  border: none;
  border-radius: 15px;

  padding: 8px;
}

button:hover {
  cursor: pointer;
  border: 1px solid black;
}

.cancel-button {
  background-color: #fca5a5;
  transition: all .2s ease-in-out;
}

.cancel-button:hover {
  transform: scale(1.025);
}

.error-message {
  background-color: #f87171;
  padding: 5px;
  border-radius: 10px;
}

input[type="submit"] {
  border: none;
  border-radius: 15px;

  padding: 8px;

  background-color: #86efac;

  transition: all .2s ease-in-out ;
}

input[type="submit"]:hover {
  cursor: pointer;
  border: 1px solid black;

  transform: scale(1.025);
}

* {
  box-sizing: border-box;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

body {
    background-color: #fffafa;
  }

@media (max-width: 400px) {
  .sidebar.close .nav-list li .sub-menu {
    display: none;
  }

  .sidebar {
    width: 78px;
  }

  .sidebar.close {
    width: 0;
  }

  .home-section {
    left: 78px;
    width: calc(100% - 78px);
    z-index: 100;
  }

  .sidebar.close ~ .home-section {
    width: 100%;
    left: 0;
  }

  

}
</style>


<script>
import SingleDeckView from "@/views/SingleDeckView.vue";

export default {
  components: {SingleDeckView},
  data() {
    return {
      sidebar: null,
      btn: null,
      arrows: null,
    };
  },
  mounted() {
    this.sidebar = document.querySelector(".sidebar");
    this.btn = document.querySelector(".fa-bars");
    this.arrows = document.querySelectorAll(".arrow");

    this.btn.addEventListener("click", this.toggleSidebar);

    for (let i = 0; i < this.arrows.length; i++) {
      this.arrows[i].addEventListener("click", this.toggleArrow);
    }
  },
  methods: {
    toggleSidebar() {
      this.sidebar.classList.toggle("close");
    },
    toggleArrow(e) {
      let arrowParent = e.target.parentElement.parentElement;
      arrowParent.classList.toggle("show");
    },
  },
};
</script>