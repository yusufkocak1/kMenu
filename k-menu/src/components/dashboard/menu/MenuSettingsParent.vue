<template>
  <div class="grid grid-cols-8 h-fif">
    <div class=" col-span-1 flex flex-col font-bold text-primary-700">
      <li v-for="panel in panels" @click="selectedPanel=panel"
          class="shadow-lg my-1 flex uppercase hover:bg-primary-900 hover:text-white inline-flex items-center gap-x-2 py-3 px-4 text-sm  border  -mt-px first:rounded-t-lg first:mt-0 last:rounded-b-lg"
          :class="panel===selectedPanel ? 'bg-primary-700 text-white':'bg-white text-primary-800'"
      >
        {{panel}}
      </li>
    </div>
    <div class="m-2 col-span-7">
      <category-parent :categories="categories" :restaurant-id="restaurant.id" v-if="selectedPanel===panels[0]"/>
      <product-settings-parent v-if="selectedPanel===panels[1]" :categories="categories" :restaurant="restaurant"/>

    </div>

  </div>
</template>

<script>
import CategoryParent from "./CategoryParent.vue"
import ProductSettingsParent from "./ProductSettingsParent.vue";
import axiosInstance from "../../../config/AxiosInstance.js";
import * as WebServiceURL from "../../../config/WebServiceURL.js";

export default {
  name: "MenuSettingsParent",
  components: {ProductSettingsParent, CategoryParent},
  props: {
    restaurant: {}
  },
  data() {
    return {
      panels:["KATEGORİ","ÜRÜNLER"],
      selectedPanel:"KATEGORİ",
      categories:[]
    }
  },
  methods: {},
  computed: {},
  created() {
    axiosInstance.post(WebServiceURL.getCategory+this.restaurant.id)
        .then(response=>{
            this.categories = response.data
        })
        .catch(error=>console.log(error))

  }
}
</script>

<style scoped>

</style>