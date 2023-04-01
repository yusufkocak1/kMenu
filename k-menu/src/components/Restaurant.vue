<template>
  <div class="container w-full h-full rounded m-2 p-2 grid grid-cols-1">
    <div>
      <!-- header start -->
      <div class=" mt-2 mx-2 border-2 border-primary-700 shadow-lg grid grid-cols-12 rounded p-2">
        <h1 class="col-span-11 text-primary-700 font-bold text-2xl uppercase ">
        {{restaurant.name}}
        </h1>
        <div class="flex flex-col items-end font-extrabold">
        <button class=" w-8 h-8 rounded-full p-0 border-2 border-sunset-orange-700 text-sunset-orange-700 hover:bg-sunset-orange-700 hover:text-white" @click="$emit('close')">X</button>
        </div>
        </div>
      <!-- header end -->
      <div class="mx-2  border-primary-400 shadow-lg grid grid-cols-1 rounded p-2 h-full ">
        <ul class="grid grid-cols-4 m-2 w-full p-2">
          <li v-for="tab in tabs" @click="selected = tab" class="border-x last:rounded-tr-lg first:rounded-tl-lg border-2 shadow-lg list-none font-bold text-primary-700 uppercase hover:bg-primary-900 hover:text-white flex justify-center items-center" :class="selected===tab ? 'bg-primary-700 text-white border-x  border-b-2':''">{{tab}}</li>
        </ul>
        <div class="h-fit p-2">
          <menu-settings-parent :restaurant="this.restaurant" v-if="selected===tabs[0]"></menu-settings-parent>
        </div>
        </div>

    </div>
  </div>
</template>

<script>


import axiosInstance from "../config/AxiosInstance.js";
import * as WebServiceUrl from "../config/WebServiceURL";
import MenuSettingsParent from "./dashboard/menu/MenuSettingsParent.vue";

export default {
  components: {
    MenuSettingsParent
  },
  props: {
   restaurant:{}
  },
  data() {
    return {
      tabs:["Menu Ayarları","Kat Planı","Kullanıcı Ayarları","Genel Ayarlar"],
      selected:"Menu Ayarları"
    };
  },
  methods: {
  },
  created() {
  },
};
</script>