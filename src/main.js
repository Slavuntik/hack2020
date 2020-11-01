import Vue from 'vue'
import App from './App'


import VueRouter from "vue-router";
Vue.use(VueRouter);



import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import './scss/custom.scss'
// Install BootstrapVue
Vue.use(BootstrapVue)
// Optionally install the BootstrapVue icon components plugin
Vue.use(IconsPlugin)

Vue.config.productionTip = false

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import( "@/components/Dashboard")
  },
  {
    path: "/pet/:id",
    name: 'Card',
    component: () => import( "@/components/Card")
  },
  {
    path: "/dashboard",
    name: 'Dashboard',
    component: () => import( "@/components/Dashboard")
  },
  {
    path: "/dashboard/:id",
    name: 'CardAdmin',
    component: () => import( "@/components/CardAdmin")
  },
  {
    path: "/application",
    name: 'Application',
    component: () => import( "@/components/Application")
  },
  {
    path: "/report",
    name: 'Report',
    component: () => import( "@/components/Report")
  }, {
    path: "/documents",
    name: 'Documents',
    component: () => import( "@/components/Documents")
  },
  {
    path: "/dictionary",
    name: 'Dictionaries',
    component: () => import( "@/components/Dictionaries")
  }, {
    path: "/settings",
    name: 'RolSettings',
    component: () => import( "@/components/RolSettings")
  },
  {
    path: "/public",
    name: 'Public',
    component: () => import( "@/components/Public")
  },
];

const router = new VueRouter({
  routes,
  mode: "history",
});


new Vue({
  render: h => h(App),
  router,
  data: {
    dictionary: [ ],
    dictionarisList:['ПОЛ','ВИД', 'АДРЕС ПРИЮТА','ПОРОДА','ОКРАС','ШЕРСТЬ','УШИ','ХВОСТ','ПРИЧИНА ВЫБЫТИЯ','ПРИЧИНА ЭВТАНАЦИИ', 'ПРИЧИН СМЕРТИ', 'ЭКПЛУАТИЗИРУЮЩАЯ ОРГАНИЗАЦИЯ'], 
    setToShow:['ВИД','КАРТОЧКА УЧЕТА ЖИВОТНОГО №', 'ПОЛ','КЛИЧКА'],

    statuses:{
      1: "Поступил в приют",
      2: "На лечении",
      3: "На социализации",
      4: "Ищет дом",
    },
    sex:{
      1: "Мальчик",
      2: "Девочка",
    },
    petsOriginal:[],
    animals: [
      {"id": 0, "title":"Мелания", 
        "main_info":{
          "character":{"name":"Характер","text":"не переносит резких звуков, пуглива, тяжело переносит изменения"},
          "lotok":{"name":"Лоток и когтеточка","text": "приучена к лотку и к когтеточке"},
          "health":{"name":"Здоровье","text":"здорова и привита"},
          "virus":{"name":"Скрытые инфекции","text":"нет"},
          "sterel":{"name":"Стерилизация/кастрация","text":"да"},
          "care":{"name":"Уход за шерстью","text":"регулярное расчесывание"},
          "child":{"name":"Отношения с детьми","text":"ладит"},
          "cat":{"name":"Отношения с другими кошками","text":"ладит"},
          "dog":{"name":"Отношения с собаками","text":"не встречалась"},
        },
        "age":"1 год",
        "bride": "без породы",
        "sex": "2",
        "date_come":"2020-20-10 16:20",
        "comment": "Мелания скромна и нежна, прямо как настоящая принцесса, а за порядком она следит прям по-королевски. Все неугодные носочки, платочки и другие текстильные изделия, кои лежат не на своем месте, обязательно будут отданы владельцу. Штрафы за разбросанные вещи Мела принимает исключительно минутами сентиментальности и веселых игр.",
        "location": "Москва, частная домашняя передержка (Косино-Ухтомский р-н)",
        "preview":"http://static1.gophotoweb.com/u6450/7603/photos/5469813/1000-95fa0b31e84217083eb13f40e750ba1d.jpg",
        "gallery":['http://static1.gophotoweb.com/u6450/7603/photos/5469813/2000-4673b6c83e74f25dd760bc0de9d34df0.jpg','http://static1.gophotoweb.com/u6450/7603/photos/5469813/2000-82a26c181be4fff145deec1ffc744618.jpg'],
        "status": 1,
      },
      {"id": 2, "title":"Эмили", "age":"2 года",
        "date_come":"2020-11-10 16:20", 
        "preview":"http://static1.gophotoweb.com/u6450/7603/photos/5134567/1500-189eceb74a8d673ef2d9b99de144ea5e.jpg",
        "status": 2,
        "gallery":[],
      },
     
      {"id": 3, "title":"Майк Майк Майк Майк Майк Майк", "age":"1,5 года", 
        "date_come":"2020-23-10 16:20",
        "preview":"http://static1.gophotoweb.com/u6450/7603/photos/5553089/2000-85ffd850e27f78ff8521e063c82fc007.jpg",
        "status": 3,
        "gallery":[],
      }
    ],

    fieldsArray:[ { "key": "0", "label": "КАРТОЧКА УЧЕТА ЖИВОТНОГО №", "sortable": true }, { "key": "1", "label": "ВИД", "sortable": true }, { "key": "2", "label": "ВОЗРАСТ, ГОД", "sortable": true }, { "key": "3", "label": "ВЕС, КГ", "sortable": true }, { "key": "4", "label": "КЛИЧКА", "sortable": true }, { "key": "5", "label": "ПОЛ", "sortable": true }, { "key": "6", "label": "ПОРОДА", "sortable": true }, { "key": "7", "label": "ОКРАС", "sortable": true }, { "key": "8", "label": "ШЕРСТЬ", "sortable": true }, { "key": "9", "label": "УШИ", "sortable": true }, { "key": "10", "label": "ХВОСТ", "sortable": true }, { "key": "11", "label": "РАЗМЕР", "sortable": true }, { "key": "12", "label": "ОСОБЫЕ ПРИМЕТЫ", "sortable": true }, { "key": "13", "label": "ВОЛЬЕР №", "sortable": true }, { "key": "14", "label": "ИДЕНТИФИКАЦИОННАЯ МЕТКА", "sortable": true }, { "key": "15", "label": "ДАТА СТЕРИЛИЗАЦИИ", "sortable": true }, { "key": "16", "label": "Ф.И.О. ВЕТЕРИНАРНОГО ВРАЧА", "sortable": true }, { "key": "17", "label": "СОЦИАЛИЗИРОВАНО (ДА/НЕТ)", "sortable": true }, { "key": "18", "label": "ЗАКАЗ-НАРЯД / АКТ О ПОСТУПЛЕНИИ ЖИВОТНОГО №", "sortable": true }, { "key": "19", "label": "ЗАКАЗ-НАРЯД ДАТА/ АКТ О ПОСТУПЛЕНИИ ЖИВОТНОГО, ДАТА", "sortable": true }, { "key": "20", "label": "АДМИНИСТРАТИВНЫЙ ОКРУГ", "sortable": true }, { "key": "21", "label": "АКТ ОТЛОВА №", "sortable": true }, { "key": "22", "label": "АДРЕС МЕСТА ОТЛОВА", "sortable": true }, { "key": "23", "label": "ДАТА ПОСТУПЛЕНИЯ В ПРИЮТ", "sortable": true }, { "key": "24", "label": "АКТ №", "sortable": true }, { "key": "25", "label": "АДРЕС ПРИЮТА", "sortable": true }, { "key": "26", "label": "ЭКСПЛУАТИРУЮЩАЯ ОРГАНИЗАЦИЯ", "sortable": true }, { "key": "27", "label": "Ф.И.О. РУКОВОДИТЕЛЯ ПРИЮТА", "sortable": true }, { "key": "28", "label": "Ф.И.О. СОТРУДНИКА ПО УХОДУ ЗА ЖИВОТНЫМ", "sortable": true }, { "key": "29", "label": "№ П/П", "sortable": true }, { "key": "30", "label": "ДАТА", "sortable": true }, { "key": "31", "label": "НАЗВАНИЕ ПРЕПАРАТА", "sortable": true }, { "key": "32", "label": "ДОЗА", "sortable": true }, { "key": "33", "label": "№ П/П", "sortable": true }, { "key": "34", "label": "ДАТА", "sortable": true }, { "key": "35", "label": "ВИД ВАКЦИНЫ", "sortable": true }, { "key": "36", "label": "ДАТА ОСМОТРА", "sortable": true },
     { "key": "37", "label": "АНАМНЕЗ", "sortable": true }],
     curPet:[]
  },
}).$mount('#app')


