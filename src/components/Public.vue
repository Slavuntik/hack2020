<template>
<div id="home">
    <div class="jumbotron">
        <div class="container">
        <h3 class="display-4">ВОЗЬМИ ЛЮБИМЦА ИЗ ПРИЮТА</h3>
        <h2 class="display-3">СПАСИ ЖИЗНЬ</h2>
        <p>Оказываем всестороннюю поддержку и помощь людям и животным, нуждающимся в них.</p>
        <p><a class="btn btn-primary btn-lg" href="#" role="button">Подробнее &raquo;</a></p>
        </div>
    </div>
    <h1 class="text-center mt-4 mb-3">ОНИ ЖДУТ ТЕБЯ</h1>
    <Album />
</div>
</template>


<script>
import Album from '@/components/Album.vue'
export default {
  name: 'Home',
  components: {
      Album
  }
}


</script>
<style scoped>
.jumbotron{
    background-image: url(https://hvostik-omsk.ru/wp-content/themes/tails/assets/images/main_top.png);
    background-position: center -345px;
    background-color: rgb(51, 51, 51);
    color: #fff;
}
</style>