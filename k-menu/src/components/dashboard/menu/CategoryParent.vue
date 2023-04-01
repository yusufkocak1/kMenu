<template>
  <div class="grid grid-cols-1 h-full">
    <div >
    <input v-model="category.name" class="bg-white border border-primary-700 rounded w-full h-fit p-2 m-2"
           placeholder="Kategori ismi"/>
    <button class="bg-primary-700 text-white rounded-md p-2 m-2 w-full hover:bg-primary-900 " @click="this.editMode ? saveCategory() : addCategory()">
      {{ saveBtnLabel }}
    </button>
    </div>
    <li v-for="category in editableCategories"
        class="shadow-lg my-1 flex grid-cols-2 hover:bg-black-100 inline-flex items-center gap-x-2 py-3 px-4 text-sm bg-white border text-primary-800 -mt-px first:rounded-t-lg first:mt-0 first:rounded-t-lg last:rounded-b-lg w-full">
      <div class="w-full grid grid-cols-10">
        <div class="col-span-8 uppercase text-primary-700 font-bold flex-col flex justify-center">
          {{ category.name }}
        </div>
      <div class="lg:flex ">
        <div  class="col-span-1">
          <button
              :key="category.id"
              class="rounded border-2 p-2 mx-0 border-primary-700 w-full hover:bg-primary-700 hover:text-white text-primary-700"
              @click="editCategory(category)">Düzenle
          </button>
        </div>
        <div class="col-span-1">
          <button
              :key="category.id"
              class="rounded border-2 p-2 lg:md:ml-1 border-sunset-orange-700 w-full text-sunset-orange-700 hover:bg-sunset-orange-700 hover:text-white"
              @click="deleteCategory(category.id)">Sil
          </button>
        </div>
      </div>
      </div>
    </li>
  </div>

</template>

<script>
import axiosInstance from "../../../config/AxiosInstance.js";
import * as WebServiceUrl from "../../../config/WebServiceURL.js";

export default {
  name: "CategoryParent.vue",
  props: {
    categories: {
      type: Array,
      default: () => []
    },
    restaurantId: ""
  },
  data() {
    return {
      category: {},
      editMode: false,
      editableCategories: [],

    }
  },
  methods: {
    editCategory(category) {
      this.editMode = true;
      this.category = category;

    },
    deleteCategory(id) {
      console.log()
      axiosInstance.post(WebServiceUrl.deleteCategory + id,{}, {headers: {'Content-Type': 'application/json'}}).then(response => {
        this.editableCategories = this.editableCategories.filter(category=>category.id !==id)
      }).then(error => console.log(error))

    },

    addCategory() {
      const formData = new FormData();
      formData.append("restaurantId", this.restaurantId);
      formData.append("name", this.category.name);
      axiosInstance.post(WebServiceUrl.addCategory, formData, {headers: {'Content-Type': 'application/json'}}).then(response => {
        this.editableCategories.push(response.data)
      }).then(error => console.log(error))

    },
    saveCategory(){
      const formData = new FormData();
      formData.append("restaurantId", this.restaurantId);
      formData.append("name", this.category.name);
      axiosInstance.post(WebServiceUrl.editCategory + this.category.id, formData, {headers: {'Content-Type': 'application/json'}}).then(response => {
      }).then(error => console.log(error))

      this.editMode = false;
      this.category = {};
    }
  },
  computed: {
    saveBtnLabel() {
      return this.editMode ? "KAYDET" : "EKLE"
    }
  },
  watch: {
    categories(newVal) {
      this.editableCategories = newVal;
    },
  },
    created(){
      this.editableCategories = this.categories;

    },


}
</script>

<style scoped>

</style>