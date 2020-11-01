<template>
  <div id="top-menu">
   
  <div v-show="false" class="bg-white border-bottom shadow-sm">
    <b-navbar toggleable="lg" type="light" variant="light">
        <div class="container">
        <b-navbar-brand href="/"><img alt="Vue logo" class="logo" src="../assets/logo.svg"></b-navbar-brand>
        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
        <b-collapse id="nav-collapse" is-nav>
        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">
            <b-nav-item  @click="$router.push('/application')">Заявление о жестоком обращениии с животными</b-nav-item>
            <b-nav-item href="#">Новости</b-nav-item>
            <b-nav-item  v-if="!userToken"  @click="$bvModal.show('auth-modal')">Войти</b-nav-item>
            <b-nav-item  v-if="userToken"  @click="LogOut">Выйти</b-nav-item>
        </b-navbar-nav>
        </b-collapse>
        </div>
    </b-navbar>
  </div>
  <div  class="bg-white border-bottom shadow-sm">
    <b-navbar toggleable="lg" type="light" variant="light">
        <div class="container">
          <b-navbar-brand href="/"><img alt="Vue logo" class="logo" src="../assets/logo.svg"></b-navbar-brand>
        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
        <b-collapse id="nav-collapse" is-nav>
        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">
            <b-nav-item  @click="$router.push('/public')">Public</b-nav-item>
            <b-nav-item  @click="$router.push('/dashboard')">Животные</b-nav-item>
            <b-nav-item  @click="$router.push('/report')">Отчеты</b-nav-item>
            <b-nav-item  @click="$router.push('/dictionary')">Справочники</b-nav-item>
            <b-nav-item  @click="$router.push('/documents')">Документы</b-nav-item>
            <b-nav-item  @click="$router.push('/settings')">Настройка прав пользователя</b-nav-item>
           

        </b-navbar-nav>
        </b-collapse>
        </div>
    </b-navbar>
  </div>
   <div  class="bg-white border-bottom shadow-sm">
     <b-navbar toggleable="lg" type="light" variant="light">
        <div class="container">
  
        <b-collapse id="nav-collapse" is-nav>
        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">
           
            <b-nav-item  >
              <select class="form-control form-control-sm" v-model="filterSh">
                <option value="-1">Выбрать приют</option>
                <option  v-for="(value, index) in dictionary['АДРЕС_ПРИЮТА']" v-bind:key="index">{{value}}</option>
              </select>
            </b-nav-item>
             <b-nav-item  >
              <select class="form-control form-control-sm" v-model="filterOrg">
                 <option value="-1">Выбрать организацию</option>
                <option  v-for="(value, index) in dictAr" v-bind:key="index">{{value}}</option>
              </select>
            </b-nav-item>

        </b-navbar-nav>
        </b-collapse>
        </div>
    </b-navbar>
  </div>


  </div>
</template>

<script>
import connect from '../connect';

export default {
  name: 'TopMenu',
  props: ['userToken', 'dictionary'],
   data(){
        return {
          filterSh: -1,filterOrg: -1,
          dictAr:[] ,
        }
   },
   
  
   watch: {
     dictionary(val, oldval) { this.dictAr = val; console.log('Prop changed: ', val, ' | was: ', oldval) },
    filterSh(val){
      this.$emit('changeRules', val); 
    },
     filterOrg(val){
      this.$emit('changeRulesOrg', val); 
    }
   },
  methods:{
    LogOut(){
      localStorage.removeItem("user_token");
      this.$emit('changeToken',null); 
      //console.log("LogOut "+localStorage.getItem("user_token"));
    },
    changeOrg(){
    },

  },
 

  created(){
    
    connect.getDictionary('ЭКСПЛУАТИРУЮЩАЯ ОРГАНИЗАЦИЯ').then((t)=> {
       this.dictAr=t.data.values  
       console.log(this.dictAr) })

  }

}
</script>

<style scoped>
.logo{
    max-width: 50px;
}
.bg-light {
    background-color: #fff !important;
}
</style>
