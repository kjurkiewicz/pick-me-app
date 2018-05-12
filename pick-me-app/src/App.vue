<template>
  <div id="app">
    <ryanair-navbar> </ryanair-navbar>
    <google-map></google-map>
    <!-- <my-map></my-map> -->
  </div>
</template>

<script>
import Hello from './components/HelloWorld'
import RyanairNavbar from './components/NavBar'
import PickMe from './components/PickMe'
import TestComp from './components/TestComp'
import MyMap from './components/MyMap'
import { Nav, Navbar} from 'bootstrap-vue/es/components'
import GoogleMap from "./components/GoogleMap";

export default {
  name: 'app',
  components: {
    Hello,
    RyanairNavbar,
    PickMe,
    TestComp,
    MyMap,
    GoogleMap
  }
}
</script>

<style>
#app {
  font-family: 'Roboto, sans serif';
  -webkit-font-smoothing: antialiased;
  text-decoration-style: solid;
  font-weight: normal;
  text-align: center;
  font-size:1.5em;
  color: #2c3e50;
}
</style>
