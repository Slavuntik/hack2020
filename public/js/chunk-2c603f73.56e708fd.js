(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2c603f73"],{"0242":function(t,e,r){"use strict";var s=r("c175"),i=r.n(s);i.a},"0c7c":function(t,e,r){"use strict";r.r(e);var s=function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("div",{staticClass:"mt-5",attrs:{id:"albumAdmin"}},[r("div",{staticClass:"container"},[r("div",{staticClass:"row w-100 justify-content-end mb-4"},[r("a",{staticClass:"btn btn-outline-primary mr-3",on:{click:function(e){return t.$router.push("/dashboard/0")}}},[r("i",{staticClass:"fas fa-plus"}),t._v("Добавить")]),r("download-excel",{attrs:{data:t.filterPets}},[r("a",{staticClass:"btn btn-outline-primary mr-3",attrs:{about:"_blank",href:"https://dtl2020.herokuapp.com/reports"}},[t._v("Экспорт в Exel")])]),r("b-button",{directives:[{name:"b-toggle",rawName:"v-b-toggle.collapseSet",modifiers:{collapseSet:!0}}],staticClass:"mr-3",attrs:{variant:"outline-primary"}},[r("i",{staticClass:"fas fa-cog"}),t._v(" Настройки отображения")]),r("b-button",{directives:[{name:"b-toggle",rawName:"v-b-toggle.collapseFilter",modifiers:{collapseFilter:!0}}],attrs:{variant:"outline-primary"}},[r("i",{staticClass:"fas fa-filter"}),t._v(" Фильтр")])],1),r("b-collapse",{staticClass:"my-2",attrs:{id:"collapseSet"}},[r("div",{staticClass:"row"},t._l(t.fieldsSetAr,(function(e,s){return r("div",{key:s,staticClass:"col-12 col-md-4"},[e?r("div",{staticClass:"form-check"},[r("input",{staticClass:"form-check-input",attrs:{type:"checkbox"},domProps:{checked:t.$root.setToShow.includes(e.label)},on:{change:function(r){return t.showField(r,e.label,e.key)}}}),r("label",{staticClass:"form-check-label",attrs:{for:"defaultCheck1"}},[t._v(t._s(e.label))])]):t._e()])})),0)]),r("b-collapse",{staticClass:"my-2",attrs:{id:"collapseFilter"}},[r("div",{staticClass:"row"},t._l(t.pets[0],(function(e,s){return r("div",{key:s,staticClass:"col-12 col-md-6"},[e[1]?r("div",{staticClass:"form-group"},[r("label",[t._v(t._s(e[1]))]),t.$root.dictionarisList.includes(e[1])?r("select",{staticClass:"custom-select mr-sm-2 filter"},[r("option",{attrs:{selected:""}},[t._v("Не выбрано...")]),t._l(t.$root.dictionary[e[1]],(function(e,s){return r("option",{key:s,domProps:{value:e}},[t._v(t._s(e))])}))],2):r("input",{staticClass:"form-control filter",attrs:{type:"text","data-id":s},on:{input:function(e){return t.filterPets(s,e)}}})]):t._e()])})),0)]),r("b-table",{attrs:{id:"my-table",striped:"",hover:"","head-variant":"dark",items:t.petsFilter,fields:t.fields,"current-page":t.currentPage,"per-page":t.perPage,selectable:"","select-mode":"single","sticky-header":"true"},on:{"row-selected":t.onRowSelected},scopedSlots:t._u([{key:"cell()",fn:function(e){return[t._v(" "+t._s(e.value[0])+" ")]}}])}),r("b-pagination",{attrs:{"sticky-header":!0,"total-rows":t.rows,"per-page":t.perPage,"aria-controls":"my-table"},on:{change:t.handlePageChange},model:{value:t.currentPage,callback:function(e){t.currentPage=e},expression:"currentPage"}})],1)])},i=[],a=(r("4de4"),r("caad"),r("fb6a"),r("a434"),r("2532"),{name:"Dashboard",props:["userToken","pets","fieldsSet"],components:{},data:function(){return{filter:null,filterOn:[],colSpan:2,perPage:100,currentPage:1,fields:[{key:"0",label:"КАРТОЧКА УЧЕТА ЖИВОТНОГО №"},{key:"1",label:"ВИД"},{key:"5",label:"ПОЛ"},{key:"4",label:"КЛИЧКА"}],test:null,rows:0,petsOriginal:[],petsFilter:[],fieldsSetAr:[]}},watch:{fieldsSet:function(){this.fieldsSetAr=this.fieldsSet},pets:function(){this.arLength=this.pets.length,this.rows=this.pets.length,this.petsFilter=this.pets.slice()}},methods:{filterPets:function(t,e){var r=e.target.value.toLocaleLowerCase(),s=this.pets.filter((function(e){return e[t][0].toLocaleLowerCase().includes(r)}));this.petsFilter=s,console.log(this.petsFilter)},showField:function(t,e,r){if(t.target.checked)this.fields.push({key:String(r),label:e});else{for(var s=0;s<this.fields.length;s++)this.fields[s].key==r&&this.fields.splice(s,1);this.colSpan--}},onRowSelected:function(t){this.$router.push("/dashboard/"+t[0][0][2])},handlePageChange:function(){},onRowClick:function(t){this.$router.push("/dashboard/"+t.row.id)}},created:function(){}}),n=a,o=(r("0242"),r("2877")),l=Object(o["a"])(n,s,i,!1,null,"3ee7d8e0",null);e["default"]=l.exports},2532:function(t,e,r){"use strict";var s=r("23e7"),i=r("5a34"),a=r("1d80"),n=r("ab13");s({target:"String",proto:!0,forced:!n("includes")},{includes:function(t){return!!~String(a(this)).indexOf(i(t),arguments.length>1?arguments[1]:void 0)}})},"44e7":function(t,e,r){var s=r("861d"),i=r("c6b6"),a=r("b622"),n=a("match");t.exports=function(t){var e;return s(t)&&(void 0!==(e=t[n])?!!e:"RegExp"==i(t))}},"4de4":function(t,e,r){"use strict";var s=r("23e7"),i=r("b727").filter,a=r("1dde"),n=r("ae40"),o=a("filter"),l=n("filter");s({target:"Array",proto:!0,forced:!o||!l},{filter:function(t){return i(this,t,arguments.length>1?arguments[1]:void 0)}})},"5a34":function(t,e,r){var s=r("44e7");t.exports=function(t){if(s(t))throw TypeError("The method doesn't accept regular expressions");return t}},8418:function(t,e,r){"use strict";var s=r("c04e"),i=r("9bf2"),a=r("5c6c");t.exports=function(t,e,r){var n=s(e);n in t?i.f(t,n,a(0,r)):t[n]=r}},a434:function(t,e,r){"use strict";var s=r("23e7"),i=r("23cb"),a=r("a691"),n=r("50c4"),o=r("7b0b"),l=r("65f0"),c=r("8418"),u=r("1dde"),d=r("ae40"),f=u("splice"),p=d("splice",{ACCESSORS:!0,0:0,1:2}),h=Math.max,b=Math.min,v=9007199254740991,g="Maximum allowed length exceeded";s({target:"Array",proto:!0,forced:!f||!p},{splice:function(t,e){var r,s,u,d,f,p,m=o(this),y=n(m.length),C=i(t,y),w=arguments.length;if(0===w?r=s=0:1===w?(r=0,s=y-C):(r=w-2,s=b(h(a(e),0),y-C)),y+r-s>v)throw TypeError(g);for(u=l(m,s),d=0;d<s;d++)f=C+d,f in m&&c(u,d,m[f]);if(u.length=s,r<s){for(d=C;d<y-s;d++)f=d+s,p=d+r,f in m?m[p]=m[f]:delete m[p];for(d=y;d>y-s+r;d--)delete m[d-1]}else if(r>s)for(d=y-s;d>C;d--)f=d+s-1,p=d+r-1,f in m?m[p]=m[f]:delete m[p];for(d=0;d<r;d++)m[d+C]=arguments[d+2];return m.length=y-s+r,u}})},ab13:function(t,e,r){var s=r("b622"),i=s("match");t.exports=function(t){var e=/./;try{"/./"[t](e)}catch(r){try{return e[i]=!1,"/./"[t](e)}catch(s){}}return!1}},c175:function(t,e,r){},caad:function(t,e,r){"use strict";var s=r("23e7"),i=r("4d64").includes,a=r("44d2"),n=r("ae40"),o=n("indexOf",{ACCESSORS:!0,1:0});s({target:"Array",proto:!0,forced:!o},{includes:function(t){return i(this,t,arguments.length>1?arguments[1]:void 0)}}),a("includes")},fb6a:function(t,e,r){"use strict";var s=r("23e7"),i=r("861d"),a=r("e8b5"),n=r("23cb"),o=r("50c4"),l=r("fc6a"),c=r("8418"),u=r("b622"),d=r("1dde"),f=r("ae40"),p=d("slice"),h=f("slice",{ACCESSORS:!0,0:0,1:2}),b=u("species"),v=[].slice,g=Math.max;s({target:"Array",proto:!0,forced:!p||!h},{slice:function(t,e){var r,s,u,d=l(this),f=o(d.length),p=n(t,f),h=n(void 0===e?f:e,f);if(a(d)&&(r=d.constructor,"function"!=typeof r||r!==Array&&!a(r.prototype)?i(r)&&(r=r[b],null===r&&(r=void 0)):r=void 0,r===Array||void 0===r))return v.call(d,p,h);for(s=new(void 0===r?Array:r)(g(h-p,0)),u=0;p<h;p++,u++)p in d&&c(s,u,d[p]);return s.length=u,s}})}}]);
//# sourceMappingURL=chunk-2c603f73.56e708fd.js.map