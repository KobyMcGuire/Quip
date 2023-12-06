<template>
  <div class="dropdown">
    <button @click="toggleDropdown" class="studyDecks">Your Decks</button>
    <div v-if="showDropdown" id="studyDecks" class="dropdown-content">
      <!-- The content will be dynamically generated here -->
      <div v-for="item in itemList" :key="item.id" class="dropdown-item">
        <h3>{{ item.name }}</h3>
        <p>{{ item.description }}</p>
      </div>
    </div>
  </div>
</template>

<script>


</script>

<style>

</style>