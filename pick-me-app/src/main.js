// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import * as VueGoogleMaps from "vue2-google-maps";

Vue.use(VueGoogleMaps, {
  load: {
    key: "klucz",
    libraries: "places" // necessary for places input
  }
});

Vue.use(BootstrapVue);
//import router from './router'

//Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
//  router,
  components: { App },
  template: '<App/>'
})
