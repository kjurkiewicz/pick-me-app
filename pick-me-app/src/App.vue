<template>
  <div id="app">
    <ryanair-navbar> </ryanair-navbar>
  </div>
</template>

<script>
import Hello from './components/HelloWorld'
import RyanairNavbar from './components/NavBar'
import { Nav, Navbar} from 'bootstrap-vue/es/components'

export default {
  name: 'app',
  components: {
    Hello,
    RyanairNavbar
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
