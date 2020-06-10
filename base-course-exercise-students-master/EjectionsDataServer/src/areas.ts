export class Area {
    top: number
    right: number
    bottom: number
    left: number
}

let areas =  {
    lebanon: {
        bottom: 31.41,
        top: 31.62,
        left: 35.18,
        right: 35.53
    } as Area,
    syria: {
        bottom: 31.17,
        top: 31.81,
        left: 35.89,
        right: 37.47
    } as Area,
    iraq: {
        bottom: 29.38,
        top: 32.59,
        left: 41.46,
        right: 45.79
    } as Area
}

export {areas}