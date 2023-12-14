<template>
  <div id="login">
    <form v-on:submit.prevent="login">
      <h1 >Please Sign In</h1>
      <div role="alert" v-if="invalidCredentials">
        Invalid username and password!
      </div>
      <div role="alert" v-if="this.$route.query.registration">
        Thank you for registering, please sign in.
      </div>
      <div class="form-input-group">
        <label for="username">Username</label>
        <input type="text" id="username" v-model="user.username" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="password">Password</label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <button type="submit" class="sign-in-button">Sign in</button>
      <p>
      <router-link v-bind:to="{ name: 'register' }">Need an account? Sign up.</router-link></p>
    </form>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    }
  }
};
</script>

<style scoped>

#login {
  display: flex;
  flex-direction: column;
  align-items: center;

  text-align: center;

  background-color: #FCF5E5;

  border: 2px solid black;
  border-radius: 5px;

  box-shadow: 10px 10px 5px rgb(198, 196, 196);

  max-width: 50%;
  min-height: 35%;

  margin-left: auto;
  margin-right: auto;
}

.sign-in-button {
  background-color: #86efac;

  transition: all .1s ease-in-out;
}

.sign-in-button:hover {
  transform: scale(1.025);
}

.form-input-group {
  margin-bottom: 1rem;
}
label {
  margin-right: 0.5rem;
}
</style>