<template>
  <div id="mapContainer">
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
          :min-opacity=".75"
          :max-zoom="10"
          :blur="5"
        ></HeatmapLayer>
      </LMap>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import L, { LatLng } from "leaflet";
import { latLng } from "leaflet";
import { LMap, LTileLayer } from "vue2-leaflet";
import HeatmapLayer from "./HeatmapLayer";

import { fetchGpx } from "@/utils/backend";
import { Position } from "@/model/GpxTypes";

@Component({
  components: {
    LMap,
    LTileLayer,
    HeatmapLayer
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

  addHeatMapPoint(data: any) {
    data.forEach((position: Position) => {
      this.latLngArray.push(latLng(position.latitude, position.longitude));
    });
  }
}
</script>

<style scoped>
#mapContainer {
  height: 1000px;
  width: -moz-available;
}
#map {
  height: 1000px;
  width: 100%;
}
</style>
