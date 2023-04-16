<template>
  <div class="container w-full h-full rounded m-2 p-2 grid grid-cols-1">
    <div>
      <PanelHeader @close="$emit('close')"  :title="this.restaurant.name" ></PanelHeader>
      <div class="mx-2  border-primary-400 shadow-lg grid grid-cols-1 rounded p-2 h-full ">
        <ul class="grid grid-cols-4 m-2 w-full p-2">
          <li v-for="tab in tabs" @click="selected = tab" class="border-x last:rounded-tr-lg first:rounded-tl-lg border-2 shadow-lg list-none font-bold text-primary-700 uppercase hover:bg-primary-900 hover:text-white flex justify-center items-center" :class="selected===tab ? 'bg-primary-700 text-white border-x  border-b-2':''">{{tab}}</li>
        </ul>
        <div class="h-fit p-2">
          <menu-settings-parent :restaurant="this.restaurant" :key="tabs[0]" v-if="selected===tabs[0]"/>
            <FloorPlan v-else-if="selected===tabs[1]" :key="tabs[1]" :restaurant="restaurant"/>
        </div>
        </div>

    </div>
  </div>
</template>

<script>


import axiosInstance from "../../config/AxiosInstance.js";
import * as WebServiceUrl from "../../config/WebServiceURL.js";
import MenuSettingsParent from "./menu/MenuSettingsParent.vue";
import PanelHeader from "./common/PanelHeader.vue";
import FloorPlan from "./FloorPlan.vue";

export default {
  components: {
      FloorPlan,
      PanelHeader,
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
      console.log(this.restaurant)
  },
};
</script>