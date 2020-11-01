<template>
  <div id="dictionary" class="mt-5">
    <div class="container">
        <h1 class="text-center mb-3">Справочники</h1> 

        <div class="row">
            <div class="col-12 col-md-12 mb-3" v-for="(dict_info, ind) in dictionaryAr" :key="ind">
               <div style="    border-bottom: 1px dotted;   margin-bottom: 10px; font-weight:bold;"> <b-a v-b-toggle="'collapse-'+ind" class="mb-2" variant="primary">{{ind}}</b-a></div>
                <b-collapse :id="'collapse-'+ind" class="mt-2">
                    <b-card>
                          <input class="form-control mb-1" :value="value"  v-for="(value, index) in dict_info" :key="index"/>
                        <div class="form-inline mb-4">
                            <button class="btn btn-secondary col-2" @click="addRow(ind)"><i class="fas fa-plus"></i> Добавить</button>
                          <!--  <button class="btn btn-secondary col-2" @click="addRow(dictionary[ind].push(document.getElementById(index).value ))"><i class="fas fa-plus"></i> Добавить</button>-->
                            <input type="text" class="form-control col-10" :id="ind" >
                        </div> 
                        <button class="btn btn-primary">Сохранить</button>
                    </b-card>
                </b-collapse>
                 
            </div>
        </div>

    </div>



    
  </div>
</template>


<script>


export default {
    props:['pets','dictionary', 'filterSh'],
    name: 'Dictionaries',

    components: {
       
    },
    data(){
        return {
            dictionaryAr: {}
        }
    },
    watch: {
        dictionary(val, oldval) { console.log(val,oldval) },
    },
    methods: {
       addRow(innd){
           this.dictionaryAr[innd].push(document.getElementById(innd).value)
       }
    },
   created(){
       this.dictionaryAr = this.dictionary
   }
}


</script>
<style scoped>
.card-img-block{ max-height: 250px; overflow: hidden;}
</style>