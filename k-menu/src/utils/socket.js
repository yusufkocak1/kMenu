import { reactive } from "vue";
import { io } from "socket.io-client";
import * as WebServiceUrl from "../config/WebServiceURL.js";

export const state = reactive({
    connected: false,
    fooEvents: [],
    barEvents: []
});

// "undefined" means the URL will be computed from the `window.location` object
const URL = WebServiceUrl.orderService

export const socket = io(URL);

socket.on("connect", () => {
    console.log("connected")
    state.connected = true;
});

socket.on("disconnect", () => {
    state.connected = false;
});

socket.on("foo", (...args) => {
    state.fooEvents.push(args);
});

socket.on("bar", (...args) => {
    state.barEvents.push(args);
});