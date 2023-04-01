const apiUrl = "http://localhost:8081/api";
const version = "/v1";
export const baseUrl = apiUrl + version;
export const login = "/auth/public/login";


//restaurant
const restaurantService = baseUrl +"/restaurants"
export const getRestaurantByUserId = restaurantService + "/user";
export const createRestaurant = restaurantService + "/addRestaurant"
export const  getRestaurant = restaurantService + "/";
export const getCategory = restaurantService + "/getCategory/"
export const addCategory = restaurantService + "/addCategory"
export const editCategory = restaurantService + "/editCategory/"
export const deleteCategory = restaurantService + "/deleteCategory/"
