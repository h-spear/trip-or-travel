import { createApp } from 'vue';
import { createPinia } from 'pinia';
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';
import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css';
import { VueDraggableNext } from 'vue-draggable-next';

import App from './App.vue';
import router from './router';

import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import {
  faEnvelope,
  faLock,
  faUser,
  faUpDownLeftRight,
  faXmark,
  faHeart,
  faPlus
} from '@fortawesome/free-solid-svg-icons';
import { faHeart as regularHeart } from '@fortawesome/free-regular-svg-icons';

import 'ant-design-vue/dist/reset.css';
import Antd from 'ant-design-vue';
import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

const app = createApp(App);

library.add(faEnvelope, faLock, faUser, faUpDownLeftRight, faXmark, faHeart, faPlus, regularHeart);

const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);
app.use(pinia);
app.component('VueDatePicker', VueDatePicker);
app.component('VueDraggableNext', VueDraggableNext);
app.component('font-awesome-icon', FontAwesomeIcon);
app.use(router);
app.use(Antd);
app.mount('#app');
