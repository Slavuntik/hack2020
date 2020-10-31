const settings = {
    connectionString: "mongodb+srv://dtl:Kamaz$321@cluster0.1hfyy.mongodb.net/dtl?retryWrites=true&w=majority"
}

const mongodb = require('mongodb');

async function loadDataCollection(collectionName) {
    const client = await mongodb.MongoClient.connect(settings.connectionString, {
        useNewUrlParser: true,
        useUnifiedTopology: true
    })
    return client.db(settings.dbname).collection(collectionName)
}

let stopWords=[
    "дата", "номер карточки", "возраст, год", "кличка", "карточка учета животного","вес, кг","особые приметы", "идентификационная метка","Вольер №","дата стерилизации","заказ-наряд / акт о поступлении животного №","заказ-наряд дата/ акт о поступлении животного, дата","акт отлова №","адрес места отлова","дата выбытия из приюта","дата поступления в приют","физическое лицо ф.и.о.","акт №","акт/договор №","карточка учета животного №","юридическое лицо","адрес приюта","№ п/п","№ серии","дата осмотра","выдан","дата выдачи","дата регистрации","адрес","телефон"
]
for (let i=0;i<stopWords.length;i++) {
    stopWords[i]=stopWords[i].toUpperCase()
}



class databaseService  {

    static async getAllPets() {
        let petsCol=await loadDataCollection("Pets")
        let pets=await petsCol.find({}).toArray()
        if (pets) {
            return pets.map((pet)=> {
                return {
                    pet:pet.petData
                }
            })
        }
        else {
            return []
        }
    }

    static async getDic(dicName) {
        let tempCol=await loadDataCollection("dics")
        let dic=await tempCol.findOne({"header":dicName.toUpperCase()})
        if (dic) {
            return {
                "header":dic.header,
                "values":dic.values,
                "count":dic.values.length
            }
        }
        else {
            return []
        }
    }

    static async updateSourceData(rows) {
        //updating dictionaries
        let tempCol=await loadDataCollection("dics")
        await tempCol.deleteMany({})
        let counter=0
        let total=0
        for (let j=0;j<rows[1].length;j++) {
            if (rows[1][j]&&(stopWords.indexOf(rows[1][j].toString().toUpperCase())==-1)) {
                
            let values=[]
            
            for (let i=2;i<rows.length;i++) {
                if (rows[i][j]) {
                let tt=rows[i][j].toString().replace(/ +/g, ' ').trim().toUpperCase();
                if (values.indexOf(tt)==-1) {
                    values.push(tt)
                }
            }
            }
            
            let data={
                "header":rows[1][j].toString().toUpperCase(),
                "values":values
            }
            counter++
            await tempCol.insertOne(data)
            }
            total++
            console.log(counter,total, "columns proceed")

        }
        console.log()
        let dics=await (await tempCol.find({}).toArray()).map((arg)=> {
            return {
                "header":arg.header,
                "values":arg.values,
                "count":arg.values.length
            }
        })
        //pets
        let petsCol=await loadDataCollection("Pets")
        await petsCol.deleteMany({})
        let petsCounter=0
        for (let i=2;i<rows.length;i++) {
            let pet=[]
            let cellIndex=0
            for (let j=0;j<rows[1].length;j++) {
                if (rows[1][j]&&rows[i][j]) {
                    
                    let cellValue=rows[i][j].toString().replace(/ +/g, ' ').trim().toUpperCase()
                    let cellHeader=rows[1][j].toString().toUpperCase()
                    let cell={
                        "cellIndex":cellIndex,
                        "cellHeader":cellHeader,
                        "cellValue":cellValue
                    }
                    cellIndex++
                    pet.push(cell)

                }
            }
            pet.push({
                "photos":[]
            })
            petsCounter++
            try
            {
            await petsCol.insertOne({petData:pet})
            }
            catch (err) {
                console.log(err)
            }
            console.log(petsCounter)
        }

        return {"petsImported":petsCounter,
            dics
    }
    }
    
}



module.exports=databaseService