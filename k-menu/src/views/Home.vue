<template>
  <div class="max-w-md mx-auto bg-white rounded-xl shadow-md overflow-hidden md:max-w-2xl">
    <div class="md:flex">
      <div class="md:flex-shrink-0">
        <img class="h-48 w-full object-cover md:h-full md:w-48" src="/img/store.jpg" alt="Man looking at item at a store">
      </div>
      <div class="p-8">
        <div class="uppercase tracking-wide text-sm text-indigo-500 font-semibold">Case study</div>
        <a href="#" class="block mt-1 text-lg leading-tight font-medium text-black hover:underline">Finding customers for your new business</a>
        <p class="mt-2 text-black-500">Getting a new business off the ground is a lot of hard work. Here are five ideas you can use to find your first customers.</p>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Home.vue",
  data() {
    return {
      restaurantName: "",
      message: "Lütfen bekleyin, restoran bilgileri yükleniyor...",
    };
  },
  created() {
    // Web servisimizden restoran bilgilerini alıyoruz
    axios
        .get(`/api/restaurants/${this.$route.params.id}`)
        .then((response) => {
          this.restaurantName = response.data.name;
          this.message = `Masanızın numarası: ${this.$route.params.tableNumber}`;
        })
        .catch((error) => {
          console.log(error);
          this.message = "Restoran bilgileri alınamadı, lütfen tekrar deneyin.";
        });
  },
  methods: {
    showMenu() {
      // Menü sayfasına yönlendiriyoruz
      this.$router.push({
        name: "MenuPage",
        params: { id: this.$route.params.id },
      });
    },
    callWaiter() {
      // Garson çağırma işlemlerini burada yapabilirsiniz
      alert("Garson çağrıldı.");
    },
  },
};
</script>
