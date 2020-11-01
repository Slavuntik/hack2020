<template>
  <div id="app">

    <TopMenu  :userToken="userToken" @changeToken='changeToken'  @changeRules='changeRules' @changeRulesOrg='changeRulesOrg' :dictionary="dictionary" v-if="dictionary"/> 

    <router-view  :filterSh="filterSh" :filterOrg="filterOrg" :userToken="userToken" :dictionary="dictionary" v-if="dictionary"/>
    <footer class="mt-5"></footer>
    
    <b-modal id="auth-modal" hide-footer>
      <template #modal-title> <h5 class="modal-title" >Авторизация</h5></template>
      <div class="modal-body"><Authorization :userToken="userToken"  @changeToken='changeToken' /></div>
    </b-modal>

    
  </div>
</template>

<script>
import TopMenu from './components/TopMenu.vue'
import Authorization from './components/Authorization.vue'
import connect from '../src/connect';
export default {
  name: 'App',
  components: {
    TopMenu,
    Authorization,
    
  },
  
 data(){
    return {
      userToken: "",
      pets: [],
      dictionary: null,
     filterSh: null ,
     filterOrg: null
    }
  },
  methods:{
    changeToken(token){
      this.userToken = token;
    },
    changeRules(item){
      this.filterSh = item; 
  
    } , changeRulesOrg(item){
      this.filterOrg = item; 
  
    }
  },
  created(){
    this.userToken =  localStorage.getItem("user_token");  
   
    connect.getDictionary('АДРЕС ПРИЮТА').then((t)=> { this.dictionary={};this.dictionary["АДРЕС_ПРИЮТА"]=t.data.values })
   //this.dictionary=Х'ПОЛ']=['ЖЕНСКИЙ','МУЖСКОЙ'];
  connect.getDictionary('ПОЛ').then((t)=> { this.dictionary["ПОЛ"]=t.data.values   })
  connect.getDictionary('ВИД').then((t)=> { this.dictionary["ВИД"]=t.data.values   })
  connect.getDictionary('ПОРОДА').then((t)=> { this.dictionary["ПОРОДА"]=t.data.values   })
  connect.getDictionary('ОКРАС').then((t)=> { this.dictionary["ОКРАС"]=t.data.values   })
  connect.getDictionary('ШЕРСТЬ').then((t)=> { this.dictionary["ШЕРСТЬ"]=t.data.values   })
  connect.getDictionary('УШИ').then((t)=> { this.dictionary["УШИ"]=t.data.values   })
  connect.getDictionary('ХВОСТ').then((t)=> { this.dictionary["ХВОСТ"]=t.data.values   })
  connect.getDictionary('ПРИЧИНА ВЫБЫТИЯ').then((t)=> { this.dictionary["ПРИЧИНА_ВЫБЫТИЯ"]=t.data.values   })
  connect.getDictionary('ПРИЧИНА ЭВТАНАЦИИ').then((t)=> { this.dictionary["ПРИЧИНА_ЭВТАНАЦИИ"]=t.data.values   })
  connect.getDictionary('ПРИЧИН СМЕРТИ').then((t)=> { this.dictionary["ПРИЧИН_СМЕРТИ"]=t.data.values   })
  connect.getDictionary('ЭКСПЛУАТИРУЮЩАЯ ОРГАНИЗАЦИЯ').then((t)=> { this.dictionary["ЭКСПЛУАТИЗИРУЮЩАЯ_ОРГАНИЗАЦИЯ"]=t.data.values   })
   console.log(this.dictionary);
    /*var this_item = this;
    this.$root.dictionarisList.forEach((item) => { 
      let ind_name=item;
      connect.getDictionary(ind_name).then((t)=> {
          if(t.status==200) { 
              this_item.dictionary[ind_name] = t.data.values
          
          }
      })
    })*/

  }
}
</script>

<style>

</style>
