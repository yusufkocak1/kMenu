                <template>
                  <div class="w-full">
                    <div class="grid grid-cols-7 gap-4 mb-4 w-full  border-b py-2">
                      <div class="col-span-6">
                        <h2 class="text-xl font-bold ">Menü</h2>
                      </div>
                      <div class="col-span-1">
                        <button class="bg-primary-700 hover:bg-white border hover:border-primary-700 hover:text-primary-700 text-white p-2 rounded-md" @click="showAddProductModal = true">
                          Yeni Ürün Ekle
                        </button>
                      </div>
                    </div>
                    <div class="grid grid-cols-1 gap-4 w-full">
                      <div class="grid grid-cols-5">
                      <div class="col-span-1 font-bold">İsim</div>
                      <div class="col-span-1 font-bold">Kategori</div>
                      <div class="col-span-1 font-bold">Fiyat</div>
                      </div>
                        <div v-for="(item, index) in items" :key="index" class="grid grid-cols-5 border border-primary-700 p-3 rounded-xl shadow-lg -my-1">
                      <div :key="index" class="col-span-1">{{ item.name }}</div>
                      <div  :key="index" class="col-span-1">{{ item.category.name }}</div>
                      <div :key="index" class="col-span-1">{{ item.price }} TL</div>
                        <button class="col-span-1 bg-primary-700 hover:bg-white border hover:border-primary-700 hover:text-primary-700 text-white px-2 py-1 rounded-md " @click="editProduct(index)">Düzenle</button>
                        <button class=" col-span-1 bg-sunset-orange-700 hover:bg-white border hover:border-sunset-orange-700 hover:text-sunset-orange-500 text-white px-2 py-1 rounded-md" @click="deleteProduct(index)">Sil</button>
                      </div>
                      </div>


                    <transition name="modal">
                      <div v-if="showAddProductModal" class="fixed inset-0 z-10 flex items-center justify-center">
                        <div class="absolute inset-0 bg-primary-500 opacity-75"></div>
                        <div class="bg-white rounded-lg px-4 py-6 w-1/2 z-50">
                          <h3 class="text-lg font-bold mb-4">Yeni Ürün Ekle</h3>
                          <div>
                            <div class="mb-4">
                              <label class="block text-primary-700 font-bold mb-2" for="name">
                                İsim
                              </label>
                              <input
                                  v-model="newItem.name"
                                  type="text"
                                  id="name"
                                  class="appearance-none border rounded w-full py-2 px-3 text-primary-700 leading-tight focus:outline-none focus:shadow-outline"
                              />
                            </div>
                            <div class="mb-4">
                              <label class="block text-primary-700 font-bold mb-2" for="category">
                                Kategori
                              </label>
                              <select v-model="newItem.category" id="category" class="border rounded w-full py-2 px-3" >
                                <option :value="categories[0]" selected>{{ categories[0].name }}</option>
                                <option v-for="category in categories.slice(1)" :key="category.id" :value="category">{{ category.name }}</option>
                              </select>

                            </div>
                            <div class="mb-4">
                              <label class="block text-primary-700 font-bold mb-2" for="price">
                                Fiyat
                              </label>
                              <input
                                  v-model="newItem.price"
                                  type="number"
                                  id="price"
                                  class="appearance-none border rounded w-full py-2 px-3 text-primary-700 leading-tight focus:outline-none focus:shadow-outline"
                              />
                            </div>
                            <div class="flex justify-end">
                              <button @click="addProduct" class="bg-primary-500 text-white px-4 py-2 rounded-md">Kaydet</button>
                              <button class="bg-primary-400 text-white px-4 py-2 rounded-md ml-2" @click="showAddProductModal = false">
                                İptal
                              </button>
                            </div>
                          </div>
                        </div>
                      </div>
                    </transition>

                    <transition name="modal">
                      <div v-if="showEditModal !== null" class="fixed inset-0 z-10 flex items-center justify-center">
                        <div class="absolute inset-0 bg-primary-500 opacity-75"></div>
                        <div class="bg-white rounded-lg px-4 py-6 w-1/2">
                          <h3 class="text-lg font-bold mb-4">Ürünü Düzenle</h3>
                          <form @submit.prevent="editProduct">
                            <div class="mb-4">
                              <label class="block text-primary-700 font-bold mb-2" for="name">
                                İsim
                              </label>
                              <input
                                  v-model="editedItem.name"
                                  type="text"
                                  id="name"
                                  class="appearance-none border rounded w-full py-2 px-3 text-primary-700 leading-tight focus:outline-none focus:shadow-outline"
                              />
                            </div>
                            <div class="mb-4">
                              <label class="block text-primary-700 font-bold mb-2" for="category">
                                Kategori
                              </label>
                              <select v-model="editedItem.category" id="category" class="border rounded w-full py-2 px-3">
                                <option v-for="category in categories" :key="category" :value="category">{{ category }}</option>
                              </select>
                            </div>
                            <div class="mb-4">
                              <label class="block text-primary-700 font-bold mb-2" for="price">
                                Fiyat
                              </label>
                              <input
                                  v-model="editedItem.price"
                                  type="number"
                                  id="price"
                                  class="appearance-none border rounded w-full py-2 px-3 text-primary-700 leading-tight focus:outline-none focus:shadow-outline"
                              />
                            </div>
                            <div class="flex justify-end">
                              <button type="submit" class="bg-primary-500 text-white px-4 py-2 rounded-md">Kaydet</button>
                              <button class="bg-primary-400 text-white px-4 py-2 rounded-md ml-2" @click="showEditModal = null">
                                İptal
                              </button>
                            </div>
                          </form>
                        </div>
                      </div>
                    </transition>

                  </div>
                </template>

                <script>
                  import axiosInstance from "../../../config/AxiosInstance.js";
                  import * as WebServiceUrl from "../../../config/WebServiceURL";
                  import * as Util from "../../../utils/util.js";


                  export default {
                    name: "ProductSettingsParent",
                    data() {
                      return {
                        items: [],
                        showAddProductModal: false,
                        showEditModal: null,
                        newItem: {
                          name: "",
                          category: {},
                          price: null,
                          active: true,
                        },
                        editedItem: {
                          name: "",
                          category: "",
                          price: null,
                          active: true,
                        },
                      };
                  },props:{
                      categories:[],
                      restaurant:{},
                    },
                  methods: {
                    addProduct() {
                      const formData = new FormData();
                      formData.append("id",Util.generateUUID())
                      formData.append("category",this.newItem.category.id);
                      formData.append("restaurant",this.restaurant.id)
                      formData.append("name",this.newItem.name);
                      formData.append("price",this.newItem.price)
                      formData.append("active",this.newItem.active)
                      axiosInstance.post(WebServiceUrl.addProduct,formData ,{headers: {'Content-Type': 'application/json'}}).then(response=>this.items.push(response.data)).catch(error=>alert(error));
                      this.showAddProductModal = false;
                      this.newItem = { name: "", category: "", price: null, active: true };
                    },
                    deleteProduct(index) {
                      this.items.splice(index, 1);
                    },
                    editProduct() {
                      this.showEditModal = null;
                    },
                    getProduct(){
                      axiosInstance.get(WebServiceUrl.getProduct+this.restaurant.id).then(response=>this.items=response.data).catch(error=>alert(error))
                    }
                  },
                    created() {
                      this.getProduct();
                    }
                  };
                </script>

                <style>
                  .modal-enter-active,
                  .modal-leave-active {
                    transition: opacity 0.2s ease-in-out;
                  }

                  .modal-enter-from,
                  .modal-leave-to {
                    opacity: 0;
                  }
                </style>