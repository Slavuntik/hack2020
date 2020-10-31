const express = require('express');
const router = express.Router();
const databaseService=require('../utils/databaseService')
const docx = require('docx');
const { VerticalAlign, AlignmentType } = require('docx');


console.log("Reports service")


const getFieldByIndex=function(index,petData) {
    let result=null
    for (let i=0;i<petData.length;i++) {
        if (petData[i].cellIndex==index) {
            result=petData[i].cellValue
            break
        }
    }
    return result
}

router.get('/', async (req, res) => {
    const { Document, Packer, Paragraph, Table, TableCell, TableRow , AlignmentType} = docx;

    let pets=await databaseService.getAllPets()

    


    let tableRows=[]
    let header=new TableRow({
        children: [
            new TableCell({
                children: [new Paragraph({text:"№ п/п",alignment:AlignmentType.CENTER})],
                verticalAlign: VerticalAlign.CENTER,
            }),
            new TableCell({
                children: [new Paragraph({text:"Карточка учета животного №",alignment:AlignmentType.CENTER})],
            }),
            new TableCell({
                children: [new Paragraph({text:"Кличка животного",alignment:AlignmentType.CENTER})],
            }),
            new TableCell({
                children: [new Paragraph({text:"Вид животного (кошка/собака",alignment:AlignmentType.CENTER})],
            }),
            new TableCell({
                children: [new Paragraph({text:"Пол животного",alignment:AlignmentType.CENTER})],
            }),
            new TableCell({
                children: [new Paragraph({text:"Идентификационная метка",alignment:AlignmentType.CENTER})],
            }),
            new TableCell({
                children: [new Paragraph({text:"Дата поступления в приют",alignment:AlignmentType.CENTER})],
            }),
        ],
    })
    let counter=0
    tableRows.push(header)
    pets.forEach((pet)=> {
        counter++
        let tableRow=new TableRow({
            children: [
                new TableCell({
                    children: [new Paragraph({text:counter.toString(),alignment:AlignmentType.CENTER})],
                    verticalAlign: VerticalAlign.CENTER,
                }),
                new TableCell({
                    children: [new Paragraph({text:getFieldByIndex(0,pet.petData),alignment:AlignmentType.CENTER})],
                }),
                new TableCell({
                    children: [new Paragraph({text:getFieldByIndex(4,pet.petData),alignment:AlignmentType.CENTER})],
                }),
                new TableCell({
                    children: [new Paragraph({text:getFieldByIndex(1,pet.petData),alignment:AlignmentType.CENTER})],
                }),
                new TableCell({
                    children: [new Paragraph({text:getFieldByIndex(5,pet.petData),alignment:AlignmentType.CENTER})],
                }),
                new TableCell({
                    children: [new Paragraph({text:getFieldByIndex(14,pet.petData),alignment:AlignmentType.CENTER})],
                }),
                new TableCell({
                    children: [new Paragraph({text:getFieldByIndex(23,pet.petData),alignment:AlignmentType.CENTER})],
                }),
            ],
        })
        tableRows.push(tableRow)
    })
    

    let doc = new Document();

    let table = new Table({
        rows: tableRows
    });

    doc.addSection({
        children: [table],
    });

    let b64string = await Packer.toBase64String(doc);
    
    res.setHeader('Content-Disposition', 'attachment; filename=Prilojenie_4.docx');
    res.send(Buffer.from(b64string, 'base64'));  
    console.log("report created")  
})


module.exports = router