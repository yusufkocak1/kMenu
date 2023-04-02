const apiUrl = "http://localhost:8081/api";
const version = "/v1";
const publicUrl = "/public";
export const baseUrl = apiUrl + version;
export const login = "/auth/public/login";
export const register = baseUrl + "/auth/users/public/register"

//restaurant
const restaurantService = baseUrl +"/restaurants"
export const getRestaurantByUserId = restaurantService + "/user";
export const createRestaurant = restaurantService + "/addRestaurant"
export const  getRestaurant = restaurantService + publicUrl + "/";
export const getBGImageUrl = getRestaurant + "image/"
//category
export const getCategory = restaurantService + "/getCategory/"
export const addCategory = restaurantService + "/addCategory"
export const editCategory = restaurantService + "/editCategory/"
export const deleteCategory = restaurantService + "/deleteCategory/"
export const addProduct = restaurantService +"/addProduct"
export const getProduct = restaurantService + publicUrl +"/getProducts/"
export const editProduct = restaurantService + "editProduct"
