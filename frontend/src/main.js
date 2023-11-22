import { createApp } from 'vue';
import { createPinia } from 'pinia';
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate';

import App from './App.vue';
import router from './router';

import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faEnvelope, faLock, faUser } from '@fortawesome/free-solid-svg-icons';

import 'ant-design-vue/dist/reset.css';
import Antd from 'ant-design-vue';
import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

const app = createApp(App);

library.add(faEnvelope, faLock, faUser);

const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);
app.use(pinia);
app.component('font-awesome-icon', FontAwesomeIcon);
app.use(router);
app.use(Antd);
app.mount('#app');
