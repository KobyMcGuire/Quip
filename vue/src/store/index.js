import { createStore as _createStore } from 'vuex';
import axios from 'axios';

export function createStore(currentToken, currentUser) {
  let store = _createStore({

    state: {
      token: currentToken || '',
      user: currentUser || {},

      correctAnswers: 0,

      /* Dummy Deck Data to test with */
      decks : [

        {
          id : 1,
          title : 'Biology',
          description : 'Flashcards for Biology'
        },

        {
          id : 2,
          title : 'Chemistry',
          description : 'Flashcards for Chemistry'
        },

        {
          id : 3,
          title : 'Math',
          description : 'Flashcards for Math'
        },

      ],

      flashcards : [
        {
          cardId : 1,
          deckId : 1,
          question : "What is a mitochondria?",
          answer : "The powerhouse of the cell.",
          tags : [],
          creator : "Koby"
        },

        {
          cardId : 2,
          deckId : 1,
          question : "What is Biology?",
          answer : ":D",
          tags : [],
          creator : "Koby"
        },

        {
          cardId : 3,
          deckId : 1,
          question : "meep",
          answer : "hehe",
          tags : [],
          creator : "Koby"
        }
      ]


    },

    mutations: {
      SET_AUTH_TOKEN(state, token) {
        state.token = token;
        localStorage.setItem('token', token);
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      },
      SET_USER(state, user) {
        state.user = user;
        localStorage.setItem('user', JSON.stringify(user));
      },
      LOGOUT(state) {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        state.token = '';
        state.user = {};
        axios.defaults.headers.common = {};
      }
    },
  });
  return store;
}
