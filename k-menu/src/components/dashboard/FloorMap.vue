<template>
    <ToolsPanel></ToolsPanel>
    <div class="grid grid-cols-100 last:border-b last:border-r">
        <div
                v-for="(cell, index) in cells"
                :key="index"
                class="border-t border-l p-1 "
                :class="{ 'bg-rust-200': cell, 'bg-white': !cell }"
                @mousedown="startDrawing(index)"
                @mouseover="drawing && draw(index)"
                @mouseup="stopDrawing"
                @click="drawLine(index)"
                @dblclick="removeSelection(index)"

        ></div>
    </div>
</template>

<script>
import ToolsPanel from "./common/ToolsPanel.vue";

export default {
    components: {ToolsPanel},
    data() {
        return {
            cells: Array(10000).fill(false),
            drawing: false,
            start: "",
            end: "",
        };
    },
    methods: {
        startDrawing(index) {
            if (!this.cells[index]) {
                this.drawing = true;
                this.cells[index] = true;
            }
        },

        draw(index) {
            if (!this.cells[index]) {
                this.cells[index] = true;
            }
        },

        stopDrawing() {
            this.drawing = false;
        },
        drawLine(index){
            if(this.start===""){
                this.start=index;
            }else{
                if(this.start===index){
                    this.cells[index]=!this.cells[index];
                }
                this.end = index
                this.markCells(this.start,this.end)

                this.start="";
                this.end ="";
            }
        },
        markCells(startIndex, endIndex) {
            const startX = startIndex % 100;
            const startY = Math.floor(startIndex / 100);
            const endX = endIndex % 100;
            const endY = Math.floor(endIndex / 100);

            const dx = endX - startX;
            const dy = endY - startY;
            const xDir = dx > 0 ? 1 : -1;
            const yDir = dy > 0 ? 1 : -1;
            const absDx = Math.abs(dx);
            const absDy = Math.abs(dy);

            let x = startX;
            let y = startY;
            let error = 0;

            if (absDx > absDy) {
                for (let i = 0; i <= absDx; i++) {
                    const index = y * 100 + x;
                    this.cells[index] = true;
                    error += absDy;
                    if (error > absDx) {
                        y += yDir;
                        error -= absDx;
                    }
                    x += xDir;
                }
            } else {
                for (let i = 0; i <= absDy; i++) {
                    const index = y * 100 + x;
                    this.cells[index] = true;
                    error += absDx;
                    if (error > absDy) {
                        x += xDir;
                        error -= absDy;
                    }
                    y += yDir;
                }
            }
        },
        removeSelection(index) {
            if (this.cells[index]) {
                this.start="";
                this.end="";
                this.cells[index] = false;
            }
        },
    },
};
</script>

<style>
.grid-cols-100 {
    grid-template-columns: repeat(100, minmax(0, 1fr));
}
.bg-white {
    background-color: #fff;
}
</style>
