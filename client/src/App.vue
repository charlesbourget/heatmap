<template>
  <div id="app">
    <Heatmap />
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import Heatmap from "./components/Heatmap.vue";

@Component({
  components: {
    Heatmap
  }
})
export default class App extends Vue {}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}
</style>
