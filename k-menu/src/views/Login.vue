<template>
  <div class="bg-primary-600">
    <div class="min-h-screen bg-black-100 py-6 flex flex-col justify-center sm:py-12">
      <div class="relative py-3 sm:max-w-xl sm:mx-auto w-full">
        <div
            class="absolute inset-0 bg-gradient-to-r from-primary-700 to-primary-800 shadow-lg transform -skew-y-6 sm:skew-y-0 sm:-rotate-6 sm:rounded-3xl">
        </div>
        <div class="relative px-4 py-10 bg-white shadow-lg sm:rounded-3xl sm:p-20">
          <div class="max-w-md mx-auto">
            <div>
              <h1 class="text-2xl font-semibold ">GİRİŞ YAP</h1>
            </div>
            <div class="divide-y divide-black-200">
              <div class="py-8 text-base leading-6 space-y-4 text-black-700 sm:text-lg sm:leading-7">
                <div class="relative">
                  <input v-model="email" autocomplete="off" id="email" name="email" type="text" class="peer placeholder-transparent h-10 w-full border-b-2 border-black-300 text-black-900 focus:outline-none focus:borer-rose-600"  />
                  <label for="email" class="absolute left-0 -top-3.5 text-black-600 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-black-440 peer-placeholder-shown:top-2 transition-all peer-focus:-top-3.5 peer-focus:text-black-600 peer-focus:text-sm">Email Address</label>
                </div>
                <div class="relative">
                  <input v-model="password" autocomplete="off" id="password" name="password" type="password" class="peer placeholder-transparent h-10 w-full border-b-2 border-black-300 text-black-900 focus:outline-none focus:borer-rose-600" />
                  <label  for="password" class="absolute left-0 -top-3.5 text-black-600 text-sm peer-placeholder-shown:text-base peer-placeholder-shown:text-black-440 peer-placeholder-shown:top-2 transition-all peer-focus:-top-3.5 peer-focus:text-black-600 peer-focus:text-sm">Password</label>
                </div>
                <div class="relative">
                  <button class="bg-primary-800 hover:bg-primary-700 text-white rounded-md p-2  w-full mb-2 " v-on:click="loginUser">Giriş Yap</button>
                  <button class="bg-primary-800 hover:bg-primary-700 text-white rounded-md p-2  w-full" v-on:click="$router.push('/register')">Kayıt Ol</button>

                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import * as WebServiceUrl from "../config/WebServiceURL";
import axiosInstance from "../config/AxiosInstance";
import toast from "../mixins/toast.js";

export default {
  name: "Login",
  data() {
    return {
      drawer: false,
      email: "",
      password: "",
    };
  },
  methods: {
    async loginUser() {
      // Login servisine istek atmak için axios kullanabilirsiniz
      axiosInstance
        .post(WebServiceUrl.login, {
          email: this.email,
          password: this.password,
        })
        .then((response) => {
          // Servisten gelen token'ı localStorage'da saklayabilirsiniz
          localStorage.setItem("token", response.data.token);
          localStorage.setItem("name",response.data.name);
          localStorage.setItem("surname",response.data.surname);
          localStorage.setItem("userId",response.data.userId)
          // Giriş yaptıktan sonra bir sayfaya yönlendirebilirsiniz
          this.$router.push("/dashboard");
        })
        .catch((error) => {
          // Hata oluştuğunda alert ile hata mesajı gösterin
            toast.error("Giriş yaparken bir hata oluştu. Lütfen tekrar deneyin.");
          console.log(error);
        });
    },
  },
};
</script>
<style>
.gradient-bg {
  background: linear-gradient(to right, #4b79a1, #283e51);
}
</style>
