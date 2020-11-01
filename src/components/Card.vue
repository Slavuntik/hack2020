<template>
  <div id="card" class="mt-5" >
    <div class="container">
        <div class="row">
            <div class="col-12 col-md-7">
                <b-carousel
                    id="gallery"
                    :interval="4000"
                    controls
                    indicators
                    background="#ababab"
                    img-width="1024"
                    img-height="480"
                    style="text-shadow: 1px 1px 2px #333;">
                    <b-carousel-slide v-for="(value, index) in petInfo.gallery" v-bind:key="index"
                        caption="First slide"
                        text="Nulla vitae elit libero, a pharetra augue mollis interdum."
                        :img-src="value"
                    ></b-carousel-slide>
                </b-carousel>
            </div>
            <div class="col-12 col-md-5">
                 <h2>{{petInfo.title}}</h2>
                 <div>{{petInfo.age}}, {{petInfo.bride}}, {{petInfo.sex}}</div>
                 <div class="my-2 row">
                    <div class="col-12 col-md-6"><button type="button" class="btn btn-secondary">Стать куратором</button></div>
                    <div class="col-12 col-md-6"><button type="button" class="btn btn-success">Забрать домой</button></div>
                 </div>
                 <div class="card my-2 w-100">
                    <div class="card-body">
                        <h5 class="card-title">Быстрое пополнение копилки «Срочная помощь»</h5>
                        <p class="card-text">Питомцы с отметкой «Срочная помощь» нуждаются в твоей поддержке. Выбери сумму, чтобы пополнить копилку для их нужд.</p>
                        <b-form-group >
                            <b-form-radio-group
                                id="btn-radios-1"
                                v-model="donateType"
                                :options="donateTypeOpt"
                                buttons
                                button-variant="outline-primary"
                                size="sm"
                                name="radio-btn-outline"
                            ></b-form-radio-group>
                        </b-form-group>
                        <div class="form-row donate-sum">
                            <div class="col text-center" @click="donateSum=2000" :class="donateSum==2000?' bg-primary':''"><span>2000р.</span></div>
                            <div class="col text-center"  @click="donateSum=750" :class="donateSum==750?' bg-primary':''"><span>750р.</span></div>
                            <div class="col text-center"  @click="donateSum=500" :class="donateSum==500?' bg-primary':''"><span>500р.</span></div>
                            <div class="col-4 text-center"><input v-model="donateSum" type="text" class="form-control" placeholder="Сумма"></div>
                        </div>

                        <a href="#" class="btn btn-primary w-100 mt-2">ПОДПИСАТЬСЯ</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="jumbotron animal-actions my-4">
        <div class="container">
            <div class="row">
                <div class="col-12 col-md-8">
                    <div class="row">
                        <div class="col-12 col-md-4 img-block" v-b-modal.feedModal>
                            <div class="img-cont"><img src="../assets/feed.svg"/></div>
                            <a  class="text-white">Кормить</a>
                            <div class="text-white">от 160 р.</div>
                        </div>
                       <div class="col-12 col-md-4 img-block"  v-b-modal.advertiseModal>
                            <div class="img-cont"><img src="../assets/advertise.svg"/></div>
                            <a data-toggle="modal" data-target="#advertiseModal" class="text-white">Рекламировать</a>
                            <div class="text-white">от 90 р.</div>
                        </div>
                        <div class="col-12 col-md-4 img-block" v-b-modal.visitModal>
                            <div class="img-cont"><img src="../assets/visit.svg"/></div>
                            <a  class="text-white">Навестить <br>и погулять</a>
                        </div>
                    </div>
                </div>
                <div class="col-12 col-md-4 get-home-block">
                    <div class="get-home" v-b-modal.getPetModal><div class="text-white text-center mt-3">Забрать домой</div></div>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <div>
            <b-tabs content-class="mt-3">
                <b-tab title="О питомце" active>
                    <div>
                       <div  v-for="(info, index) in petInfo.main_info" v-bind:key="index">
                            <b>{{ info.name }}</b>: {{ info.text }} 
                        </div>
                        <div><b>Комментарий</b>: {{petInfo.comment}}</div>
                        <div><b>Локация</b>: {{petInfo.location}}</div>
                    </div>
                </b-tab>
                <b-tab title="Мед карта">
                    <div class="med-card">
                        <div class="note">
                            <div class="text-muted">01.10.2020</div>
                            <div><b>Клинический анализ крови, экспресс анализ на панлейкопинию.</b></div>
                            <div ><span class="text-muted">Сумма:</span> <b>2000р.</b></div>
                        </div>
                        <hr>
                        <div class="note">
                            <div class="text-muted">02.10.2020</div>
                            <div><b>Капельница, лекарства</b></div>
                            <div ><span class="text-muted">Сумма:</span> <b>1965р.</b></div>
                        </div>
                    </div>
                    <hr>
                </b-tab>
            </b-tabs>
        </div>
    </div>

 <b-modal  hide-footer  id="visitModal" title="BootstrapVue">
        <template #modal-title> <h5 class="modal-title" >Навестить и погулять</h5></template>
        <div class="modal-body">
            <div class="form-group">
                <label for="exampleInputPassword1">Имя</label>
                <input type="text" class="form-control" id="exampleInputPassword1">
            </div>
            <div class="form-group">
                <label for="exampleInputEmail1">Email</label>
                <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp">
            </div>
            <button  class="btn btn-primary">Отправить заявку</button>
        </div>
 </b-modal>

 <b-modal  hide-footer  id="getPetModal" title="BootstrapVue">
        <template #modal-title> <h5 class="modal-title" >Приютить питомца</h5></template>
        <div class="">
            <small class="text-muted mb-2">Обзавестись питомцем - это серьезный шаг как для вас, так и для животного. Но это совсем несложно, как может показаться на первый взгляд. Команда Teddy Food всячески поддержит вас в этом благородном деле.
Ветеринары проверят животное и дадут рекомендации.
Подскажем как и чем кормить, ухаживать, лечить, как гулять и чем играть.
Оставьте свои контакты и мы поможем сделать первый шаг.
Не бойтесь, мы не кусаемся :)</small>
            <div class="form-group">
                <label for="exampleInputPassword1">Имя</label>
                <input type="text" class="form-control" id="exampleInputPassword1">
            </div>
            <div class="form-group">
                <label for="exampleInputEmail1">Email</label>
                <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp">
            </div>
            <button  class="btn btn-primary">Отправить заявку</button>
        </div>
 </b-modal>
 
 <b-modal  hide-footer size="xl"  id="feedModal" title="BootstrapVue">
        <template #modal-title> <h5 class="modal-title" >Рекламировать</h5></template>
        <div class="modal-body">
            <div class="row">
                <div class="col-12 col-md-6">
                    <div class="card mb-3" style="max-width: 540px;">
                        <div class="row no-gutters">
                            <div class="col-md-4">
                                <img src="../assets/feed.svg" class="card-img" />
                            </div>
                            <div class="col-md-8">
                            <div class="card-body">
                                <b class="card-title">Оплатить день</b>
                                <p class="card-text text-mute">Оплатите день и ваш подопечный будет обеспечен дневной нормой корма и свежего наполнителя.</p>
                                <p class="card-text">Стоимость: 96р.</p>
                                <button class="btn btn-outline-primary"> Помочь </button>
                            </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-12 col-md-6">
                    <div class="card mb-3" style="max-width: 540px;">
                        <div class="row no-gutters">
                            <div class="col-md-4">
                                <img src="../assets/feed.svg" class="card-img" />
                            </div>
                            <div class="col-md-8">
                            <div class="card-body">
                                <b class="card-title">Оплатить неделю</b>
                                <p class="card-text text-muted">Оплатите неделю и ваш подопечный будет обеспечен неделбной нормой корма и свежего наполнителя.</p>
                                <p class="card-text">Стоимость: 396р.</p>
                                <button class="btn btn-outline-primary"> Помочь </button>
                            </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
  </b-modal>

  <b-modal size="xl"   hide-footer id="advertiseModal" title="BootstrapVue">
        <template #modal-title> <h5 class="modal-title" >Рекламировать</h5></template>
        <div class="modal-body">
            <div class="row">
                <div class="col-12 col-md-6">
                    <div class="card mb-3" style="max-width: 540px;">
                        <div class="row no-gutters">
                            <div class="col-md-4">
                                <img src="../assets/advertise.svg" class="card-img" />
                            </div>
                            <div class="col-md-8">
                            <div class="card-body">
                                <b class="card-title">Рекламировать питомца (пакет Econom)</b>
                                <p class="card-text text-muted">Питомец быстрее найдет дом, если показать его большому количеству людей. Оплатите рекламный пакет Econom, и информация о животном будет появляться на тематических площадках в интернете.</p>
                                <p class="card-text">Стоимость: 90р.</p>
                                <button class="btn btn-outline-primary"> Помочь </button>
                            </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-12 col-md-6">
                    <div class="card mb-3" style="max-width: 540px;">
                        <div class="row no-gutters">
                            <div class="col-md-4">
                                <img src="../assets/advertise.svg" class="card-img" />
                            </div>
                            <div class="col-md-8">
                            <div class="card-body">
                                <b class="card-title">Рекламировать питомца (пакет VIP)</b>
                                <p class="card-text text-muted">Питомец быстрее найдет дом, если показать его большому количеству людей. Оплатите рекламный пакет Econom, и информация о животном будет появляться на тематических площадках в интернете.</p>
                                <p class="card-text">Стоимость: 490р.</p>
                                <button class="btn btn-outline-primary"> Помочь </button>
                            </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
  </b-modal>




  </div>
</template>

<script>
export default {
    name: 'Card', 
    data() {
        return {
            donateSum: null,
            donateType: 1,
            donateTypeOpt:[
               { text: 'ЕЖЕМЕСЯЧНЫЙ', value: '1' },
                { text: 'РАЗОВЫЙ', value: '2' },
            ],
            petInfo: [],
            mainProps: {
                center: true,
                fluidGrow: true,
                blank: true,
                blankColor: '#bbb',
                width: 600,
                height: 400,
                class: 'my-5'
            }
        }
    },
    created(){
        this.petInfo = this.$root.animals[0];
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
            outline: none!important;
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
        .get-home-block{
            margin: -3em 0;
            outline: none!important;
            .get-home{
                outline: none!important;
                &:hover{
                    cursor: pointer;
                    transform: scale(1.05);
                }
                background: linear-gradient(120deg, #007bff 0, #044a94 100%);
                flex-shrink: 0;
                overflow: hidden;
                width: 240px;
                height: 240px;
                -webkit-transition: all .5s ease;
                -moz-transition: all .5s ease;
                -o-transition: all .5s ease;
                -ms-transition: all .5s ease;
                transition: all .5s ease;
                position: relative;
                margin: auto auto .35em;
                border: 2px solid #fff;
                -webkit-border-radius: 50%;
                border-radius: 50%;
                -webkit-box-shadow: 0 2px 14px rgba(0,0,0,.4);
                box-shadow: 0 2px 14px rgba(0,0,0,.4);
                &:before{
                    content: '';
                    position: absolute;
                    top: 50%;
                    left: 50%;
                    width: 240px;
                    height: 240px;
                    margin-top: -120px;
                    margin-left: -120px;
                    background-size: 240px 240px;
                    background: url(~@/assets/get_home.svg) no-repeat center center
                }
            }
        }
    }
}
</style>
