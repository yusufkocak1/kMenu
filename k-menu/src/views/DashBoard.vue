<template>
  <div class="bg-black-100 flex-col justify-center items-center flex">
    <div class="shadow-lg w-screen mb-2">
      <nav-bar />
    </div>
    <div class=" h-full items-center flex container shadow-lg  bg-white">
    <UserRestaurant :user-id="getUserId"></UserRestaurant>

    </div>
  </div>
</template>

<script>

import NavBar from "../components/NavBar.vue";
import UserRestaurant from "../components/UserRestaurant.vue";

export default {
  name: "DashBoard",
  components: {UserRestaurant, NavBar},
  computed:{
    getUserId(){
      return localStorage.getItem("userId");
    }
  }
};
</script>

<style scoped></style>
