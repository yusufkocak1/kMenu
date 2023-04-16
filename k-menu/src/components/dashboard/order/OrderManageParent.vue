<template>
    <div>
        <PanelHeader @close="$emit('close')"  :title="restaurant.name" ></PanelHeader>
        <div id="body">
            <ul v-for="section in sections">
                <li v-for="table in getTablesBySection(section)" :class="getTableStatus(table)">
                    {{table.name}}
                </li>
            </ul>
        </div>
    </div>


</template>

<script>
import PanelHeader from "../common/PanelHeader.vue";
import axiosInstance from "../../../config/AxiosInstance.js";
import * as WebServiceUrl from "../../../config/WebServiceURL.js";
import toast from "../../../mixins/toast.js";

export default {
    name: "OrderManageParent",
    components: {PanelHeader},
    props:{
        restaurant:{}
    },
    data(){
        return {
            tables:Array,
            sections:Array
        }
    },
    computed:{

    },
    created() {
        this.getSection();
        this.getTables()
    },
    methods:{
        getTablesBySection(section){
            return this.tables.filter(table=>table.floor.id===section.id)
        },
        getSection() {
            axiosInstance.get(WebServiceUrl.getFloors + this.restaurant.id)
                .then(response =>{
                    this.sections = response.data
                })
                .catch(error => toast.error("Bölümler Alınamadı : " + error))
        },
        getTables(){
            axiosInstance.get(WebServiceUrl.getTables + this.restaurant.id)
                .then(response => this.tables = response.data)
                .catch(error => toast.error("Masa Bilgileri Alınamadı : " + error))
        },
        getTableStatus(table){
            return table.status ==='avail' ? 'border-mountain-meadow-700' : table.status === 'onStart'? 'border-blue-700': table.status === 'alert' ? 'border-sunset-orange-700' : table.status === 'active' ? 'border-b-rust-600':''
        }
    }
}
</script>

<style scoped>

</style>