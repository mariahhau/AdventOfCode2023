const fs = require("node:fs");
const path = require("node:path");

const data = fs.readFileSync(path.resolve(__dirname, "./input.txt"), {
  encoding: "utf-8",
  flag: "r",
});

const dataArray = data.toString().split("\r\n");

let previousNumbers = [];
let nextNumbers = [];
let previousSymbols = [];
let nextSymbols = [];
let partNumberSum = 0;

const processLine = function (pLine) {
  let i = 0;
  const line = pLine.trim();

  while (i < line.length) {
    if (!isNaN(line[i])) {
      let startIndex = i;
      let number = line[i];
      while (!isNaN(line[++i])) {
        number += line[i];
      }
      startIndex > 0 ? (startIndex -= 1) : startIndex;
      nextNumbers.push([parseInt(number), startIndex, i]);
    } else if (line[i] !== ".") {
      nextSymbols.push(i);
      i++;
    } else {
      i++;
    }
  }

  nextNumbers.forEach((number) => {
    for (symbolIndex of nextSymbols) {
      if (symbolIndex >= number[1] && symbolIndex <= number[2]) {
        partNumberSum += number[0];
        number[0] = 0;
      }
    }
  });

  previousNumbers.forEach((number) => {
    for (symbolIndex of nextSymbols) {
      if (symbolIndex >= number[1] && symbolIndex <= number[2]) {
        partNumberSum += number[0];
        number[0] = 0;
      }
    }
  });

  nextNumbers.forEach((number) => {
    for (symbolIndex of previousSymbols) {
      if (symbolIndex >= number[1] && symbolIndex <= number[2]) {
        partNumberSum += number[0];
        number[0] = 0;
      }
    }
  });

  previousNumbers = nextNumbers;
  previousSymbols = nextSymbols;
  nextNumbers = [];
  nextSymbols = [];
};

dataArray.forEach((line) => {
  processLine(line);
});

console.log(partNumberSum);
