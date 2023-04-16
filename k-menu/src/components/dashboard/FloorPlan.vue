<template>
    <div class="flex flex-col items-center">
        <div class="flex justify-center space-x-4 mb-4">
            <label v-for="mode in modes" :key="mode" class="flex items-center cursor-pointer">
                <input v-model="selectedMode" :value="mode"
                       class="mr-2  checked:border-2 checked:bg-primary-700 shadow-2xl"
                       type="radio">
                <span class="text-sm  font-bold text-primary-700">{{ mode }}</span>
            </label>
            <label class="p-1">Bölüm: <select id="sectionSelector" class="p-2 rounded border border-primary-700 " v-model="selectedSection"  @change="selectSection">
                <option v-for="(section, index) in sections" :key="section.id" :value="section.id" :selected="index === 0" class="text-primary-700">{{ section.name }}</option>
            </select>
            </label>
            <button class="rounded bg-primary-700 text-white border border-primary-700 hover:bg-white hover:text-primary-700 p-1"
                    @click="isShowAddSectionModal=true">Bölüm Ekle
            </button>
            <button class="rounded bg-primary-700 text-white border border-primary-700 hover:bg-white hover:text-primary-700 p-1"
                    @click="isShowAddTableModal=true">Masa Ekle
            </button>
        </div>
        <Modal
                id="addSectionModal"
                :bodyModel="addSectionModel"
                :isOpen="isShowAddSectionModal"
                cancel-btn="İptal"
                confirmBtn="Kaydet"
                header="Bölüm ekle"
                @close="isShowAddSectionModal=false"
                @ok="addSection"
        ></Modal>
        <Modal
            id="addTableModal"
            :bodyModel="addTableModel"
            :isOpen="isShowAddTableModal"
            cancel-btn="İptal"
            confirmBtn="Kaydet"
            header="Masa Ekle"
            @close="isShowAddTableModal=false"
            @ok="addTables"
        ></Modal>

        <div class="w-full max-w-full">
            <div v-if="selectedMode === 'List'" class="bg-white shadow overflow-hidden sm:rounded-md">
                <FloorList :selected-section="getSelectionSection" :tables="getTablesBySelectedSection"/>
            </div>
            <div v-else-if="selectedMode === 'Grid'" class="bg-white shadow overflow-hidden sm:rounded-md">
                <FloorGrid/>
            </div>
            <div v-else-if="selectedMode === 'Map'" class="bg-white shadow overflow-hidden sm:rounded-md">
                <FloorMap/>
            </div>
        </div>
    </div>
</template>

<script>
import FloorList from './FloorList.vue'
import FloorGrid from './FloorGrid.vue'
import FloorMap from './FloorMap.vue'
import Modal from "./common/Modal.vue";
import axiosInstance from "../../config/AxiosInstance.js";
import * as WebServiceUrl from "../../config/WebServiceURL.js";
import {useToast} from "vue-toastification";
import {addTables} from "../../config/WebServiceURL.js";

const toast = useToast()

export default {
    data() {
        return {
            selectedMode: 'List',
            modes: ['List', 'Grid', 'Map'],
            sections: [],
            tables:[],
            selectedSection: Object,
            isShowAddSectionModal: false,
            isShowAddTableModal:false,
            addSectionModel: [
                {
                    label: "Bölüm Adı",
                    type: "text"
                }
            ],
            addTableModel:[
                {
                    label: "Masa Adı",
                    type: "text"
                }
            ]
        }
    },
    props: {
        restaurant: {},
    },
    mounted() {
        this.getTables();
    },
    created() {
        this.getSection();

    },
    methods: {
        getSection() {
            axiosInstance.get(WebServiceUrl.getFloors + this.restaurant.id)
                .then(response =>{
                    this.sections = response.data
                    this.selectedSection = this.sections[0].id ;
                })
                .catch(error => toast.error("Bölümler Alınamadı : " + error))
        },
        getTables(){
            axiosInstance.get(WebServiceUrl.getTables + this.restaurant.id)
                .then(response => this.tables = response.data)
                .catch(error => toast.error("Masa Bilgileri Alınamadı : " + error))
        },
        addSection() {
            const formData = new FormData();
            formData.append('restaurantId', this.restaurant.id);
            formData.append('name', this.addSectionModel[0].value);

            axiosInstance.post(WebServiceUrl.addFloor, formData, {headers: {"Content-Type": "application/json"}})
                .then(response => {
                    toast.success("Bölüm eklendi.");
                    this.sections.push(response.data)
                }).catch(error => {
                toast.error("Bölüm eklenirken bir hata oluştu : " + error);
            })
        },
        addTables() {
            const formData = new FormData();
            formData.append('restaurantId', this.restaurant.id);
            formData.append('floorId',this.selectedSection)
            formData.append('name', this.addTableModel[0].value);

            axiosInstance.post(WebServiceUrl.addTables, formData, {headers: {"Content-Type": "application/json"}})
                .then(response => {
                    toast.success("Masa eklendi.");
                    this.tables.push(response.data)
                }).catch(error => {
                toast.error("Masa eklenirken bir hata oluştu : " + error);
            })
        },
        selectSection(){
           // toast.info("seçilen bölüm : " + this.sections.find(section=>section.id === this.selectedSection).name)

        }
    },
    computed:{
        getSelectionSection(){
            return this.sections.find(section=>section.id === this.selectedSection)
        },
        getTablesBySelectedSection(){
            return this.tables.filter(table=>table.floor.id === this.selectedSection)
        }
    },
    components: {
        Modal,
        FloorList,
        FloorGrid,
        FloorMap
    }
}
</script>

<style>
label input[type="radio"] {
    appearance: none;
    -webkit-appearance: none;
    -moz-appearance: none;
    width: 1.5rem;
    height: 1.5rem;
    border: 1px solid #e5e7eb;
    border-radius: 50%;
    outline: none;
    transition: all 0.15s ease-in-out;
}

label input[type="radio"]:checked {
    background-size: 50% 50%;
    background-position: center;
    background-repeat: no-repeat;
}

label input[type="radio"]:focus {
    box-shadow: 0 0 0 2px rgba(79, 70, 229, 0.5);
}

label span {
    font-size: 0.875rem;
}

@media (min-width: 640px) {
    label span {
        font-size: 1rem;
    }
}
</style>
