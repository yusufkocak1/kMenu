<template>
  <div>
    <nav-bar/>
    <div class="flex justify-center mt-10">
      <div class="w-full md:w-1/2 lg:w-1/3 px-4">
        <div class="bg-white shadow-lg rounded-lg p-6">
          <h2 class="text-2xl font-bold mb-10 text-primary-700">Kayıt Formu</h2>
          <div>
            <div class="mb-4">
              <label class="block text-primary-700 font-bold mb-2" for="username">
                Ad
              </label>
              <input
                  id="name"
                  v-model="user.name"
                  class="appearance-none border rounded-md py-2 px-3 text-primary-700 leading-tight focus:outline-none focus:shadow-outline w-full"
                  placeholder="Ad"
                  type="text"
              />
            </div>
            <div class="mb-4">
              <label class="block text-primary-700 font-bold mb-2" for="username">
                Soyad
              </label>
              <input
                  id="soyad"
                  v-model="user.surname"
                  class="appearance-none border rounded-md py-2 px-3 text-primary-700 leading-tight focus:outline-none focus:shadow-outline w-full"
                  placeholder="Soyad"
                  type="text"
              />
            </div>
            <div class="mb-4">
              <label class="block text-primary-700 font-bold mb-2" for="email">
                Email Adresi
              </label>
              <input
                  id="email"
                  v-model="user.email"
                  class="appearance-none border rounded-md py-2 px-3 text-primary-700 leading-tight focus:outline-none focus:shadow-outline w-full"
                  placeholder="Email Adresi"
                  type="email"
              />
            </div>
            <div class="mb-4">
              <label class="block text-primary-700 font-bold mb-2" for="password">
                Şifre
              </label>
              <input
                  id="password"
                  v-model="user.password"
                  class="appearance-none border rounded-md py-2 px-3 text-primary-700 leading-tight focus:outline-none focus:shadow-outline w-full"
                  placeholder="Şifre"
                  type="password"
              />
            </div>
            <div class="mb-4">
              <label class="block text-primary-700 font-bold mb-2" for="password">
                Şifre tekrar
              </label>
              <input
                  id="confirmPassword"
                  v-model="user.confirmPassword"
                  class="appearance-none border rounded-md py-2 px-3 text-primary-700 leading-tight focus:outline-none focus:shadow-outline w-full"
                  placeholder="Şifre Tekrar"
                  type="password"
              />
            </div>
            <button
                class="bg-primary-800 hover:bg-primary-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline w-full"
                @click="registerUser"
            >
              Kayıt Ol
            </button>
          </div>
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
import {mapActions} from "vuex";
import NavBar from "../components/NavBar.vue";
import axiosInstance from "../config/AxiosInstance.js";
import * as WebServiceUrl from "../config/WebServiceURL";

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
      const formData = new FormData();
      formData.append("name", this.user.name);
      formData.append("surname", this.user.surname);
      formData.append("email", this.user.email);
      formData.append("password", this.user.password);
      axiosInstance
          .post(WebServiceUrl.register, formData, {headers: {'Content-Type': 'application/json'}})
          .then((response) => {
            alert(response.data);
            this.$router.push("/login");
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
