import { Coordinates } from "./ejection";
import { Area } from "./areas";

export function generateRandomDoubleInRange(min: number, max: number): number {
    if (min>max){
        let temp = min;
        min = max;
        max = temp;
    }
    return Math.random() * (max - min) + min;
}

/**
 * 
 * @param min minimum of the range, inclusive
 * @param max maximum of the range, exclusive
 */
export function generateRandomIntInRange(min: number, max: number): number {
    return Math.floor(generateRandomDoubleInRange(min,max));
}

export function generateCoordinatesInRectangle(area: Area): Coordinates{
    let lat = generateRandomDoubleInRange(area.bottom, area.top);
    let lon = generateRandomDoubleInRange(area.left, area.right);
    return { lat: lat, lon: lon };
}