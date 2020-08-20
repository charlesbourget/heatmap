<template>
  <div id="mapContainer">
    <div id="datePicker">
      <Datepicker v-model="fromDate" placeholder="From"> </Datepicker>
      <Datepicker v-model="toDate" placeholder="To"> </Datepicker>
      <button type="button" v-on:click="updateHeatmap">Update Heatmap</button>
    </div>
    <div id="map">
      <LMap
        ref="map"
        :zoom="zoom"
        :center="center"
        :options="mapOptions"
        @update:center="centerUpdate"
        @update:zoom="zoomUpdate"
      >
        <LTileLayer :url="url" :attribution="attribution" />
        <HeatmapLayer
          :lat-lng="latLngArray"
          :radius="5"
          :min-opacity="0.75"
          :max-zoom="10"
          :blur="5"
        ></HeatmapLayer>
      </LMap>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import { LatLng } from "leaflet";
import { latLng } from "leaflet";
import { LMap, LTileLayer } from "vue2-leaflet";
import HeatmapLayer from "./HeatmapLayer";
import Datepicker from "vuejs-datepicker";

import { fetchGpx, fetchGpxBetweenDates } from "@/utils/backend";
import { Position } from "@/model/GpxTypes";

@Component({
  components: {
    LMap,
    LTileLayer,
    HeatmapLayer,
    Datepicker
  }
})
export default class Heatmap extends Vue {
  private zoom = 13;
  private center = latLng(45.50884, -73.58781);
  private mapOptions = {
    zoomSnap: 0.5
  };
  private currentZoom = 11.5;
  private currentCenter = latLng(45.50884, -73.58781);
  private url = "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png";
  private attribution =
    '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors';
  private data = [];
  private latLngArray: Array<LatLng> = [];

  private fromDate = "";
  private toDate = "";

  public centerUpdate(center: LatLng): void {
    this.center = center;
  }

  public zoomUpdate(zoom: number): void {
    this.currentZoom = zoom;
  }

  async beforeCreate() {
    this.data = await fetchGpx();
    this.addHeatMapPoint(this.data);
  }

  async updateHeatmap() {
    this.data = await fetchGpxBetweenDates(this.fromDate, this.toDate);
    this.latLngArray.length = 0;
    this.addHeatMapPoint(this.data);
  }

  addHeatMapPoint(data: Array<Position>) {
    data.forEach((position: Position) => {
      this.latLngArray.push(latLng(position.latitude, position.longitude));
    });
  }
}
</script>

<style scoped>
#mapContainer {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 1000px;
  width: -moz-available;
}
#datePicker {
  flex-basis: 5%;
  display: flex;
  flex-direction: row;
  justify-content: center;
  z-index: 999
}
#map {
  flex-basis: 95%;
  width: 100%;
}
</style>
