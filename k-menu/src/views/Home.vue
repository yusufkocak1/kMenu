<template>
  <div class="max-w-4xl mx-auto bg-white rounded-xl shadow-md overflow-hidden md:max-w-3xl container p-2 mt-10">
    <div class="max-w-4xl">
      <div v-if="showMenu">
        <button class=" w-8 h-8 rounded-full p-0 border-2 border-sunset-orange-700 text-sunset-orange-700 hover:bg-sunset-orange-700 hover:text-white" @click="showMenu=!showMenu">X</button>
        <div v-for="category in getCategory">
          <h1 class="border-2 bg-primary-900 text-white my-2 p-2 ">{{ category.name }}</h1>
          <div v-for="(item, index) in items.filter(product=>product.category.id === category.id)" :key="index"
               class="mx-2 grid grid-cols-4 border border-primary-700 p-3 rounded-xl shadow-lg my-1">
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
      <div v-else class=" grid grid-cols-1">
        <img :src="imageUrl"/>
        <button
            class="col-span-1 mt-10 p-2 border-2 border-primary-700 bg-white text-primary-700 hover:bg-primary-700 hover:text-white rounded"
            @click="callWaiter">Garson Çağır
        </button>
        <button
            class="col-span-1 mt-2 p-2 border-2 border-primary-700 bg-white text-primary-700 hover:bg-primary-700 hover:text-white rounded"
            @click="showMenu=!showMenu">Menuyü Göster
        </button>
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
      restaurant: {},
      message: "Lütfen bekleyin, restoran bilgileri yükleniyor...",
      items: [],
      showMenu: false,
      imageUrl:String,
      restaurantId:""
    };
  },
  created() {
    this.restaurantId = this.$route.params.restaurantId;

    this.getProduct();

  },
  methods: {

    addBasket(id) {
      alert(this.items.filter(item => item.id == id))
    },
    getProduct() {
      axiosInstance.get(WebServiceUrl.getProduct + this.restaurantIdd).then(response => {
        this.items = response.data
        console.log(this.items)
      }).catch(error => alert(error))
    },
    callWaiter() {
      // Garson çağırma işlemlerini burada yapabilirsiniz
      alert("Garson çağrıldı.");
    },


  },
  computed: {
    getCategory() {
      const categories = [];

      for (const item of this.items) {
        if (!categories.some(category => category.name === item.category.name)) {
          categories.push(item.category);
        }
      }

      return categories
    }
  },
  mounted() {
    axiosInstance.get(WebServiceUrl.getRestaurant + this.restaurantId).then(restaurantResponse=>{
      this.restaurant = restaurantResponse.data
      axiosInstance.get(WebServiceUrl + this.restaurant.backgroundImgUrl)
          .then(response  => {
            return response.arrayBuffer();
          })
          .then(imageBytes => {
            const base64 = btoa(
                new Uint8Array(imageBytes).reduce((data, byte) => data + String.fromCharCode(byte), "")
            );
            this.imageUrl = "data:image/jpeg;base64," + base64;
          })
          .catch(error => {
            console.error(error);
          });
    })


  }
};
</script>
