class Route {
    listPosition: Array<Position>;

    constructor(listPosition: Array<Position>) {
        this.listPosition = listPosition;
    }
}

class Position {
    latitude: number;
    longitude: number;

    constructor(latitude: number, longitude: number) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}

export { Route, Position };