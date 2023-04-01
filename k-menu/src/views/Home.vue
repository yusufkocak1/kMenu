<template>
  <div class="max-w-2xl mx-auto bg-white rounded-xl shadow-md overflow-hidden md:max-w-2xl container p-2 mt-10">
    <div class="max-w-4xl">
      <div v-for="category in getCategory">
        <h1 class="border-2 bg-primary-900 text-white my-2 p-2">{{ category.name }}</h1>
        <div v-for="(item, index) in items.filter(product=>product.category.id === category.id)" :key="index"
             class="mx-2 grid grid-cols-4 border border-primary-700 p-3 rounded-xl shadow-lg -my-1">
          <div :key="index" class="col-span-1">{{ item.name }}</div>
          <div :key="index" class="col-span-1">{{ item.category.name }}</div>
          <div :key="index" class="col-span-1">{{ item.price }} TL</div>
          <button
              class="col-span-1 bg-primary-700 hover:bg-white border hover:border-primary-700 hover:text-primary-700 text-white px-2 py-1 rounded-md "
              @click="addBasket(item.id)">Sepete ekle
          </button>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import axiosInstance from "../config/AxiosInstance.js";
import * as WebServiceUrl from "../config/WebServiceURL.js";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Home.vue",
  data() {
    return {
      restaurantName: "",
      message: "Lütfen bekleyin, restoran bilgileri yükleniyor...",
      items: []
    };
  },
  created() {
    // Web servisimizden restoran bilgilerini alıyoruz
    this.getProduct();

  },
  methods: {
    showMenu() {
      // Menü sayfasına yönlendiriyoruz
      this.$router.push({
        name: "MenuPage",
        params: {id: this.$route.params.id},
      });
    },
    addBasket(id) {
      alert(this.items.filter(item => item.id == id))
    },
    getProduct() {
      axiosInstance.get(WebServiceUrl.getProduct + this.$route.params.restaurantId).then(response => {
        this.items = response.data
        console.log(this.items)
      }).catch(error => alert(error))
    },
    callWaiter() {
      // Garson çağırma işlemlerini burada yapabilirsiniz
      alert("Garson çağrıldı.");
    },
    removeDuplicates(arr) {
      let uniqueArr = [];
      for (let i = 0; i < arr.length; i++) {
        if (uniqueArr.indexOf(arr[i]) === -1) {
          uniqueArr.push(arr[i]);
        }
      }
      return uniqueArr;
    }

  },
  computed: {
    getCategory() {

      return this.removeDuplicates(this.items.map(item => item.category))
    }
  }
};
</script>
