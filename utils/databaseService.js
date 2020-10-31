const settings = {
    connectionString: "mongodb+srv://dtl:Kamaz$321@cluster0.1hfyy.mongodb.net/dtl?retryWrites=true&w=majority"
}

const mongodb = require('mongodb');
const { default: parseDate } = require('read-excel-file/commonjs/parseDate');
const fs=require('fs')
const imageToBase64 = require('image-to-base64');
const imageThumbnail = require('image-thumbnail');
let options = { percentage: 5, responseType: 'base64' }

async function loadDataCollection(collectionName) {
    const client = await mongodb.MongoClient.connect(settings.connectionString, {
        useNewUrlParser: true,
        useUnifiedTopology: true
    })
    return client.db(settings.dbname).collection(collectionName)
}

let stopWords=[
    "дата", "номер карточки", "возраст, год", "кличка", "карточка учета животного","вес, кг","особые приметы", "идентификационная метка","Вольер №","дата стерилизации","заказ-наряд / акт о поступлении животного №","заказ-наряд дата/ акт о поступлении животного, дата","акт отлова №","адрес места отлова","дата выбытия из приюта","дата поступления в приют","физическое лицо ф.и.о.","акт №","акт/договор №","карточка учета животного №","юридическое лицо","№ п/п","№ серии","дата осмотра","выдан","дата выдачи","дата регистрации","адрес","телефон"
]
for (let i=0;i<stopWords.length;i++) {
    stopWords[i]=stopWords[i].toUpperCase()
}





class databaseService  {
    static checkPath(path) {
        let fileExists=false
        let tempPath=path
        if (fs.existsSync(tempPath+".JPG")) {
            tempPath+=".JPG"
            fileExists=true
        }
        else if (fs.existsSync(tempPath+".jpg")) {
            
            tempPath+=".jpg"
            fileExists=true
        }
        else if (fs.existsSync(tempPath+".jpeg")) {
            tempPath+=".jpeg"
            fileExists=true
        } 
        else if (fs.existsSync(tempPath+".JPEG")) {
            tempPath+=".JPEG"
            fileExists=true
        }                  
        if (fileExists) {
            return tempPath
        }
        else {
            return null
        }
    }

    static async getPetPhotos(petid) {
        try {
        let photosCol=await loadDataCollection("Photos")
        let _petid=new mongodb.ObjectID(petid)
        let petPhotos=await photosCol.find({"petid":_petid}).toArray()
        return petPhotos
        }
        catch(err) {
            console.log(err)
            return null
        }
    }

    static async getAllPets() {
        let petsCol=await loadDataCollection("Pets")
        console.log("collection loaded")
        let pets=await petsCol.find({},{petData:1,photos:0}).toArray()

        console.log("done")
        return pets
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
        let shelterDirs=[]
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
                    if (rows[1][j].toString().toUpperCase()=="АДРЕС ПРИЮТА") {
                        shelterDirs.push((shelterDirs.length+1).toString()+". "+rows[i][j].toString().replace(/ +/g, ' ').trim())
                    }
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

        console.log(shelterDirs)

        //pets
        let petsCol=await loadDataCollection("Pets")
        let photosCol=await loadDataCollection("Photos")
        await petsCol.deleteMany({})
        await photosCol.deleteMany({})
        
        let petsCounter=0
        let photosImported=0
        for (let i=2;i<rows.length;i++) {
            let pet=[]
            let cellIndex=0
            let shelterIndex=-1
            let petCardIndex=-1
            for (let j=0;j<rows[1].length;j++) {
                if (rows[1][j]&&rows[i][j]) {
                    
                    let cellValue=rows[i][j].toString().replace(/ +/g, ' ').trim().toUpperCase()
                    let cellHeader=rows[1][j].toString().toUpperCase()
                    if (cellValue.indexOf("GMT")>-1) {
                        //formating date
                        try
                        {
                            let tempDate=new Date(cellValue)
                            cellValue=tempDate.getFullYear().toString()+"-"+(tempDate.getMonth()+1).toString().padStart(2,"0")+"-"+(tempDate.getDate()).toString()
                        }
                        catch(err) {
                            console.log(err)
                        }
                    }
                    if (cellHeader=="АДРЕС ПРИЮТА") {
                        shelterIndex=cellIndex
                    }
                    if (cellHeader=="КАРТОЧКА УЧЕТА ЖИВОТНОГО №") {
                        petCardIndex=cellIndex
                    }
                    let cell={
                        "cellIndex":cellIndex,
                        "cellHeader":cellHeader,
                        "cellValue":cellValue
                    }
                    cellIndex++
                    pet.push(cell)

                }
            }
            let importedPhoto=null
            //checking is there any photo in import dir
            let photo=null
            if ((shelterIndex>-1)&&(petCardIndex>-1)) {
                let arrayIndex=-1
                for (let i=0;i<shelterDirs.length;i++) {
                    if (shelterDirs[i].substr(3,shelterDirs[i].length-3).toUpperCase()==pet[shelterIndex].cellValue) {
                        arrayIndex=i
                        //console.log("MATCH", arrayIndex)
                        break;

                    }
                }
                if (arrayIndex>-1) {

                try
                {
                    let path="/Users/vyacheslavfokin/Documents/DTL/product/backend/routes/import/adv/photo/"+shelterDirs[arrayIndex]+"/"+pet[petCardIndex].cellValue
                    let fileExists=false
                    
                    let filePath=this.checkPath(path)
                    if (filePath) {
                        fileExists=true
                    }
                    else {
                        path=path.replace("3-","з-")
                        filePath=this.checkPath(path)
                        if (filePath) {
                            fileExists=true
                        }
                        else {
                            path=path.replace("К","к")
                            filePath=this.checkPath(path)
                            if (filePath) {
                                fileExists=true
                            }

                        }
                    }
                    if (fileExists) {
                        console.log("loading photo ", filePath)
                        await imageToBase64(filePath).then((r)=> {
                            //console.log(r)
                            importedPhoto=r
                        }).catch((err)=> {
                            console.log(err)
                        })
                        console.log("creating thumbnail for photo")
                        try
                        {
                            let thumbnail=await imageThumbnail(filePath,options)
                            console.log("created")
                            pet.push({"thumbnail":thumbnail})
        

                        }
                        catch (err) 
                        {
                            console.log(err)
                        }

                    }

                    
                    
                }
                catch
                {

                }
             }
            }

            if (importedPhoto) {
                photosImported++
            }
            petsCounter++
            try
            {
                let insertedId=null
            await petsCol.insertOne({petData:pet}).then((r)=> {
                insertedId=r.insertedId
            }).catch(err=> {
                console.log(err)
            })
            if (insertedId) {
                await photosCol.insertOne({"petid":insertedId,photos:[importedPhoto]})
            }

            }
            catch (err) {
                console.log(err)
            }
            console.log(petsCounter)
        }

        return {"petsImported":petsCounter,
        "photosImporeted":photosImported,
            "NSI":dics
    }
    }
    
}



module.exports=databaseService