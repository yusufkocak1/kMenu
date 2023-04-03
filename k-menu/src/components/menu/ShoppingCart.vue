<template>
    <div class="grid grid-rows-3">
        <div class="border-b-2 pb-2 grid grid-cols-6  flex  items-center shadow-lg mb-2">
            <h1 class="col-span-5 font-extrabold text-shiraz-700 text-xl">Alışveriş Sepeti</h1>
            <button class=" w-8 h-8 rounded-full p-0 border-2 border-sunset-orange-700 text-sunset-orange-700 hover:bg-sunset-orange-700 hover:text-white col-span-1 mr-1"
                    @click="$emit('close')">X
            </button>
        </div>
        <div v-for="(item, index) in getUniqBasketItem"
             :key="index"
             class="mx-2 grid grid-cols-4 border border-shiraz-700 p-3 rounded-xl shadow-lg my-1 justify-center items-center">
            <div :key="index" class="col-span-1 text-shiraz-700 font-bold">{{ item.name }}</div>
            <div :key="index" class="col-span-1"></div>
            <div :key="index" class="col-span-1 text-shiraz-700 font-bold">{{ item.price }} TL</div>
            <div>
                <button
                        class="col-span-1 bg-shiraz-700 hover:bg-white border hover:border-shiraz-700 hover:text-shiraz-700 text-white px-2 py-1 rounded-md "
                        @click="removeItemFromBasket(item)">-
                </button>
                <span class="p-1">{{ getItemCount(item.id) }}</span>

                <button
                        class="col-span-1 bg-shiraz-700 hover:bg-white border hover:border-shiraz-700 hover:text-shiraz-700 text-white px-2 py-1 rounded-md "
                        @click="addItemToBasket(item)">+
                </button>

            </div>
        </div>
        <div class=" mt-3">

            <div class=" grid grid-cols-3 ">
                <span class="col-span-2"></span>
                <span class=" col-span-1 flex justify-center items-center  drop-shadow-lg border-2  m-1 rounded text-xl font-bold text-shiraz-700">= {{
                    basket.reduce((previousValue, currentItem) => previousValue + parseFloat(currentItem.price), 0)
                    }}</span>

            </div>
            <button
                    class=" w-full p-2 border-2 hover:border-shiraz-700 hover:bg-white hover:text-shiraz-700 bg-shiraz-700 text-white rounded-xl font-bold"
            >Sipariş Ver
            </button>
        </div>
    </div>
</template>

<script>
export default {
    name: "ShoppingCart",
    props: {
        basket: []
    }, methods: {
        addItemToBasket(id) {
            this.basket.push(id)
        },
        removeItemFromBasket(item) {
            const index = this.basket.indexOf(item);
            if (index > -1) {
                this.basket.splice(index, 1);
            }
        },
        getItemCount(id) {
            return this.basket.filter(item => item.id === id).length;
        },
    },
    computed: {
        getUniqBasketItem() {
            const products = [];

            for (const item of this.basket) {
                if (!products.some(product => product.id === item.id)) {
                    products.push(item);
                }
            }

            return products.sort((a, b) => {
                if (a.id < b.id) {
                    return -1;
                } else if (a.id > b.id) {
                    return 1;
                } else {
                    return a.name.localeCompare(b.name);
                }
            });
        }

    }
}
</script>

<style scoped>

</style>