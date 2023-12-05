const fs = require("node:fs");
const path = require("node:path");

const data = fs.readFileSync(path.resolve(__dirname, "./input.txt"), {
  encoding: "utf-8",
  flag: "r",
});

const dataArray = data.toString().split("\r\n");

let numbersLine1 = [];
let numbersLine2 = [];
let gearsLine1 = [];
let gearsLine2 = [];
let gearRatioSum = 0;

const processLine = function (pLine) {
  const line = pLine.trim();
  let i = 0;

  while (i < line.length) {
    if (!isNaN(line[i])) {
      let gearIndex = -1;

      if (i > 0 && line[i - 1] === "*") {
        gearIndex = i - 1;
      }

      let startIndex = i;
      let number = line[i];
      while (!isNaN(line[++i])) {
        number += line[i];
      }
      if (gearIndex !== -1)
        gearsLine2[gearsLine2.length - 1].push(parseInt(number));

      numbersLine2.push([parseInt(number), startIndex, i - 1]);
    } else if (line[i] === "*") {
      if (!isNaN(line[i - 1])) {
        gearsLine2.push([i, numbersLine2[numbersLine2.length - 1][0]]);
      } else {
        gearsLine2.push([i]);
      }
      i++;
    } else {
      i++;
    }
  }

  gearsLine1.forEach((gear) => {
    for (let i = 0; i < numbersLine2.length; i++) {
      if (
        (numbersLine2[i][1] >= gear[0] - 1 &&
          numbersLine2[i][1] <= gear[0] + 1) ||
        (numbersLine2[i][2] >= gear[0] - 1 && numbersLine2[i][2] <= gear[0] + 1)
      ) {
        gear.push(numbersLine2[i][0]);
      }
    }
    if (gear.length === 3) {
      gearRatioSum += gear[1] * gear[2];
    }
  });

  gearsLine2.forEach((gear) => {
    for (let i = 0; i < numbersLine1.length; i++) {
      if (
        (numbersLine1[i][1] >= gear[0] - 1 &&
          numbersLine1[i][1] <= gear[0] + 1) ||
        (numbersLine1[i][2] >= gear[0] - 1 && numbersLine1[i][2] <= gear[0] + 1)
      ) {
        gear.push(numbersLine1[i][0]);
      }
    }
  });

  numbersLine1 = numbersLine2;
  gearsLine1 = gearsLine2;
  numbersLine2 = [];
  gearsLine2 = [];
};

dataArray.forEach((line) => {
  processLine(line);
});

processLine("");

console.log(gearRatioSum);
