import {generateRandomIntInRange} from './random-generators'

let pilotNames = ["דרור זליכה", "אופיר בר", "אורי חייק", "זאב אלרואי", "עשהאל מובשוביץ"]
export class Coordinates{
    lat: number
    lon: number
}
export class Ejection {
    static lastId=1;
    id: number
    coordinates: Coordinates
    pilotName: string

    constructor(coordinates: Coordinates){
        this.id = Ejection.lastId++;
        this.coordinates = coordinates;
        this.pilotName = pilotNames[ generateRandomIntInRange(0, pilotNames.length) ]
    }
}

/**
 * The ejections are managed in division to namespaces - this enables the student to create
 * ejections that are only visible to themselves, so they can debug in a predictible environment.
 * 
 * So this is a map of  namespace->list of ejections.
 */
let database = new Map<string, Ejection[]>();

export function clearAll(namespace: string){
    database.delete(namespace);
}

export function addEjection(ejection: Ejection, namespace: string){
    if (!database.has(namespace)) database.set(namespace, []);

    database.get(namespace).push(ejection);
}

export function getEjections(ofNamespace: string): Ejection[] {
    if (!database.has(ofNamespace)) return [];

    return database.get(ofNamespace);
}