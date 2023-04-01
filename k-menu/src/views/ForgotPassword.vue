<template>
  <div class="forgot-password">
    <h2 class="title">Şifrenizi sıfırlayın</h2>
    <p class="subtitle">
      Kayıtlı e-posta adresinizi girerek şifrenizi sıfırlayabilirsiniz.
    </p>
    <v-form v-model="valid" ref="form" lazy-validation>
      <v-text-field
        v-model="email"
        :rules="emailRules"
        :counter="emailMaxLength"
        label="E-posta"
        required
      ></v-text-field>
      <v-btn
        @click="resetPassword"
        :disabled="!valid || loading"
        :loading="loading"
        color="primary"
        block
      >
        Şifremi Sıfırla
      </v-btn>
    </v-form>
    <v-snackbar v-model="snackbar" :timeout="snackbarTimeout" color="error">
      {{ snackbarMessage }}
    </v-snackbar>
  </div>
</template>
<script>
import axios from "axios";

export default {
  name: "ForgotPassword",
  data: () => ({
    valid: false,
    loading: false,
    snackbar: false,
    snackbarMessage: "",
    snackbarTimeout: 3000,
    email: "",
    emailMaxLength: 255,
    emailRules: [
      (v) => !!v || "E-posta alanı gereklidir.",
      (v) => /.+@.+/.test(v) || "Geçerli bir e-posta adresi giriniz.",
    ],
  }),
  methods: {
    resetPassword() {
      if (this.email === "") {
        this.snackbarMessage = "Lütfen e-posta adresinizi girin.";
        this.snackbar = true;
        return;
      }

      this.loading = true;
      this.snackbar = false;

      axios
        .post("/api/v1/users/reset-password", {
          email: this.email,
        })
        .then(() => {
          this.loading = false;
          this.snackbarMessage =
            "Şifre sıfırlama bağlantısı e-posta adresinize gönderildi.";
          this.snackbar = true;
        })
        .catch(() => {
          this.loading = false;
          this.snackbarMessage =
            "Şifrenizi sıfırlama sırasında bir hata oluştu. Lütfen daha sonra tekrar deneyin.";
          this.snackbar = true;
        });
    },
  },
};
</script>
