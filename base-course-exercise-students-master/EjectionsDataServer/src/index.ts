import * as express from 'express'
import * as ejection from './ejection'
import {Area, areas} from './areas'
import {generateCoordinatesInRectangle} from './random-generators'

const app = express();

app.use(express.static('public'))

let checkName = (namespace: string): boolean => {
    return namespace && RegExp("^[A-Za-zא-ת0-9]+$").test(namespace)
}

app.get('/clear', (req,res)=>{
    let namespace: string = req.query.name
    if (!checkName(namespace)){
        res.status(400);
        res.send('Illegal name')
        return
    }
    ejection.clearAll(namespace)
    res.send()
})

app.get('/ejections', (req,res)=>{
    let namespace: string = req.query.name
    if (!checkName(namespace)){
        res.status(400);
        res.send('Illegal name')
        return
    }

    res.send(ejection.getEjections(namespace))
})

app.get('/:kind', (req, res)=>{
    let kind = req.params.kind;
    let namespace: string = req.query.name
    if (!checkName(namespace)){
        res.status(400);
        res.send('Illegal name')
        return
    }
    let area: Area;
    switch (kind){
        case "lebanon": area = areas.lebanon; break;
        case "syria": area = areas.syria; break;
        case "iraq": area = areas.iraq; break;
        default: res.status(404); res.send('No such country'); return;
    }

    let coord = generateCoordinatesInRectangle(area);
    ejection.addEjection(new ejection.Ejection(coord), namespace);
    res.send();
})

app.listen(4000,()=>{console.log("Ready")})