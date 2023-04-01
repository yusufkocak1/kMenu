import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  server: {
    port: 8081, // uygulamanın çalışacağı port
    proxy: {
      '/api': {
        target: 'http://localhost:8088', // API isteklerinin yönlendirileceği port
        changeOrigin: true, // CORS hatası alınmaması için gereklidir
      },
    },
  },
  plugins: [vue()],
})
