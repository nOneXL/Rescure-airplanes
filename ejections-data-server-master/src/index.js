const express = require("express");
const path = require("path");
const ejection = require("./ejection");
const areasModule = require("./areas.js");
const genrateCoordModule = require("./random-generators");

const app = express();

app.use(express.static(path.join(__dirname, "public")));

let checkName = (namespace) => {
  return namespace && RegExp("^[A-Za-zא-ת0-9]+$").test(namespace);
};

app.get("/clear", (req, res) => {
  let namespace = req.query.name;
  if (!checkName(namespace)) {
    res.status(400);
    res.send("Illegal name");
    return;
  }
  ejection.clearAll(namespace);
  res.send();
});

app.get("/ejections", (req, res) => {
  let namespace = req.query.name;
  if (!checkName(namespace)) {
    res.status(400);
    res.send("Illegal name");
    return;
  }

  res.send(ejection.getEjections(namespace));
});

app.get("/:kind", (req, res) => {
  let kind = req.params.kind;
  let namespace = req.query.name;
  if (!checkName(namespace)) {
    res.status(400);
    res.send("Illegal name");
    return;
  } else {
    let area;
    switch (kind) {
      case "lebanon":
        area = areasModule.areas.lebanon;
        break;
      case "syria":
        area = areasModule.areas.syria;
        break;
      case "iraq":
        area = areasModule.areas.iraq;
        break;
      default:
        res.status(404);
        res.send("No such country");
        return;
    }

    let coord = genrateCoordModule.generateCoordinatesInRectangle(area);
    ejection.addEjection(new ejection.Ejection(coord), namespace);
    res.send();
  }
});

app.listen(4000, () => {
  console.log("Ready");
});
