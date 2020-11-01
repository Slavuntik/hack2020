<template>
  <div id="application" class="mt-5">
    <div class="container">
        <h1 class="text-center page-title">Подача заявления <br>о жестоком обращении с животными</h1>
        <div class="fill-appl" v-if="!send">
            <div class="row">
                <div class="col-12 col-md-6">
                    <div class="form-group">
                        <label>Фамилия:</label>
                        <input type="text" placeholder="Петров" class="form-control" >
                    </div>
                    <div class="form-group">
                        <label>Имя:</label>
                        <input type="text" placeholder="Иван" class="form-control" >
                    </div>
                    <div class="form-group">
                        <label>Отчество:</label>
                        <input type="text" placeholder="Александрович" class="form-control" >
                    </div>
                    <div class="form-group">
                        <label>Год рождения:</label>
                        <input type="number" placeholder="" class="form-control" >
                    </div>
                    <div class="form-group">
                        <label>Адрес прописки:</label>
                        <input type="text" placeholder="" class="form-control" >
                    </div>
                    <div class="form-group">
                        <label>Адрес фактического проживания:</label>
                        <input type="text" placeholder="" class="form-control" >
                    </div>
                    <div class="form-group">
                        <label>Номер телефона:</label>
                        <input type="text" placeholder="" class="form-control" >
                    </div>
                    <div class="form-group">
                        <label>Email:</label>
                        <input type="text" placeholder="" class="form-control" >
                    </div>
                    <div class="form-group">
                        <label>Место работы:</label>
                        <input type="text" placeholder="" class="form-control" >
                    </div>
                    <div class="form-group">
                        <label>Должность:</label>
                        <input type="text" placeholder="" class="form-control" >
                    </div>
                    <div class="form-group">
                        <label>Укажите пол:</label>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1">
                            <label class="form-check-label" for="inlineRadio1">М</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio2" value="option2">
                            <label class="form-check-label" for="inlineRadio2">Ж</label>
                        </div>
                    </div>
                </div>
                <div class="col-12 col-md-6">
                    <div class="form-group">
                        <label>1. Укажите, что случилось:</label>
                        <select class="form-control">
                            <option>Животное пострадало, но осталось в живых</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>2. Как пострадало животное:</label>
                        <select class="form-control">
                            <option>Ранили из огнестрельного оружия</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>3. Произошло ли это в черте города:</label>
                        <select class="form-control">
                            <option>Да</option>
                            <option>Нет</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>4. Свидетелем этой ситуации были дети?:</label>
                        <select class="form-control">
                            <option>Да</option>
                            <option>Нет</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>5. Было ли это домашнее животное?:</label>
                        <select class="form-control">
                            <option>Да</option>
                            <option>Нет</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>6. Выберите свой регион?:</label>
                        <select class="form-control">
                            <option>Москва</option>
                            <option>Северная Осетия</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>7. Укажите дату и приблизительное время происшествия:</label>
                        <input type="date" placeholder="" class="form-control" >
                    </div>
                    <div class="form-group">
                        <label>8. Укажите адрес происшествия:</label>
                        <input type="text" placeholder="" class="form-control" >
                    </div>
                    <div class="form-group">
                        <label>9. Укажите адрес преступника:</label>
                        <input type="text" placeholder="" class="form-control" >
                    </div>
                    <div class="form-group">
                        <label>10. Расскажите, что случилось:</label>
                        <textarea  placeholder="" class="form-control" rows=3 ></textarea>
                    </div>
                </div>
            </div>
           <button @click="send=true" class="btn btn-primary mt-4">Отправить заявление</button> 
        </div>
        <div v-else class="sennd-ok text-center mt-4">
            <div><i class="far fa-check-circle text-success h1"></i></div>
            <h3 class="text-success">Ваше заявление отправлено на рассмотрение</h3>
        </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Application',
   data() {
        return {
            send: false,
        }
   },
  methods:{

  }

}
</script>

<style scoped>

</style>
