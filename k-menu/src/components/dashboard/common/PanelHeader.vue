<template>
    <div class=" mt-2 mx-2 border border-primary-700 shadow-lg grid grid-cols-12 rounded p-2">
        <h1 class="col-span-11 text-primary-700 font-bold text-2xl uppercase ">
            {{title}}
        </h1>
        <div class="flex flex-col items-end font-extrabold">
            <button class=" w-8 h-8 rounded-full p-0 border-2 border-sunset-orange-700 text-sunset-orange-700 hover:bg-sunset-orange-700 hover:text-white" @click="$emit('close')">X</button>
        </div>
    </div>
</template>

<script>
export default {
    name: "PanelHeader",
    props:{
        title:""
    }
}
</script>

<style scoped>

</style>