<template>
  <div>
    <v-toolbar color="grey lighten-4">
      <v-toolbar-title>Restoran Planı Çizimi</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn @click="savePlan">Kaydet</v-btn>
    </v-toolbar>
    <v-container fluid>
      <v-row>
        <v-col cols="12" md="8" class="mb-3">
          <v-card class="elevation-0">
            <v-card-title>
              Lütfen restoran planını burada çizin
            </v-card-title>
            <v-card-text>
              <div ref="container" class="restaurant-plan-container"></div>
            </v-card-text>
          </v-card>
        </v-col>
        <v-col cols="12" md="4">
          <v-card class="elevation-0">
            <v-card-title>
              Öğeler
            </v-card-title>
            <v-card-text>
              <v-btn color="primary" @click="addTable">Masa Ekle</v-btn>
              <v-btn color="primary" @click="addDoor">Kapı Ekle</v-btn>
              <v-btn color="primary" @click="addWindow">Pencere Ekle</v-btn>
              <v-btn color="primary" @click="addCashier">Kasa Masası Ekle</v-btn>
              <v-btn color="primary" @click="addStairs">Merdiven Ekle</v-btn>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import Vue from 'vue';
import Konva from 'konva';
import VueKonva from 'vue-konva';

Vue.use(VueKonva);

export default {
  data() {
    return {
      shapes: [],
    };
  },
  mounted() {
    const container = this.$refs.container;
    const stage = new Konva.Stage({
      container,
      width: container.clientWidth,
      height: container.clientHeight,
    });
    const layer = new Konva.Layer();
    stage.add(layer);
    this.layer = layer;
  },
  methods: {
    addTable() {
      // Masa ekleme kodu buraya gelecek
    },
    addDoor() {
      // Kapı ekleme kodu buraya gelecek
    },
    addWindow() {
      // Pencere ekleme kodu buraya gelecek
    },
    addCashier() {
      // Kasa masası ekleme kodu buraya gelecek
    },
    addStairs() {
// Merdiven e
