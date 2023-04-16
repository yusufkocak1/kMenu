<template>
<div class="m-1 mt-3">
  <div class="m-1">
      <ul>
          <li v-for="table in tables" class="grid grid-cols-6" :key="table.id">
              <span class="col-span-4 uppercase text-primary-700 font-bold flex-col flex justify-center">{{table.name}}</span>
              <div>
              <button
                  :key="'edit' + table.id"
                  class="rounded border-2 p-2 mx-0 border-primary-700 w-full hover:bg-primary-700 hover:text-white text-primary-700"
                  @click="editTable(table)">Düzenle
              </button>
          </div>
              <div>
                  <button
                      :key="'delete' + table.id"
                      class="rounded border-2 p-2 ml-1 border-sunset-orange-700 w-full text-sunset-orange-700 hover:bg-sunset-orange-700 hover:text-white"
                      @click="deleteTable(table.id)">Sil
                  </button>
              </div></li>
      </ul>
  </div>
</div>
</template>
<script>

import toast from "../../mixins/toast.js";

export default {
    data() {
        return {

        }
    },
    props:{
        selectedSection:{},
        tables:[],

    },
    methods:{
        editTable(table){
        toast.success("edit")
        },
        deleteTable(table){
            toast.info(table.name)
        }

    },
    computed:{

    },
    components: {

    }
}
</script>

