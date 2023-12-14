<template>
    <div id="user">
        <h1>User Profile</h1>

    </div>
    <div class="profile-picture">
        <img src="../assets/pfp.jpg" alt="" />
    </div>
    <div class="firstname">
        <label for="firstname">First Name: </label>
        <input type="text" id="firstname" v-model="user.username" required autofocus />
    </div>
    <div class="lastname">
        <label for="lastname">Last Name: </label>
        <input type="text" id="lastname" v-model="user.password" required autofocus />
    </div>
</template>
<script>
export default {
    components: {},
    data() {
        return {
            user: {
                username: "",
                password: ""
            },
        };
    },
    methods: {
        if(){
            
        }
    }
    
}

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
label {
  margin-right: 0.5rem;
}
</style>