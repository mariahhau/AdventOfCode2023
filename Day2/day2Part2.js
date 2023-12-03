const fs = require("node:fs");
const path = require("node:path");

const data = fs.readFileSync(path.resolve(__dirname, "./input.txt"), {
  encoding: "utf-8",
  flag: "r",
});

const dataArray = data.toString().split("\r\n");

const processLine = function (pLine) {
  let minimumSet = {
    red: 1,
    green: 1,
    blue: 1,
  };

  try {
    const cubes = pLine.split(/;|,|:/);

    for (let i = 1; i < cubes.length; i++) {
      const values = cubes[i].trim().split(" ");

      if (values[0] > parseInt(minimumSet[values[1]])) {
        minimumSet[values[1]] = values[0];
      }
    }
  } catch (e) {
    console.log(e);
  }

  const power = Object.values(minimumSet).reduce((a, b) => a * b, 1);
  return power;
};

let answer = 0;

dataArray.forEach((line, i) => {
  const result = processLine(line, i);
  !isNaN(result) ? (answer += result) : console.log("Result was not a number");
});

console.log(answer);
