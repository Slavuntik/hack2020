<template>
    <div id="authorization">
        <div class="form-group">
            <label for="exampleInputEmail1">Логин (email)</label>
            <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp">
        </div>
        <div class="form-group">
            <label for="exampleInputPassword1">Пароль</label>
            <input type="password" class="form-control" id="exampleInputPassword1">
        </div>
        <button  @click="LogIn" class="btn btn-primary">Войти</button>
    </div>
</template>

<script>
export default {
  name: 'Authorization',
    methods:{
        LogIn(){
            document.getElementById("auth-modal").querySelector(".close").click();
           
            localStorage.setItem("user_token","test_token");
            this.$emit('changeToken',"test_token"); 
            
        },
    }
}
</script>

<style scoped>

</style>
