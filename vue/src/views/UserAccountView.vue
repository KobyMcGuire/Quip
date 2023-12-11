<template>
    <div id="user">
        <h1>User Profile</h1>

    </div>
    <div class="profile-picture">
        <img src="../assets/pfp.jpg" alt="" />
    </div>
    <div class="username">
        <label for="username">Username </label>
        <input type="text" id="username" v-model="user.username" required autofocus />
    </div>
    <div class="password">
        <label for="password">Password </label>
        <input type="text" id="password" v-model="user.password" required autofocus />
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
<style>
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
</style>