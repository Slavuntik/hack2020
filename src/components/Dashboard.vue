<template>
  <div id="albumAdmin" class="mt-5">
    <div class="container">
        <div class="row w-100 justify-content-end mb-4">
            <a class="btn btn-outline-primary mr-3" @click="$router.push('/dashboard/0');"><i class="fas fa-plus"></i>Добавить</a>
            <a class="btn btn-outline-primary mr-3" v-on:click="download">Экспорт в Exel</a>
            <b-button v-b-toggle.collapseSet variant="outline-primary" class="mr-3"><i class="fas fa-cog"></i> Настройки отображения</b-button>
            <b-button v-b-toggle.collapseFilter variant="outline-primary"><i class="fas fa-filter"></i> Фильтр</b-button>
        </div>
        <b-collapse id="collapseSet" class="my-2">
            <div class="row">
            <div class="col-12 col-md-4" v-for="(field, ind) in $root.fieldsArray" :key="ind" >
            <div class="form-check" v-if='field'>
                <input class="form-check-input" type="checkbox" :checked="$root.setToShow.includes(field.label)"  v-on:change="showField($event, field.label,field.key )"> 
                <label class="form-check-label" for="defaultCheck1">{{field.label}}</label>
            </div>
            </div>
            </div>
        </b-collapse>
        <b-collapse id="collapseFilter" class="my-2">
            <div class="row"> 
                <div class="col-12 col-md-6" v-for="(field, fild_ind) in pets[0]" :key="fild_ind" >
                    <div class="form-group" v-if="fild_ind!=999" >
                        <label >{{field[1]}}</label>
                        <select v-if="$root.dictionarisList.includes(field[1])" @change="filterPets(fild_ind, $event)" class="custom-select mr-sm-2 filter" >
                            <option selected>Не выбрано...</option>
                            <option v-for="(div_val, dic_ind) in dictionary[field[1].replace(' ', '_')]" :key="dic_ind"  :value="div_val">{{div_val}}</option>

                        </select>
                        <input v-else type="text" class="form-control filter" @input="filterPets(fild_ind, $event)" :data-id="fild_ind">
                    </div>
                </div>
            </div>
        </b-collapse>




        <b-table id="my-table" striped hover 
            head-variant="dark"
            :items="petsFilter" 
            :fields="fields" 
            :current-page="currentPage" 
            :per-page="perPage"
             selectable
            select-mode="single"
            @row-selected="onRowSelected"
            sticky-header="true"
           >
             <template #cell()="data">
                {{ data.value[0] }}
            </template>
        </b-table>

        <b-pagination
            v-model="currentPage"
            :sticky-header="true"
            :total-rows="rows"
            :per-page="perPage"
            aria-controls="my-table"
            @change="handlePageChange"
        ></b-pagination>

        <!--<table class="table table-hover table-sm table-striped table-responsive">
            <thead class="thead-dark">
                <tr>
                <th scope="col"  v-for="(field, fild_ind) in $root.animals[0].pet" :key="fild_ind" :data-id="field.cellIndex" >{{field.cellHeader}}</th>
                </tr>
            </thead>
            <tbody>
                <tr  v-for="(pet, index) in $root.animals" :key="index" @click="$router.push('/dashboard/'+items[0].id)">
                  <td v-for="(field, fild_ind) in pet.pet" :key="fild_ind" :data-id="field.cellIndex" >{{field.cellValue}}</td>
                
                </tr>
            </tbody>
        </table>-->
        
    </div>


    
  </div>
</template>


<script>
import connect from '../connect';
import XLSX from 'xlsx';
export default {
    name: 'Dashboard',
     props: ['userToken' , 'dictionary', 'filterSh','filterOrg'],
    components: {
       
    },
    data(){
        return {
            filter: null,
        filterOn: [],
            colSpan:2,
            perPage:100,
            currentPage: 1,
            fields: [{key: "0", label:"КАРТОЧКА УЧЕТА ЖИВОТНОГО №"},{key: "1", label:"ВИД"},{key: "5", label:"ПОЛ"},{key: "4", label:"КЛИЧКА"}],
            test: null,
            rows: 0,
            petsOriginal: [],
            petsFilter: [],
            pets:[]
           
        }
    },
      watch:{
        filterSh:function (val) {
            if(val!=-1){
            let results=this.pets.filter(
               notification => {
                return   notification[25][0].toLocaleLowerCase()==val.toLocaleLowerCase();
             
            })
            this.petsFilter = results;

            }
        },
         filterOrg:function (val) {
            if(val!=-1){
            let results=this.pets.filter(
               notification => {
                return   notification[26][0].toLocaleLowerCase()==val.toLocaleLowerCase();
             
            })
            this.petsFilter = results;
           
            }
        },
    },
    methods:{
        download : function() {
        var toDownload=[];
        this.petsFilter.forEach((pet) => { 
            let ar={};
            for (var key of Object.keys(pet)) {
                if(key!=999){
                
                ar[pet[key][1]]=pet[key][0];  
              
                }
            }
            toDownload.push(ar);
        });
       
       

        const data = XLSX.utils.json_to_sheet(toDownload)
        const wb = XLSX.utils.book_new()
        XLSX.utils.book_append_sheet(wb, data, 'data')
        XLSX.writeFile(wb,'demo.xlsx')
      },
        async fetchData(){
      
      return this.json_data;
    },
       filterPets(ind, event){
           
           var searchText =event.target.value.toLocaleLowerCase(); 
           if(searchText!=-1){
           let results=this.pets.filter(
               notification => {
                return   notification[ind][0].toLocaleLowerCase().includes(searchText);
             
            })
            this.petsFilter = results;
          }

         },
        showField(event, field, key){
            if(event.target.checked){
                this.fields.push( { key: String(key), label: field },);
            }
            else {
                for (var i = 0; i < this.fields.length; i++) {
                    if(this.fields[i].key==key)
                        this.fields.splice(i, 1);
                }
                 this.colSpan--;
            }
            
        },
        onRowSelected(items) {
            this.$root.curPet = items[0];
           this.$router.push('/dashboard/'+items[0][0][2]);
        },
        handlePageChange(){
          /*  if(this.arLength<value*this.perPage) 
            {
                //ajax
                alert();
                this.arLength=this.arLength+this.perPage;
            }*/
        },
     
    },
    created(){
         connect.getPets().then((t)=> { 
            this.pets = t; this.arLength  = this.pets.length;
            this.rows = this.pets.length;
            this.petsFilter = this.pets.slice();
            console.log( this.pets)
            })
            


    }
}


</script>
<style scoped>
.card-img-block{ max-height: 250px; overflow: hidden;}
table th, table td{
    white-space: nowrap;
}
</style>