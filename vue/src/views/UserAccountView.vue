<template>
  <div id="user">
    <h1>User Profile</h1>

    <div class="profile-picture">
      <img :src="profilePictureLink" alt="Profile Picture" />
      <input type="file" @change="onFileChange" accept="image/*" />
    </div>
    <div class="username">
      <label for="username">Username: </label>
      <span>{{ this.$store.state.user.username }}</span>
    </div>
    <div class="decks">
      <h2>User Decks</h2>
      <ul>
        <li v-for="deck in decks" :key="deck.id">
          <router-link
            v-bind:to="{ name: 'deck-cards', params: { id: deck.deckId } }"
            >{{ deck.title }}</router-link
          >
        </li>
      </ul>
    </div>

    <div class="flashcards">
      <h2>User Flashcards</h2>
      <ul>
        <li v-for="flashcard in flashcards" :key="flashcard.id">
          {{ flashcard.question }}
        </li>
      </ul>
    </div>
  </div>
</template>
<script>
import DeckService from '../services/DeckService';
export default {
  data() {
    return {
       
        profilePictureUrl: "",
        decks: [],
        flashcards: [],
    
    };
  },
  methods: {
    uploadProfilePicture(file) {
      try {
        const reader = new FileReader();

        reader.onload = (e) => {
          // Update profilePictureUrl with base64-encoded image data
          this.profilePictureUrl = e.target.result;
        };

        // Read the selected file as a data URL
        reader.readAsDataURL(file);
      } catch (error) {
        console.error("Error uploading profile picture:", error);
      }
    },

    onFileChange(event) {
      const file = event.target.files[0];
      if (file) {
        this.uploadProfilePicture(file);
      }
    },

    fetchUserDecks() {
      DeckService.getDecks()
      .then((response)=>{
        this.decks = response.data;
      })
    },

    fetchUserFlashcards() {
      DeckService.getCards()
      .then((response)=>{
        this.flashcards = response.data;
      })
    },

    // fetchUserInfo(){
    //     try {
    //     // Call your login endpoint to get user details
    //     const response =  this.$axios.post("/login", {
    //       // provide login credentials if needed
    //     });

    //     // Assuming the response contains user details including username and profilePictureUrl
    //     const userData = response.data.user;

    //     this.user.username = userData.username;
    //     this.user.profilePictureUrl = userData.profilePictureUrl;
    //   } catch (error) {
    //     console.error("Error fetching user information:", error);
    //   }
    // },
  },
  created(){
    this.fetchUserDecks();
    this.fetchUserFlashcards();
    // this.fetchUserInfo();
  },

  computed:{
    profilePictureLink(){
      if(!this.profilePictureUrl){
      return new URL('../assets/pfp.jpg', import.meta.url).href;
    }else{
      return this.profilePictureUrl;
    }
    }
    
  }
};
</script>
<style scoped>
.profile-picture img {
  height: 30%;
  /* width: 51px; */
  max-width: 20%;
  border-radius: 50%;
  display: block;
  margin: 0 14px 0 12px;
  background-color: #1d1b31;
  transition: all 0.5s ease;
}
.form-input-group {
  margin-bottom: 1rem;
}
.username label {
  margin-right: 0.5rem;
  font-weight: bold;
}
</style>