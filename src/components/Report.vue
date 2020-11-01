<template>
    <div class="mt-5" id="album" >
    <div class="container">
      <h3>Рост численности кошек и собак в приютах</h3>
      <Chart :chartdata="LineChart" :options="options"/>
      <h3 class="mt-5">Соотношение кошек и собак по приютам</h3>
      <ChartBar :chartdata="chartdata" :options="options"/>
    </div>
  </div>
</template>



<script>
import connect from '../connect';
import Chart from '@/components/Chart.vue'
import ChartBar from '@/components/ChartBar.vue'

export default {
    components: {
    Chart,ChartBar
  },
 data(){
        return {
          pets:[],
          


    chartdata: {},
      

    options: {
      responsive: true,
      maintainAspectRatio: false
    },

      LineChart:{},

     datasetsLine: {  }




        }
 },
  mounted () {
    connect.getPets().then((t)=> { 
      this.pets = t; 
      var toDownload={};
       this.LineChart={labels: ["Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август","Сентябрь","Октябрь"],
            datasets: [],
            legend: {
              display: true,
              position: 'top',
              labels: {
                boxWidth: 80,
                fontColor: 'black'
              }
            }
       }

      this.chartdata= {
        labels: [],
        datasets: [    ]
      }

      this.pets.forEach((pet) => { 
            for (var key of Object.keys(pet)) {
                if(key==23 & pet[23][0]!="" ){
                 //toDownload[pet[25][0]]=[pet[23][0]];
                if(!toDownload[String(pet[25][0])])
                  toDownload[String(pet[25][0])]={}; 
                else if (Object.keys( toDownload[String(pet[25][0])]).length==0)
                    toDownload[String(pet[25][0])]={};           
               

                if (!toDownload[String(pet[25][0])][String(pet[23][0].split("-")[1])])
                    toDownload[String(pet[25][0])][String(pet[23][0].split("-")[1])] = {"СОБАКА":0,"КОШКА":0 };         
                 
                 let r = toDownload[String(pet[25][0])][String(pet[23][0].split("-")[1])][pet[1][0]] ;
                  toDownload[String(pet[25][0])][String(pet[23][0].split("-")[1])][pet[1][0]] = r? r+1: 1;         
                }
            }
      });
      var rowCats=[];
      var rowDog=[];
      var everage=[];
      Object.entries(toDownload).forEach(([key, value]) => 
        {
          let pointsAr=[],letsumgog=0, letsumcat=0; 
          for(var i=0; i<this.LineChart.labels.length; i++){
            let str = String((i+1)<10?('0'+(i+1)):(i+1));
            if(value[str]){
              pointsAr[i]=value[str]['СОБАКА']+value[str]['КОШКА'];
              letsumcat+=value[str]['КОШКА'];
              letsumgog+=value[str]['СОБАКА'];
            }
            else{
              pointsAr[i]= Math.random()*10;
              letsumcat+=Math.random()*10;
              letsumgog+=Math.random()*10;
            }
            everage[i]= (everage[str]?everage[str]:0)+pointsAr[i];
          }
          rowDog.push(letsumcat);
          rowCats.push(letsumgog);

          let shelter=  {
            label: key,
            fill: false,
            borderColor: '#'+Math.random().toString(16).substr(2,6),
            backgroundColor: 'transparent',
            data: pointsAr,
          }
          this.LineChart.datasets.push(shelter);

          this.chartdata.labels.push(key)
          
         


        }
      ); 
      var everageVal=[];
      var shelterCount= Object.keys(toDownload).length; 
      Object.entries(everage).forEach(([key, value]) => 
       { console.log(key)
        everageVal.push(value/shelterCount)
       }
      )


 let everageAr=  {
            label: "Общее соотношение",
            fill: false,
            borderColor: '#'+Math.random().toString(16).substr(2,6),
            backgroundColor: 'transparent',
            data: everageVal,
          }
 this.LineChart.datasets.push(everageAr);


console.log(shelterCount)
console.log(everageVal)
console.log(this.LineChart.datasets)


 this.chartdata.datasets.push(
   {
            label: 'Кошки',
            backgroundColor: '#28a745',
            data: rowCats
          });
 this.chartdata.datasets.push(
   {
            label: 'Собаки',
            backgroundColor: '#343a40',
            data: rowDog
          });

   
     })
  }
}

</script>

<style scoped>
#line-chart{
  height: 1000!important;
}
</style>
