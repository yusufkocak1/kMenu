import { createRouter, createWebHistory } from 'vue-router'
import Home from "../views/Home.vue";
import Login from "../views/Login.vue";
import Register from "../views/Register.vue";
import ForgotPassword from "../views/ForgotPassword.vue";
import ResetPassword from "../views/ResetPassword.vue";
import DashBoard from "../views/DashBoard.vue";
import Exit from "../views/Exit.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes : [
    {
      path: "/:restaurantId/:tableId",
      name: "Home",
      component: Home,
    },
    {
      path: "/login",
      name: "Login",
      component: Login,
    },
    {
      path: "/register",
      name: "Register",
      component: Register,
    },
    {
      path: "/forgot-password",
      name: "ForgotPassword",
      component: ForgotPassword,
    },
    {
      path: "/reset-password/:token",
      name: "ResetPassword",
      component: ResetPassword,
    },
    {
      path: "/dashboard",
      name: "DashBoard",
      component: DashBoard,
    },
    {
      path:"/exit",
      name:"Exit",
      component: Exit,
    }
  ]
})
// Giriş yapılmamışsa login sayfasına yönlendir
router.beforeEach((to, from, next) => {
  const publicPages = [
    "/",
    "/login",
    "/register",
    "/forgot-password",
    "/reset-password",
    `/${to.params.restaurantId}/${to.params.tableId}`

  ];
  const authRequired = !publicPages.includes(to.path);
  const loggedIn = localStorage.getItem("token");

  if (authRequired && !loggedIn) {
    return next("/login");
  }

  next();
});
export default router
