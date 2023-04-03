<template>
    <div >
        <div class="grid grid-cols-5 border-b pb-2 flex  items-center shadow-lg mb-2">
            <h1 class="col-span-3 font-extrabold text-shiraz-700 text-xl ">Menü</h1>

            <div  class=" grid grid-cols-3 col-span-2 ">
            <button class="flex items-center justify-center bg-rust-700 text-white text-lg font-bold py-2 rounded-full col-span-2 w-20" @click="$emit('showShoppingCart')">
                <FontAwesomeIcon icon="fa-solid fa-cart-shopping"/>
                <span class="ml-2 bg-mountain-meadow-600 rounded-full h-4 w-6 flex items-center justify-center text-white text-sm">{{ basket.length }}</span>
            </button>
            <button class=" w-8 h-8 rounded-full p-0 border-2 border-sunset-orange-700 text-sunset-orange-700 hover:bg-sunset-orange-700 hover:text-white col-span-1 ml-2 "
                    @click="$emit('close')">X
            </button>
            </div>
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
                        @click="addBasket(item)">Sepete ekle
                </button>
            </div>
        </div>
    </div>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";

export default {
    name: "MenuList",
    components:{
        FontAwesomeIcon
    },
    data(){
        return{
            count: 0
        }
    },
    props:{
        items:[],
        basket:[]
    },
    methods:{
        addBasket(product) {
            this.basket.push(product);
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
}
</script>

<style scoped>

</style>