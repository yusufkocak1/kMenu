import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router'
import { library } from '@fortawesome/fontawesome-svg-core'
import { faSignOutAlt, faBars, faSave } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import Toast, {POSITION} from "vue-toastification";
// Import the CSS or use your own!
import "vue-toastification/dist/index.css";


library.add(faSignOutAlt, faBars,faSave)

createApp(App).use(router).use(Toast,{position:POSITION.TOP_CENTER}).component('font-awesome-icon', FontAwesomeIcon).mount('#app')
