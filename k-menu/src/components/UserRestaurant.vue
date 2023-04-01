<template>
  <div class=" h-screen container mx-auto ">

    <div class="grid grid-cols-1 flex px-2">
      <Restaurant v-if="selectedRestaurant!=='' && showRestaurantEditPanel"
                  :restaurant="selectedRestaurant"
                  @close="closeEditPanel"
      ></Restaurant>
      <div v-else>
        <button class="bg-primary-700 text-white rounded-md p-2 m-2 w-full hover:bg-primary-900 "
                @click="showAddRestaurantDialog = !showAddRestaurantDialog"
        >Yeni Restoran Ekle
        </button
        >
        <div class="grid-cols-1 px-6  flex container ">
          <ul class=" flex flex-col w-full">
            <li v-for="restaurant in restaurants"
                class="shadow-lg my-1 flex grid-cols-2 hover:bg-primary-50 inline-flex items-center gap-x-2 py-3 px-4 text-sm bg-white border text-primary-800 -mt-px first:rounded-t-lg first:mt-0 last:rounded-b-lg ">
              <div class="w-full grid grid-cols-10">
                <div class="col-span-8 uppercase text-primary-700 font-bold flex-col flex justify-center">
                  {{ restaurant.name }}
                </div>
                <div>
                  <button
                      :key="restaurant.id"
                      class="rounded border-2 p-2 mx-0 border-primary-700 w-full hover:bg-primary-700 hover:text-white text-primary-700" @click="editRestaurant(restaurant)">Düzenle
                  </button>
                </div>
                <div>
                  <button
                      :key="restaurant.id"
                      class="rounded border-2 p-2 ml-1 border-sunset-orange-700 w-full text-sunset-orange-700 hover:bg-sunset-orange-700 hover:text-white" @click="deleteRestaurant(restaurant.id)">Sil
                  </button>
                </div>
              </div>
            </li>

          </ul>
        </div>
        <add-restaurant-dialog
            :dialog="showAddRestaurantDialog"
            :user-id="userId"
            @close="showAddRestaurantDialog=false"
            @save="addRestaurant"
        />
      </div>
    </div>

  </div>
</template>

<script>
import AddRestaurantDialog from "../components/AddRestaurantDialog.vue";
import axiosInstance from "../config/AxiosInstance";
import * as WebServiceUrl from "../config/WebServiceURL";
import * as Util from "../utils/util.js";
import Restaurant from "./Restaurant.vue";

export default {
  components: {
    Restaurant,
    AddRestaurantDialog,
  },
  props: {
    userId: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      restaurants: [],
      showAddRestaurantDialog: false,
      selectedRestaurant: {},
      showRestaurantEditPanel: false,
    };
  },
  methods: {
    fetchRestaurants() {
      axiosInstance
          .get(WebServiceUrl.getRestaurantByUserId + `/${this.userId}`)
          .then((response) => {
            this.restaurants = response.data.restaurants;
          })
          .catch((error) => {
            console.log(error);
          });
    },
    addRestaurant(restaurant) {
      console.log(restaurant);
      this.restaurants.push(restaurant);
      this.showAddRestaurantDialog = false;

      const formData = new FormData();
      formData.append("file", restaurant.backgroundImgFile);
      formData.append("restaurantInfo", JSON.stringify({
        id: Util.generateUUID(),
        name: restaurant.restaurantName,
        contact: restaurant.contactInfo,
        adminUserInfo: this.userId
      }));

      axiosInstance.post(WebServiceUrl.createRestaurant, formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }).then((response) => {
        console.log(response);
      }).catch((error) => {
        console.error(error);
      });
    },

    editRestaurant(restaurant) {
      this.selectedRestaurant = restaurant;
      this.showRestaurantEditPanel = true;
    },
    closeEditPanel(){
      this.showRestaurantEditPanel = false;
      this.selectedRestaurant = '';
    },
    deleteRestaurant(restaurantId) {
      // TODO: Implement restaurant deletion functionality
      console.log("Deleting restaurant with ID:", restaurantId);
    },
  },
  created() {
    this.fetchRestaurants();
  },
};
</script>
