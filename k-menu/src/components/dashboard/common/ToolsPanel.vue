<template>
    <div class=" relative">
        <div class=" flex justify-center p-2 border-b mb-2 ">
            <div class="border-2 hover:border-sunset-orange-700 p-1 rounded"  @click="isOpen = !isOpen">
                <button class="w-6 h-6 text-gray-600 ">
                    <span v-if="isOpen">🔼</span>
                    <span v-else>🔽</span>
                </button>
                Araçlar
            </div>
            <div class="border-2 bg hover:border-sunset-orange-700 ml-2 p-1 rounded flex justify-center">
                <button class=" px-2  ">
                    <font-awesome-icon class="w-6 h-6" icon="fa-solid fa-save"></font-awesome-icon>
                </button>
                Kaydet
            </div>
        </div>
        <div v-if="isOpen"
             class="p-5 absolute bg-white border-2 border-shiraz-900 flex justify-center w-11/12 shadow-2xl text-shiraz-900 rounded-lg  mx-10">
            <!-- Buraya şekilleri ekleyebilirsiniz -->
            <ul class="clear-none flex">
                <li class="mx-10">masa</li>
                <li class="mx-10">kapı</li>
            </ul>

        </div>
    </div>
</template>

<script>
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";

export default {
    components: {FontAwesomeIcon},
    data() {
        return {
            isOpen: false
        }
    }
}
</script>

<style>
button {
    cursor: pointer;
}
</style>
