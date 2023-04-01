<template>
  <div class="flex flex-col justify-center items-center h-screen">
    <h1  class="text-3xl font-bold mb-6">Çıkış Yapılıyor...</h1>
    <div v-if="isLoading" class="animate-spin rounded-full h-32 w-32 border-t-2 border-b-2 border-primary-900"></div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isLoading: true
    }
  },
  mounted() {
    this.clearLocalStorageAndRedirect()
  },
  methods: {
    clearLocalStorageAndRedirect() {
      localStorage.clear()
      setTimeout(() => {
        this.isLoading = false // Yükleme işlemi tamamlandı
        this.$router.push('/login') // Anasayfaya yönlendir
      }, 2000) // 2 saniye bekle
    }
  }
}
</script>

<style>
/* Stil dosyası için Tailwind CSS kullanabilirsiniz */
</style>
