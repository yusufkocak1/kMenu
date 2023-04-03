<template>
    <div>
        <nav class="relative flex flex-wrap items-center px-2 py-3 bg-shiraz-800  shadow-lg justify-center">
            <h1 class="flex justify-center text-white text-4xl font-extrabold">{{ restaurant.name }}</h1>
        </nav>
        <div class="max-w-4xl mx-auto bg-white rounded-xl shadow-md overflow-hidden md:max-w-3xl container p-2">
            <div class="max-w-4xl">
                <div v-if="showMenu">
                    <div class="grid grid-cols-5">
                        <button class=" w-8 h-8 rounded-full p-0 border-2 border-sunset-orange-700 text-sunset-orange-700 hover:bg-sunset-orange-700 hover:text-white col-span-4 mr-1"
                                @click="showMenu=!showMenu">X
                        </button>
                        <button class="flex items-center justify-center bg-rust-700 text-white text-lg font-bold py-2 rounded-full col-span-1 w-20">
                            <FontAwesomeIcon icon="fa-solid fa-cart-shopping"/>
                            <span class="ml-2 bg-mountain-meadow-600 rounded-full h-4 w-6 flex items-center justify-center text-white text-sm">{{ count }}</span>
                        </button>
                    </div>
                    <div v-for="category in getCategory">
                        <h1 class="border-2 bg-mountain-meadow-800 text-white my-2 p-2 rounded-t-xl  flex justify-center font-bold">
                            {{ category.name }}</h1>
                        <div v-for="(item, index) in items.filter(product=>product.category.id === category.id)"
                             :key="index"
                             class="mx-2 grid grid-cols-4 border border-shiraz-700 p-3 rounded-xl shadow-lg my-1 justify-center items-center">
                            <div :key="index" class="col-span-1 text-shiraz-700 font-bold">{{ item.name }}</div>
                            <div :key="index" class="col-span-1"></div>
                            <div :key="index" class="col-span-1 text-shiraz-700 font-bold">{{ item.price }} TL</div>
                            <button
                                    class="col-span-1 bg-shiraz-700 hover:bg-white border hover:border-shiraz-700 hover:text-shiraz-700 text-white px-2 py-1 rounded-md "
                                    @click="addBasket(item.id)">Sepete ekle
                            </button>
                        </div>
                    </div>
                </div>
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

export default {
    // eslint-disable-next-line vue/multi-word-component-names
    components:{
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
            count: 0
        };
    },
    created() {
        this.restaurantId = this.$route.params.restaurantId;

        this.getProduct();
        library.add(faCartShopping);

    },
    methods: {
        addBasket(id) {
            this.count++
        },
        getProduct() {
            axiosInstance.get(WebServiceUrl.getProduct + this.restaurantId).then(response => {
                this.items = response.data
                console.log(this.items)
            }).catch(error => alert(error))
        },
        callWaiter() {
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
