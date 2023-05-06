import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  server: {
    port: 8081, // uygulamanın çalışacağı port
    proxy: {
      '/api': {
        target: 'http://localhost:8088', // API isteklerinin yönlendirileceği port
        changeOrigin: true, // CORS hatası alınmaması için gereklidir
      },
      '/websocket': {
        target: 'ws://localhost:8083/websocket',
        changeOrigin: true,
        ws: true,
      },
    },
  },
  plugins: [vue()],
})
