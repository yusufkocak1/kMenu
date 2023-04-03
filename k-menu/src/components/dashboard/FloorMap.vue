<template>
  <div
    ref="canvasWrapper"
    @mousemove="draw"
    @mousedown="startDrawing"
    @mouseup="stopDrawing"
    @mouseleave="stopDrawing"
    class="canvas-wrapper"
  >
    <canvas ref="canvas" class="canvas"></canvas>
  </div>
</template>

<script>
export default {
  name: "RestaurantMap",
  data() {
    return {
      isDrawing: false,
      points: [],
    };
  },
  mounted() {
    this.canvas = this.$refs.canvas;
    this.canvasWrapper = this.$refs.canvasWrapper;
    this.context = this.canvas.getContext("2d");

    this.canvas.width = this.canvasWrapper.offsetWidth;
    this.canvas.height = this.canvasWrapper.offsetHeight;

    this.context.lineWidth = 5;
    this.context.lineJoin = "round";
    this.context.lineCap = "round";
    this.context.strokeStyle = "#000000";
  },
  methods: {
    startDrawing(event) {
      this.isDrawing = true;
      const x = event.clientX - this.canvas.offsetLeft;
      const y = event.clientY - this.canvas.offsetTop;
      this.points.push({ x, y });
    },
    draw(event) {
      if (!this.isDrawing) return;
      const x = event.clientX - this.canvas.offsetLeft;
      const y = event.clientY - this.canvas.offsetTop;
      this.points.push({ x, y });

      this.context.beginPath();
      this.context.moveTo(
        this.points[this.points.length - 2].x,
        this.points[this.points.length - 2].y
      );
      this.context.lineTo(
        this.points[this.points.length - 1].x,
        this.points[this.points.length - 1].y
      );
      this.context.stroke();
      this.context.closePath();
    },
    stopDrawing() {
      this.isDrawing = false;
      this.points = [];
    },
  },
};
</script>

<style scoped>
.canvas-wrapper {
  width: 100%;
  height: 100%;
}

.canvas {
  width: 100%;
  height: 100%;
  cursor: crosshair;
}
</style>
