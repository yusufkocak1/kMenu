<template>
    <div
            class="fixed inset-0 z-50 flex items-center justify-center overflow-x-hidden overflow-y-auto"
            :class="{ 'opacity-0 pointer-events-none': !isOpen }"
    >
        <div
                class="relative w-auto max-w-md mx-auto my-6 z-50"
                :class="{ 'opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95': !isOpen }"
                @click.self="close"
        >
            <div class="bg-white rounded-lg shadow-xl pt-1">
                    <PanelHeader @close="close" :title="header"></PanelHeader>

                <div class="p-4">
                    <div v-if="bodyModel.length > 0">
                        <div v-for="(body, index) in bodyModel" :key="index" class="mb-4">
                            <template v-if="body.type === ''">
                                <p class="text-black-900">{{ body.label }}</p>
                            </template>
                            <template v-else>
                                <label class="block mb-2 font-medium text-black-700">{{ body.label }}</label>
                                <input v-model="body.value" :type="body.type" class="block w-full px-3 py-2 border rounded-md shadow-sm focus:outline-none focus:ring-primary-500 focus:border-primary-500 sm:text-sm" />
                            </template>
                        </div>
                    </div>
                    <div class="flex justify-end">
                        <button v-if="cancelBtn!== ''" @click="close" type="button" class="inline-flex justify-center px-4 py-2 text-sm font-medium text-white bg-sunset-orange-700 border border-black-300 rounded-md shadow-sm hover:bg-white hover:text-sunset-orange-700 hover:border hover:border-sunset-orange-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary-500">
                            {{ cancelBtn }}
                        </button>
                        <button v-if="confirmBtn!== ''" @click="ok" type="button" class="inline-flex justify-center px-4 py-2 ml-3 text-sm font-medium text-white bg-primary-600 border border-transparent rounded-md shadow-sm hover:bg-white hover:text-primary-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary-500">
                            {{ confirmBtn }}
                        </button>

                    </div>
                </div>
            </div>
        </div>
        <div class="fixed inset-0 transition-opacity bg-black-900 bg-opacity-75 z-20" aria-hidden="true" :class="{ 'opacity-0 pointer-events-none': !isOpen }"></div>
    </div>
</template>

<script>
import PanelHeader from "./PanelHeader.vue";

export default {
    components: {PanelHeader},
    props: {
        header: "",
        cancelBtn: "Cancel",
        confirmBtn: "Ok",
        otherActions: [{method:"asd",label:"asd"}],
        bodyModel: Array,
        isOpen: false

    },
    data() {
        return {
        }
    },
    methods: {
        close() {
            this.$emit('close')
        },
        ok() {
            this.close()
            this.$emit('ok')
        }
    }
}
</script>
