<script>
export default {
  data() {
    return {
      animationId: 0,
      notes: [
        {
          sub: 'Versatile Learning',
          content: 'Craft notecards, take quizzes, and study – all in one place!',
          active: false,
          transform: ''
        },
        {
          sub: 'Your Learning, Your Way',
          content: 'Customize notecards, quizzes, and sessions to fit your style.',
          active: false,
          transform: ''
        },
        {
          sub: 'Effortless Prep',
          content: 'Build notecards in seconds for efficient, effective learning.',
          active: false,
          transform: ''
        },
        {
          sub: 'Smart and Adaptive',
          content: 'Experience smart learning that evolves with you.',
          active: false,
          transform: ''
        }
      ]
    };
  },

  methods: {
    rotateNotes() {
      let angle = 0;
      this.notes.forEach((note) => {
        if (note.active) {
          note.transform = `translate(-50%, -120vh) rotate(-48deg)`;
        } else {
          note.transform = `translate(-50%, -50%) rotate(${angle}deg)`;
          angle = angle - 10;
        }
      });
    },

    startAutoScroll() {
      window.scrollBy(0, 4);
      this.animationId = window.requestAnimationFrame(this.startAutoScroll);
    },

    stopAutoScroll() {
      window.scrollBy(0, 0)
      window.cancelAnimationFrame(this.animationId);
      window.removeEventListener("scroll", this.scrollAnimation)
    },

    scrollAnimation() {
      if (this.$route.name === 'home') {
        let proportion = this.$el.getBoundingClientRect().top / window.innerHeight;
        if (proportion <= 0) {
          let n = this.notes.length;
          let index = Math.ceil((proportion * n) / 2);
          index = Math.abs(index) - 1;
          for (let i = 0; i < n; i++) {
            this.notes[i].active = i <= index;
          }
          this.rotateNotes();
        }
      } else {
        this.stopAutoScroll();
      }

    },
  },

  mounted() {
    this.startAutoScroll();
    window.addEventListener("scroll", this.scrollAnimation);
  },

  beforeUnmount() {
    console.log("here");
      this.stopAutoScroll()

  }
}

</script>

<template>
  <div class="center">
    <div class="stack-area">
      <div class="left">
        <div class="title">QUIP</div>
        <div class="sub-title">
          OUR FEATURES ARE BIG AND STRONG
          <br/>
          <button>
            <router-link v-bind:to="{ name: 'register' }" class="sign-up-button">Register Now and Begin Studying!</router-link>
          </button>
        </div>
      </div>
      <div class="right">
        <div class="cards">
          <div class="card" v-for="(note, index) in notes" :key="index" :class="{active: note.active}"
               :style="{transform: note.transform}">
            <div class="sub">{{ note.sub }}</div>
            <div class="content">{{ note.content }}</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
body {
  width: 100%;
  height: fit-content;
  margin: 0;
  padding: 0;
}

.center {
  width: 100%;
  height: fit-content;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
}

.stack-area {
  width: 100%;
  height: 300vh;
  position: relative;
  display: flex;
  justify-content: center;
}

.right,
.left {
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  position: sticky;
  top: 0;
  box-sizing: border-box;
  flex-basis: 50%;
}

.cards {
  width: 100%;
  height: 100%;
  position: relative;
}

.card {
  width: 350px;
  height: 350px;
  box-sizing: border-box;
  padding: 35px;
  border-radius: 6mm;
  display: flex;
  justify-content: space-between;
  flex-direction: column;
  position: absolute;
  top: 50%;
  left: 50%;
  transition: 0.5s ease-in-out;
}

.card:nth-child(1) {
  background: rgb(64, 122, 255);
  z-index: 4;
}

.card:nth-child(2) {
  background: rgb(221, 62, 88);
  z-index: 3;
}

.card:nth-child(3) {
  background: rgb(186, 113, 245);
  z-index: 2;
}

.card:nth-child(4) {
  background: rgb(247, 92, 208);
  z-index: 1;
}

.sub {
  font-size: 15px;
  font-weight: 700;
}

.content {
  font-size: 33px;
  font-weight: 700;
  line-height: 54px;
}

.card.active {
  transform-origin: bottom left;
}

.left {
  align-items: center;
  flex-direction: column;
}

.title {
  width: 420px;
  font-size: 84px;
  font-weight: 700;
  line-height: 88px;
}

.sub-title {
  width: 420px;
  font-size: 14px;
  margin-top: 30px;
}

.sub-title button {
  font-size: 14px;
  padding: 15px 30px;
  background: black;
  color: white;
  border-radius: 8mm;
  border: none;
  outline: none;
  cursor: pointer;
  margin-top: 20px;
}

.sign-up-button {
  color:white;
  text-decoration: none;
}

</style>
