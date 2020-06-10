function generateRandomDoubleInRange(min, max) {
  if (min > max) {
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
function generateRandomIntInRange(min, max) {
  return Math.floor(generateRandomDoubleInRange(min, max));
}

function generateCoordinatesInRectangle(area) {
  let lat = generateRandomDoubleInRange(area.bottom, area.top);
  let lon = generateRandomDoubleInRange(area.left, area.right);
  return { lat: lat, lon: lon };
}

module.exports = {
  generateRandomDoubleInRange,
  generateRandomIntInRange,
  generateCoordinatesInRectangle,
};
