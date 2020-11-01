<template>
  <div class="container mt-5" id="album" >
    <div class="row row-cols-1 row-cols-md-3">
        <div class="col mb-4"  v-for="(pet, index) in $root.animals" :key="index" >
          <div class="card " >
              <img :src="pet.preview" class="card-img-top">
              <div class="card-body">
                  <h5 class="card-title">{{pet.title}}</h5>
                  <p class="card-text" >{{ pet.age }} </p>
                  <p class="card-text" >{{ $root.sex[pet.sex] }} </p>
                  <p class="card-text" >{{ pet.bride }} </p>
                   <a  class="btn btn-primary stretched-link" @click="$router.push('/pet/'+index)">Подробнее</a>
              </div>
          </div>
        </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Album',

}
</script>

<style scoped>
.card{
    
}
</style>
