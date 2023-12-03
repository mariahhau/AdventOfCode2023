const fs = require("node:fs");
const path = require("node:path");

const numberArray = new Array();

const processLine = function (pLine) {
  const line = pLine.trim();
  let first;
  let second;
  for (let i = 0; i < line.length; i++) {
    if (!isNaN(line[i])) {
      first = line[i];
      second = line[i];
      break;
    }
  }

  for (let i = line.length - 1; i >= 0; i--) {
    if (!isNaN(line[i])) {
      second = line[i];
      break;
    }
  }

  let asd = parseInt(first + second);
  numberArray.push(asd);
};

const data = fs.readFileSync(path.resolve(__dirname, "./input.txt"), {
  encoding: "utf-8",
  flag: "r",
});
const dataArray = data.toString().split("\n");
dataArray.forEach((line, i) => {
  processLine(line, i);
});

const answer = numberArray.reduce((sum, current) => sum + current, 0);
console.log(answer);
