<template>
    <div>
        <PanelHeader :title="restaurant.name" @close="$emit('close')"></PanelHeader>
        <div id="body">
            <ul v-for="section in sections">
                <li v-for="table in getTablesBySection(section)" :class="getTableStatus(table)">
                    {{ table.name }}
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
import SockJS from '@ssockjs-client';

import { Stomp } from '@stomp/stompjs';


export default {
    name: "OrderManageParent",
    components: {PanelHeader},
    props: {
        restaurant: {}
    },
    data() {
        return {
            tables: Array(),
            sections: Array(),
            orders: Array(),
        }
    },
    computed: {},
    created() {
        this.getSection();
        this.getTables();

    },
    mounted() {
        this.orderService();

    },
    methods: {
        getTablesBySection(section) {
            return this.tables.filter(table => table.floor.id === section.id)
        },
        getSection() {
            axiosInstance.get(WebServiceUrl.getFloors + this.restaurant.id)
                .then(response => {
                    this.sections = response.data
                })
                .catch(error => toast.error("Bölümler Alınamadı : " + error))
        },
        getTables() {
            axiosInstance.get(WebServiceUrl.getTables + this.restaurant.id)
                .then(response => {
                    this.tables = response.data
                    console.log(this.tables)
                })
                .catch(error => toast.error("Masa Bilgileri Alınamadı : " + error))
        },
        getTableStatus(table) {
            return table.status === 'avail' ? 'border-mountain-meadow-700' : table.status === 'onStart' ? 'border-blue-700' : table.status === 'alert' ? 'border-sunset-orange-700' : table.status === 'active' ? 'border-b-rust-600' : ''
        },
        orderService() {

            let socket = new SockJS(WebServiceUrl.orderService);
            let stompClient = Stomp.over(socket);
            stompClient.connect({}, function(frame) {
                console.log('Connected to the WebSocket server');
/*
                let subscription = stompClient.subscribe('/queue/orders', function(message) {
                    console.log('Received a new order:', JSON.parse(message.body));
                }, {'restaurantId': this.restaurant.id});
*/
                //console.log('Subscribed to the WebSocket server with parameters', subscription.id, subscription.headers);
            });


        }
    }
}
</script>

<style scoped>

</style>