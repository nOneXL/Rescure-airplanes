const genrateCoordModule = require("./random-generators");

let pilotNames = [
  "דרור זליכה",
  "אופיר בר",
  "אורי חייק",
  "זאב אלרואי",
  "עשהאל מובשוביץ",
];

class Coordinates {
  lat;
  lon;
}
class Ejection {
  static lastId = 1;
  id;
  coordinates;
  pilotName;

  constructor(coordinates) {
    this.id = Ejection.lastId++;
    this.coordinates = coordinates;
    this.pilotName =
      pilotNames[
        genrateCoordModule.generateRandomIntInRange(0, pilotNames.length)
      ];
  }
}

/**
 * The ejections are managed in division to namespaces - this enables the student to create
 * ejections that are only visible to themselves, so they can debug in a predictible environment.
 *
 * So this is a map of  namespace->list of ejections.
 */
let database = new Map();

function clearAll(namespace) {
  database.delete(namespace);
}

function addEjection(ejection, namespace) {
  if (!database.has(namespace)) database.set(namespace, []);

  database.get(namespace).push(ejection);
}

function getEjections(ofNamespace) {
  if (!database.has(ofNamespace)) return [];

  return database.get(ofNamespace);
}

module.exports = { Coordinates, Ejection, clearAll, addEjection, getEjections };
