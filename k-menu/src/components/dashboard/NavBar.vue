<template>

  <nav class="relative flex flex-wrap items-center justify-between px-2 py-3 bg-primary-800  shadow-lg ">
    <div class="container px-4 mx-auto flex flex-wrap items-center justify-between">
      <div class="w-full relative flex justify-between lg:w-auto px-4 lg:static lg:block lg:justify-start">
        <a class="text-sm font-bold leading-relaxed inline-block mr-4 py-2 whitespace-nowrap uppercase text-white"
           href="/public">
          K-Menu
        </a>
        <button
            class="text-white cursor-pointer text-xl leading-none px-3 py-1 border border-solid border-transparent rounded bg-transparent block lg:hidden outline-none focus:outline-none"
            type="button" v-on:click="toggleNavbar()">
          <FontAwesomeIcon icon="bars" />
        </button>
      </div>
      <div class="lg:flex lg:flex-grow items-center">
        <ul class="flex flex-col lg:flex-row list-none ml-auto">

          <li v-if="showMenu" class="nav-item items-center lg:hidden">
            <a class="px-3 py-2 flex items-center text-xs uppercase font-bold leading-snug text-white hover:opacity-75 rounded-md border-2 border-black-900 hover:bg-black-900"
               href="/exit">
              <FontAwesomeIcon icon="sign-out-alt" />
              <span class="ml-2">Çıkış Yap</span>
            </a>
          </li>
          <li v-if="isLogin" class="nav-item items-center hidden lg:block">
            <a class="px-3 py-2 flex items-center text-xs uppercase font-bold leading-snug text-white hover:opacity-75 rounded-md border-2 border-white hover:bg-black-900"
               href="/exit">
              <FontAwesomeIcon icon="sign-out-alt" />
              <span class="ml-2">Çıkış Yap</span>
            </a>
          </li>
          <li v-else class="nav-item items-center hidden lg:block">
          <a class="px-3 py-2 flex items-center text-xs uppercase font-bold leading-snug text-white hover:opacity-75 rounded-md border-2 border-white hover:bg-black-900"
             href="/login">
            <FontAwesomeIcon icon="sign-in-alt" />
            <span class="ml-2">Giriş Yap</span>
          </a>
        </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { faSignOutAlt,faSignInAlt, faBars } from '@fortawesome/free-solid-svg-icons'
import {library} from "@fortawesome/fontawesome-svg-core";

export default {
  components: {
    FontAwesomeIcon
  },
  data() {
    return {
      tab: null,
      showMenu: false,
      items: ["web", "shopping", "videos", "images", "news"],
    };
  },
  methods: {
    toggleNavbar() {
      this.showMenu = !this.showMenu;
    },
  },
  created() {
    library.add(faSignOutAlt, faBars,faSignInAlt);
  },computed:{
    isLogin(){
      return localStorage.getItem("token");
    }
  }
};
</script>

<style>
.nav-item:hover {
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
}
</style>
