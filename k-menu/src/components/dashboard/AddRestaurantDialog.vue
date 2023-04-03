<template>
  <div>
    <div v-if="dialog" class="fixed z-10 inset-0 overflow-y-auto">
      <div class="flex items-end justify-center min-h-screen pt-4 px-4 pb-20 text-center sm:block sm:p-0">
        <div class="fixed inset-0 transition-opacity">
          <div class="absolute inset-0 bg-black-100 opacity-75"></div>
        </div>

        <span class="hidden sm:inline-block sm:align-middle sm:h-screen"></span>&#8203;

        <div class="inline-block align-bottom bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full">
          <div class="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
            <form ref="form">
              <div class="mb-4">
                <label for="name" class="block text-black-700 font-bold mb-2">Restaurant Name:</label>
                <input type="text" v-model="restaurant.restaurantName" id="name" name="name" class="shadow appearance-none border rounded w-full py-2 px-3 text-black-700 leading-tight focus:outline-none focus:shadow-outline" placeholder="Enter name">
              </div>
              <div class="mb-4">
                <label for="contactInfo"   class="block text-black-700 font-bold mb-2">iletişim bilgisi:</label>
                <input type="text" v-model="restaurant.contactInfo" id="contactInfo" name="contactInfo" class="shadow appearance-none border rounded w-full py-2 px-3 text-black-700 leading-tight focus:outline-none focus:shadow-outline" placeholder="Enter contact info">
              </div>
              <div class="mb-4">
                <label for="backgroundImgFile" class="block text-black-700 font-bold mb-2">Background Image:</label>
                <input type="file" id="backgroundImgFile"  @change="onFileChange" name="backgroundImgFile" class="shadow appearance-none border rounded w-full py-2 px-3 text-black-700 leading-tight focus:outline-none focus:shadow-outline" accept="image/*">
              </div>
            </form>
          </div>
          <div class="bg-black-50 px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse">
            <button type="button" class="w-full inline-flex justify-center rounded-md border border-transparent shadow-sm px-4 py-2 bg-primary-500 text-base font-medium text-white hover:bg-primary-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary-500 sm:ml-3" @click="save">Save</button>
            <button type="button" class="mt-3 w-full inline-flex justify-center rounded-md border border-black-300 shadow-sm px-4 py-2 bg-white text-base font-medium text-black-700 hover:text-black-500 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary-500 sm:mt-0 sm:ml-3 sm:w-auto sm:text-sm\" @click="closeModal">Cancel</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    dialog :false,
    userId:{
      String
    }
  },
  data: () => ({
    restaurant: {
      restaurantName: "",
      contactInfo: "",
      adminUserInfo: "",
      backgroundImgFile: "",
    },
    rules: {
      required: (value) => !!value || "Required.",
    },
  }),
  methods: {
    onFileChange(event) {
      this.restaurant.backgroundImgFile = event.target.files[0]
    },
    save() {
      if (this.validate()) {
        this.$emit("save", this.restaurant);
        this.closeModal();
      }
    },
    validate(){
return true
    },
    closeModal() {
      this.$refs.form.reset();
      this.$emit("close");
    },
  },
};
</script>
