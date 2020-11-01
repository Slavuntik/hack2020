<template>
  <div id="card" class="mt-5" >
    <div class="container" v-if="!edit">
        <div class="row">
            <div class="col-12 col-md-7">
        
                <b-carousel v-if="photos[0]!=null"
                    id="gallery"
                    :interval="4000"
                    controls
                    indicators
                    background="#ababab"
                    img-width="1024"
                    img-height="480"
                    style="text-shadow: 1px 1px 2px #333;">
                    <b-carousel-slide v-for="(value, index) in photos" v-bind:key="index"
                        caption="First slide"
                        text="Nulla vitae elit libero, a pharetra augue mollis interdum."
                        :img-src="'data:image/gif;base64,'+value"
                    ></b-carousel-slide>
                   <!-- <b-carousel-slide v-if="petInfo[999]"
                        caption="First slide"
                        text="Nulla vitae elit libero, a pharetra augue mollis interdum."
                        :img-src="'data:image/gif;base64,'+petInfo[999][0]"
                    ></b-carousel-slide>-->
                </b-carousel>
            </div>
            <div class="col-12 col-md-5">
                 <div class="d-flex     justify-content-between">
                     <h2 v-if="petInfo[4]">{{petInfo[4][0]}} </h2>
                     <button type="button" class="btn btn-outline-primary" @click="edit=true">
                        <i class="fas fa-edit"></i> Редактировать 
                    </button>
                 </div>
                 <div >{{$root.statuses[petInfo.status]}}</div>
                 <div v-if="petInfo[0]">{{petInfo[0][1]}} {{petInfo[0][0]}}</div>
             
                
             

                 <div class="card my-2 w-100">
                    <div class="card-body">
                        <div ><span class="text-mute">Собранные средства:</span> <b>1000 <i class="fas fa-ruble-sign"></i></b></div>
                        <div ><span class="text-mute">Потраченные средства:</span> <b>249 <i class="fas fa-ruble-sign"></i></b></div>
                        
                    </div>
                </div>
            </div>
        </div>
        <div  class="mt-3">
            <b-tabs content-class="mt-3">
                <b-tab title="Общие  сведения" active>
                    <div>
                        <div v-for="(i, index) in 13"  v-bind:key="index">
                            <div v-if="petInfo[i]"><b>{{ petInfo[i][1] }}</b>: {{ petInfo[i][0] }}</div>
                        </div>
                    </div>
                </b-tab>
                <b-tab title="Доп сведения">
                    <div>
                        <div v-for="(i, index) in [14,15,16,17]"  v-bind:key="index">
                            <div v-if="petInfo[i]"><b>{{ petInfo[i][1] }}</b>: {{ petInfo[i][0] }}</div>
                        </div>
                    </div>
                </b-tab>
                <b-tab title="Отлов">
                    <div>
                        <div v-for="(i, index) in [18,19,20,21,22]"  v-bind:key="index">
                           <div v-if="petInfo[i]"><b>{{ petInfo[i][1] }}</b>: {{ petInfo[i][0] }}</div>
                        </div>
                    </div>
                </b-tab>
               <!-- <b-tab  v-show="false" title="Новые владельцы">
                    <div>
                        <div v-for="(i, index) in []"  v-bind:key="index">
                            <div v-if="petInfo[i]"><b>{{ petInfo[i][1] }}</b>: {{ petInfo[i][0] }}</div>
                        </div>
                    </div>
                </b-tab>-->
                <b-tab title="Движение">
                    <div>
                        <div v-for="(i, index) in [24,23]"  v-bind:key="index">
                            <div v-if="petInfo[i]"><b>{{ petInfo[i][1] }}</b>: {{ petInfo[i][0] }}</div>
                        </div>
                    </div>
                </b-tab>
                 <b-tab title="Ответственные">
                    <div>
                        <div v-for="(i, index) in [28,27,26,25]"  v-bind:key="index">
                            <div v-if="petInfo[i]"> <b>{{ petInfo[i][1] }}</b>: {{ petInfo[i][0] }}</div>
                        </div>
                    </div>
                </b-tab> 
                <b-tab title="Экто- и эндопаразитов">
                    <div>
                        <div v-for="(i, index) in [32,31,30,29]"  v-bind:key="index">
                            <div v-if="petInfo[i]"> <b>{{ petInfo[i][1] }}</b>: {{ petInfo[i][0] }}</div>
                        </div>
                    </div>
                </b-tab>
                <b-tab title="Вакцинации">
                    <div>
                        <div v-for="(i, index) in [35,34,33]"  v-bind:key="index">
                            <div v-if="petInfo[i]"> <b>{{ petInfo[i][1] }}</b>: {{ petInfo[i][0] }}</div>
                        </div>
                    </div>
                </b-tab>
                <b-tab title="Здоровье">
                    <div>
                        <div v-for="(i, index) in [36,37]"  v-bind:key="index">
                           <div v-if="petInfo[i]"> <b>{{ petInfo[i][1] }}</b>: {{ petInfo[i][0] }}</div>
                        </div>
                    </div>
                </b-tab>
            </b-tabs>
        </div>
    </div>

    <div class="container" v-else>
        <div class="row">
            <div class="col-12 col-md-7">
                <div class="card-columns">
                    <div class="card"  v-for="(value, index) in photos" v-bind:key="index">
                        <img :src="'data:image/gif;base64,'+value" class="card-img-top" >
                        <div class="card-img-overlay text-right delete-img">
                            <span @click="deleteImg(index)" class="trash"><i class="fas fa-trash "></i></span>
                        </div>
                    </div>
                    <div class="card text-center">
                        <div class="upload-img">
                            <label><input type="file" class="upload-btn" ref="file" accept="image/*" multiple v-on:change="handleFileUpload()"/></label>
                        <span style="font-size: 3em;"><i class="fas fa-plus"></i></span>
                        </div>
                    </div>
                    
                </div>
            </div>
            <div class="col-12 col-md-5">
                <div class="form-group" v-if="petInfo[4]">
                    <label >{{petInfo[4][1]}}</label>
                    <input type="text" :value="petInfo[4][0]" class="form-control" />
                </div>
                 <div class="form-group" v-if="petInfo[0]">
                    <label v-if="petInfo[0]">{{petInfo[0][1]}}</label>
                    <input type="text" :value="petInfo[0][0]" class="form-control" />
                </div>
                <div class="form-group">
                    <label>Статус</label>
                     <select class="custom-select mr-sm-2" id="" v-model="petInfo.status">
                        <option >Не выбрано...</option>
                        <option  v-for="(value, index) in $root.statuses" :key="index" :value="index" >{{value}}</option>
                    </select>
                </div>

                 <div class="card my-2 w-100">
                    <div class="card-body">
                        <div class="form-group ">
                            <label>Собранные средства:</label>
                            <input type="number" class="form-control" id=""  v-model="petInfo.colSum">
                        </div> 
                        <div class="form-group ">
                            <label>Потраченные средства:</label>
                            <input type="number" class="form-control" id=""  v-model="petInfo.spendSum">
                        </div> 
                    </div>
                </div>
            </div>
        </div>
        <div class="mt-3">
            <b-tabs content-class="mt-3">
                <b-tab title="О питомце" active>
                    <div>
                        <div class="form-group row"  v-for="(info, index) in petInfo.main_info" v-bind:key="index">
                            <label  class="col-sm-2 col-form-label">{{info.name}}</label>
                            <div class="col-sm-10">
                            <input type="text" class="form-control" v-model="petInfo.main_info[index].text" />
                            </div>
                        </div>
                    </div>
                </b-tab>
                
            </b-tabs>

            <b-tabs content-class="mt-3">
                <b-tab title="Общие  сведения" active>
                    <div v-for="(i, index) in 13"  v-bind:key="index"  class="form-group " >
                        
                        <div  v-if="petInfo[i]"  class="row">
                    
                            <label  class="col-sm-2 col-form-label">{{ petInfo[i][1]}}</label>
                            <div class="col-sm-10" >
                                <select v-if="$root.dictionarisList.includes(petInfo[i][1])"  v-model="petInfo[i][0]" class="custom-select mr-sm-2 filter" >
                                    <option selected>Не выбрано...</option>
                                    <option v-for="(div_val, dic_ind) in $root.dictionary[petInfo[i][1]]" :key="dic_ind"  :value="div_val">{{div_val}}</option>
                                </select>
                                <input v-else  type="text" class="form-control" v-model="petInfo[i][0]" />
                            </div>
                        </div>
                    </div>
                </b-tab>
                <b-tab title="Доп сведения">
                        <div v-for="(i, index) in [14,15,16,17]"  v-bind:key="index" class="form-group ">
                            <div  v-if="petInfo[i]"  class="row">
                            <label  class="col-sm-2 col-form-label">{{ petInfo[i][1]}}</label>
                            <div class="col-sm-10">
                                <select v-if="$root.dictionarisList.includes(petInfo[i][1])"  v-model="petInfo[i][0]" class="custom-select mr-sm-2 filter" >
                                    <option selected>Не выбрано...</option>
                                    <option v-for="(div_val, dic_ind) in $root.dictionary[petInfo[i][1]]" :key="dic_ind"  :value="div_val">{{div_val}}</option>
                                </select>
                                <input v-else type="text" class="form-control" v-model="petInfo[i][0]" />
                            </div>
                        </div>
                        </div>
                </b-tab>
                <b-tab title="Отлов">
                   
                        <div v-for="(i, index) in [18,19,20,21,22]"  v-bind:key="index" class="form-group ">
                            <div  v-if="petInfo[i]"  class="row">
                                <label  class="col-sm-2 col-form-label">{{ petInfo[i][1]}}</label>
                                <div class="col-sm-10">
                                    <select v-if="$root.dictionarisList.includes(petInfo[i][1])"  v-model="petInfo[i][0]" class="custom-select mr-sm-2 filter" >
                                        <option selected>Не выбрано...</option>
                                        <option v-for="(div_val, dic_ind) in $root.dictionary[petInfo[i][1]]" :key="dic_ind"  :value="div_val">{{div_val}}</option>
                                    </select>
                                    <input v-else type="text" class="form-control" v-model="petInfo[i][0]" />
                                </div>
                             </div>
                        </div>
                </b-tab>
               <!-- <b-tab v-show="false" title="Новые владельцы">
                    
                        <div v-for="(i, index) in []"  v-bind:key="index" class="form-group ">
                            <div  v-if="petInfo[i]"  class="row">
                                <label  class="col-sm-2 col-form-label">{{ petInfo[i][1]}}</label>
                                <div class="col-sm-10">
                                    <select v-if="$root.dictionarisList.includes(petInfo[i][1])"  v-model="petInfo[i][0]" class="custom-select mr-sm-2 filter" >
                                        <option selected>Не выбрано...</option>
                                        <option v-for="(div_val, dic_ind) in $root.dictionary[petInfo[i][1]]" :key="dic_ind"  :value="div_val">{{div_val}}</option>
                                    </select>
                                    <input v-else type="text" class="form-control" v-model="petInfo[i][0]" />
                                </div>
                            </div>
                        </div>
                </b-tab>-->
                <b-tab title="Движение">
                  
                        <div v-for="(i, index) in [24,23]"  v-bind:key="index" class="form-group ">
                            <div  v-if="petInfo[i]"  class="row">
                                <label  class="col-sm-2 col-form-label">{{ petInfo[i][1]}}</label>
                                <div class="col-sm-10">
                                    <select v-if="$root.dictionarisList.includes(petInfo[i][1])"  v-model="petInfo[i][0]" class="custom-select mr-sm-2 filter" >
                                        <option selected>Не выбрано...</option>
                                        <option v-for="(div_val, dic_ind) in $root.dictionary[petInfo[i][1]]" :key="dic_ind"  :value="div_val">{{div_val}}</option>
                                    </select>
                                    <input v-else type="text" class="form-control" v-model="petInfo[i][0]" />
                                </div>
                            </div>
                        </div>
                </b-tab>
                 <b-tab title="Ответственные">
                    
                        <div v-for="(i, index) in [28,27,26,25]"  v-bind:key="index" class="form-group ">
                            <div  v-if="petInfo[i]"  class="row">
                            <label  class="col-sm-2 col-form-label">{{ petInfo[i][1]}}</label>
                            <div class="col-sm-10">
                                <select v-if="$root.dictionarisList.includes(petInfo[i][1])"  v-model="petInfo[i][0]" class="custom-select mr-sm-2 filter" >
                                    <option selected>Не выбрано...</option>
                                    <option v-for="(div_val, dic_ind) in $root.dictionary[petInfo[i][1]]" :key="dic_ind"  :value="div_val">{{div_val}}</option>
                                </select>
                                <input v-else type="text" class="form-control" v-model="petInfo[i][0]" />
                            </div>
                            </div>
                        </div>
                </b-tab> 
                <b-tab title="Экто- и эндопаразитов">
                   
                        <div v-for="(i, index) in [32,31,30,29]"  v-bind:key="index" class="form-group ">
                            <div  v-if="petInfo[i]"  class="row">
                                <label  class="col-sm-2 col-form-label">{{ petInfo[i][1]}}</label>
                                <div class="col-sm-10">
                                    <select v-if="$root.dictionarisList.includes(petInfo[i][1])"  v-model="petInfo[i][0]" class="custom-select mr-sm-2 filter" >
                                        <option selected>Не выбрано...</option>
                                        <option v-for="(div_val, dic_ind) in $root.dictionary[petInfo[i][1]]" :key="dic_ind"  :value="div_val">{{div_val}}</option>
                                    </select>
                                    <input v-else type="text" class="form-control" v-model="petInfo[i][0]" />
                                </div>
                            </div>

                        </div>
                </b-tab>
                <b-tab title="Вакцинации">
                   
                        <div v-for="(i, index) in [35,34,33]"  v-bind:key="index" class="form-group ">
                             <div  v-if="petInfo[i]"  class="row">
                                <label  class="col-sm-2 col-form-label">{{ petInfo[i][1]}}</label>
                                <div class="col-sm-10">
                                    <select v-if="$root.dictionarisList.includes(petInfo[i][1])"  v-model="petInfo[i][0]" class="custom-select mr-sm-2 filter" >
                                        <option selected>Не выбрано...</option>
                                        <option v-for="(div_val, dic_ind) in $root.dictionary[petInfo[i][1]]" :key="dic_ind"  :value="div_val">{{div_val}}</option>
                                    </select>
                                    <input v-else type="text" class="form-control" v-model="petInfo[i][0]" />
                                </div>
                            </div>
                        </div>
                </b-tab>
                <b-tab title="Здоровье">
                  
                        <div v-for="(i, index) in [36,37]"  v-bind:key="index" class="form-group ">
                            <div  v-if="petInfo[i]"  class="row">
                                <label  class="col-sm-2 col-form-label">{{ petInfo[i][1]}}</label>
                                <div class="col-sm-10">
                                    <select v-if="$root.dictionarisList.includes(petInfo[i][1])"  v-model="petInfo[i][0]" class="custom-select mr-sm-2 filter" >
                                        <option selected>Не выбрано...</option>
                                        <option v-for="(div_val, dic_ind) in $root.dictionary[petInfo[i][1]]" :key="dic_ind"  :value="div_val">{{div_val}}</option>
                                    </select>
                                    <input v-else type="text" class="form-control" v-model="petInfo[i][0]" />
                                </div>
                            </div>
                        </div>
                </b-tab>
            </b-tabs>
        </div>
        <div class="row"> <button @click="edit=false" class="btn btn-primary">Сохранить</button></div>
    </div>



  </div>
</template>

<script>
import connect from '../connect';

export default {
    name: 'Card',
    props: ['userToken','pets' ], 
    data() {
        return {
            edit: false,
           newImgs: [],

            donateSum: null,
            donateType: 1,
            donateTypeOpt:[
               { text: 'ЕЖЕМЕСЯЧНЫЙ', value: '1' },
                { text: 'РАЗОВЫЙ', value: '2' },
            ],
            petInfo: {},
            mainProps: {
                center: true,
                fluidGrow: true,
                blank: true,
                blankColor: '#bbb',
                width: 600,
                height: 400,
                class: 'my-5'
            },
            photos:[]
        }
    },

    methods:{
        deleteImg(ind){
            this.petInfo.gallery.splice(ind, 1);
        },
        handleFileUpload(){
            
            for (var i = 0; i < this.$refs.file.files.length; i++) {
                let reader = new FileReader();
                reader.readAsDataURL(this.$refs.file.files[i]);
                reader.onload = () => {
                    this.petInfo.gallery.push(reader.result);
                };
            }
        }

    },
    created(){
        if(this.$route.params.id==0){
            this.edit=true;
          this.petInfo={};
             this.$root.fieldsArray.forEach((item, i) => { 
                this.petInfo[i] =["",item.label];
            });

        }else{
              this.petInfo = this.$root.curPet; 
              if(Object.keys(this.petInfo).length !== 0) 
               connect.getPhotos(this.petInfo[0][2]).then((t)=> { console.log("fff") ;
                        this.photos = t.data[0].photos; console.log(t.data) 
                })
        }
      /*  this.petInfo.spendSum = 1000;
        this.petInfo.colSum = 1900;
        this.petInfo.docCome = "http://base.garant.ru/73507713/56ae36fc84d31bc2f9c0b07177fa16a9/";
        this.petInfo.docGone = null;
        this.petInfo.medCard = [
            {id: 0, "date":"2020-10-20", "work":"Клинический анализ крови, экспресс анализ на панлейкопинию.","sum": "2000"},
            {id: 1, "date":"2020-10-25", "work":"Капельница, лекарства","sum": "1985"}
        ];*/
        
    }

}
</script>

<style scoped  lang="scss">
#gallery{
    max-height: 420px;
    overflow: hidden;
}
.donate-sum{
    cursor: pointer;
    .bg-primary{
        color: #fff;
        &>div {
            border: 1px solid #007bff;
        }
    }
}
.jumbotron {
    padding: 2rem 2rem;
    &.animal-actions{ 
        background: url(~@/assets/pattern.svg) no-repeat center center , linear-gradient(to right,#007bff 0,#007bff75 100%);
        background-size: cover;

        .img-block{
            &:hover{
                cursor: pointer;
                .img-cont{
                    -webkit-transform: scale(1.05);
                    -moz-transform: scale(1.05);
                    -o-transform: scale(1.05);
                    -ms-transform: scale(1.05);
                    transform: scale(1.05)
                }
                a{
                    text-decoration: underline;
                }
            }
            display: flex;
            flex-direction: column;
            align-items: center;
            .img-cont{
                background: linear-gradient(120deg,#c5ffff 0,#6de7ed 100%);
                box-shadow: 0 2px 14px rgba(0,0,0,.4);
                transition: all .5s ease;
                position: relative;
                width: 75px;
                height: 75px;
                margin: auto auto .35em;
                border: 2px solid #fff;
                -webkit-border-radius: 50%;
                border-radius: 50%;
                -webkit-transition: all .5s ease;
                -moz-transition: all .5s ease;
                -o-transition: all .5s ease;
                -ms-transition: all .5s ease;
                display: flex;
                align-items: center;
                justify-content: center;
                img{
                    max-width: 100%;
                    max-height: 100%;
                }
            }
        }
    }
}
.delete-img .trash{
    cursor: pointer;
}
.upload-img{
    cursor: pointer;
    .upload-btn{
        z-index: 100;
        opacity: 0;
        position: absolute;
        bottom: 0;
        right: 0;
        margin: 0;
        width: 100%;
        height: 100%;
    }
}
</style>
