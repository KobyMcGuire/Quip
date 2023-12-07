<template>
    <h1></h1>
  <div>{{ correctAnswers }}</div>
</template>

<script>
export default {
    data(){
        return {
            correctAnswers: 0,
        }
    },

    created(){
        this.correctAnswers = this.$store.state.correctAnswers;
    }
    

}
</script>

<style>

</style>