<template>
  <div id="documents" class='mt-5'>
      <div class="container" >
          <h1 class="text-center mb-3">Настройка прав пользователей</h1>
        <div class="row mb-4"> <div class="col-12"> <a class="btn btn-outline-primary" href="https://dtl2020.herokuapp.com/sqlexsport" target="_blank">Сформировать SQL схему данных</a></div></div>
          <table class="table">
  <thead class="thead-dark">
    <tr>
      <th scope="col">#</th>
      <th scope="col">Логин</th>
      <th scope="col">Просмотр бд</th>
      <th scope="col">Редактирование бд</th>
      <th scope="col">Доступ к сайту</th>
      <th scope="col">Доступ к моб приложению</th>
      <th scope="col">Роль</th>
    </tr>
  </thead>
  <tbody>
    <tr  v-for="(user, ind) in  users" :key="ind">
      <th scope="row">1</th>
      <td>{{user.login}}</td>
      <td><input type="checkbox" :checked="user.read" /></td>
      <td><input type="checkbox" :checked="user.write" /></td>
      <td><input type="checkbox" :checked="user.webAc" /></td>
      <td><input type="checkbox" :checked="user.MobAc" /></td>
      <td>
           <select  class="custom-select mr-sm-2 filter" >
                                        <option selected>Не выбрано...</option>
                                        <option v-for="(role, ind) in  roles" :key="ind" :selected="(user.role==ind)">{{role}}</option>
                                    </select>
      </td>
    </tr>
  </tbody>
          </table>
  </div>


  </div>


  
</template>

<script>
export default {
  name: 'Documents',
  props: ['userToken'],
   data() {
        return {
            roles:["Приют","Эксплуатирующая организация","Департамент"],
            users:[
                {"login": "admin","read":true,"write":true,"webAc":true,"MobAc":true ,"role": 2},
                {"login": "vika","read":true,"write":false,"webAc":true,"MobAc":false ,"role": 1}
            ]
        }
   },
  methods:{
 
  }

}
</script>

<style scoped>

</style>
