<template>
    <div>
        <nav class="relative flex flex-wrap items-center px-2 py-3 bg-shiraz-800  shadow-lg justify-center">
            <h1 class="flex justify-center text-white text-4xl font-extrabold">{{ restaurant.name }}</h1>
        </nav>
        <div class="max-w-4xl mx-auto bg-white rounded-xl shadow-md overflow-hidden md:max-w-3xl container p-2">
            <div class="max-w-4xl">
                <MenuList v-if="showMenu"
                          :items="items"
                          @close="showMenu=!showMenu"
                          @showShoppingCart="showShoppingCart"
                          :basket="basket"
                />
                <ShoppingCart v-else-if="isShoppingCartShow" @close="closeShoppingCartAndShowMenu" :basket="basket"></ShoppingCart>

                <div v-else class=" grid grid-cols-1">
                    <img :src="imageUrl"/>
                    <button
                            class="col-span-1 mt-2 p-2 border-2 border-shiraz-700 bg-white text-shiraz-700 hover:bg-shiraz-700 hover:text-white rounded font-bold"
                            @click="callWaiter">Garson Çağır
                    </button>
                    <button
                            class="col-span-1 mt-2 p-2 border-2 border-shiraz-700 bg-white text-shiraz-700 hover:bg-shiraz-700 hover:text-white rounded font-bold"
                            @click="showMenu=!showMenu">Menuyü Göster
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axiosInstance from "../config/AxiosInstance.js";
import * as WebServiceUrl from "../config/WebServiceURL.js";
import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome'
import {faCartShopping} from '@fortawesome/free-solid-svg-icons'
import {library} from "@fortawesome/fontawesome-svg-core";
import MenuList from "../components/menu/MenuList.vue";
import ShoppingCart from "../components/menu/ShoppingCart.vue";

export default {
    // eslint-disable-next-line vue/multi-word-component-names
    components: {
        ShoppingCart,
        MenuList,
        FontAwesomeIcon
    },
    name: "Home.vue",
    data() {
        return {
            restaurant: {},
            message: "Lütfen bekleyin, restoran bilgileri yükleniyor...",
            items: [],
            showMenu: false,
            imageUrl: "",
            restaurantId: "",
            isShoppingCartShow:false,
            basket:[],
        };
    },
    created() {
        this.restaurantId = this.$route.params.restaurantId;

        this.getProduct();
        library.add(faCartShopping);

    },
    methods: {
        getProduct() {
            axiosInstance.get(WebServiceUrl.getProduct + this.restaurantId).then(response => {
                this.items = response.data
                console.log(this.items)
            }).catch(error => alert(error))
        },
        callWaiter() {
            alert("Garson çağrıldı.");
        },
        showShoppingCart(){
            this.showMenu = false;
            this.isShoppingCartShow = true;
        },
        closeShoppingCartAndShowMenu(){
            this.isShoppingCartShow = false;
            this.showMenu = true;
        }


    },
    computed: {},
    mounted() {
        axiosInstance.get(WebServiceUrl.getRestaurant + this.restaurantId).then(restaurantResponse => {
            this.restaurant = restaurantResponse.data;
            axiosInstance.get(WebServiceUrl.getBGImageUrl + this.restaurant.backgroundImgUrl, {responseType: 'arraybuffer'})
                .then(response => {
                    const base64 = btoa(
                        new Uint8Array(response.data).reduce((data, byte) => data + String.fromCharCode(byte), '')
                    );
                    this.imageUrl = 'data:image/jpeg;base64,' + base64;
                })
                .catch(error => {
                    console.error(error);
                });
        });


    }
};
</script>
