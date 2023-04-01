<template>
  <v-container fluid class="fill-height">
    <v-row align="center" justify="center">
      <v-col cols="12" sm="8" md="6">
        <v-card class="elevation-12">
          <v-toolbar color="primary" dark>
            <v-toolbar-title>Reset Password</v-toolbar-title>
          </v-toolbar>
          <v-card-text>
            <v-form @submit.prevent="resetPassword">
              <v-text-field
                v-model="password"
                label="New Password"
                type="password"
                required
              ></v-text-field>
              <v-text-field
                v-model="confirmPassword"
                label="Confirm New Password"
                type="password"
                required
              ></v-text-field>
              <v-btn type="submit" color="primary">Submit</v-btn>
            </v-form>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>
<script>
import axios from "axios";

export default {
  name: "ResetPassword",
  data() {
    return {
      password: "",
      confirmPassword: "",
      token: this.$route.query.token,
    };
  },
  methods: {
    async resetPassword() {
      try {
        if (this.password !== this.confirmPassword) {
          this.$store.commit(
            "setMessage",
            "Passwords do not match. Please try again."
          );
          return;
        }
        await axios.post("/api/v1/auth/reset-password", {
          password: this.password,
          token: this.token,
        });
        this.$store.commit(
          "setMessage",
          "Your password has been reset successfully."
        );
        this.$router.push("/login");
      } catch (error) {
        this.$store.commit("setError", error.message);
      }
    },
  },
};
</script>
