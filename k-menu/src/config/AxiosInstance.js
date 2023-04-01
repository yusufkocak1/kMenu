import axios from "axios";
import * as WebServiceUrl from "../config/WebServiceURL";
import router from '../router'; // Router'ı import edin

const axiosInstance = axios.create({
  baseURL: WebServiceUrl.baseUrl,
});

axiosInstance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    Promise.reject(error);
  }
);
axiosInstance.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (error.response.status === 401) {
      localStorage.removeItem("token");
      router.push({ name: "Login" });
    }
    return Promise.reject(error);
  }
);

export default axiosInstance;
