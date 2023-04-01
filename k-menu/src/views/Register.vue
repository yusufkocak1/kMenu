<template>
  <div>
    <nav-bar/>
    <div class="flex justify-center mt-10">
      <div class="w-full md:w-1/2 lg:w-1/3 px-4">
        <div class="bg-white shadow-lg rounded-lg p-6">
          <h2 class="text-2xl font-bold mb-10 text-primary-700">Kayıt Formu</h2>
          <form >
            <div class="mb-4">
              <label class="block text-primary-700 font-bold mb-2" for="username">
                Ad
              </label>
              <input
                  class="appearance-none border rounded-md py-2 px-3 text-primary-700 leading-tight focus:outline-none focus:shadow-outline w-full"
                  id="name"
                  v-model="user.name"
                  type="text"
                  placeholder="Ad"
              />
            </div>
            <div class="mb-4">
              <label class="block text-primary-700 font-bold mb-2" for="username">
                Soyad
              </label>
              <input
                  class="appearance-none border rounded-md py-2 px-3 text-primary-700 leading-tight focus:outline-none focus:shadow-outline w-full"
                  id="soyad"
                  v-model="user.surname"
                  type="text"
                  placeholder="Soyad"
              />
            </div>
            <div class="mb-4">
              <label class="block text-primary-700 font-bold mb-2" for="email">
                Email Adresi
              </label>
              <input
                  class="appearance-none border rounded-md py-2 px-3 text-primary-700 leading-tight focus:outline-none focus:shadow-outline w-full"
                  id="email"
                  v-model="user.email"
                  type="email"
                  placeholder="Email Adresi"
              />
            </div>
            <div class="mb-4">
              <label class="block text-primary-700 font-bold mb-2" for="password">
                Şifre
              </label>
              <input
                  class="appearance-none border rounded-md py-2 px-3 text-primary-700 leading-tight focus:outline-none focus:shadow-outline w-full"
                  id="password"
                  v-model="user.password"
                  type="password"
                  placeholder="Şifre"
              />
            </div>
            <div class="mb-4">
              <label class="block text-primary-700 font-bold mb-2" for="password">
                Şifre tekrar
              </label>
              <input
                  class="appearance-none border rounded-md py-2 px-3 text-primary-700 leading-tight focus:outline-none focus:shadow-outline w-full"
                  id="confirmPassword"
                  type="password"
                  v-model="user.confirmPassword"
                  placeholder="Şifre Tekrar"
              />
            </div>
            <button
                class="bg-primary-800 hover:bg-primary-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline w-full"
                @click="registerUser"
            >
              Kayıt Ol
            </button>
          </form>
        </div>
      </div>
    </div>

  </div>
</template>

<style>
.mx-auto {
  margin-left: auto;
  margin-right: auto;
}
</style>

<script>
import axios from "axios";
import { mapActions } from "vuex";
import NavBar from "../components/NavBar.vue";
import axiosInstance from "../config/AxiosInstance.js";

export default {
  name: "Register",
  components: {NavBar},
  data() {
    return {
      user: {
        name: "",
        surname: "",
        email: "",
        password: "",
        confirmPassword: "",
      },
    };
  },
  methods: {
    ...mapActions(["login"]),
    registerUser() {
      if (this.user.password !== this.user.confirmPassword) {
        alert("Şifreler aynı değil!");
        return;
      }

      axiosInstance
        .post("/api/v1/auth/users/public/register", this.user)
        .then((response) => {
          this.login(response.data.token);
          this.$router.push("/dashboard");
        })
          // eslint-disable-next-line no-unused-vars
        .catch((error) => {
          console.log(error);
          alert("Kayıt oluşturulurken hata oluştu!");
        });
    },
  },
};
</script>
