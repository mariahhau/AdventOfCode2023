const fs = require("node:fs");
const path = require("node:path");

const numberArray = new Array();

const numberStrings = {
  one: 1,
  two: 2,
  three: 3,
  four: 4,
  five: 5,
  six: 6,
  seven: 7,
  eight: 8,
  nine: 9,
};

const searchWord = function (text, arrayOfWords) {
  let found;

  arrayOfWords.some((t) => {
    const match = text.match(new RegExp(t));

    if (match) {
      found = numberStrings[match[0]];
      return true;
    }
  });

  return found;
};

const processLine = function (pLine) {
  const line = pLine.trim();
  let first,
    firstString = "";
  let second,
    secondString = "";

  for (let i = 0; i < line.length; i++) {
    firstString += line[i];

    const foundText = searchWord(firstString, Object.keys(numberStrings));

    if (foundText) {
      first = foundText;
      second = first;
      break;
    }

    if (!isNaN(line[i])) {
      first = line[i];
      second = first;
      break;
    }
  }

  for (let i = line.length - 1; i >= 0; i--) {
    secondString = line[i] + secondString;

    const foundText = searchWord(secondString, Object.keys(numberStrings));

    if (foundText) {
      second = foundText;
      break;
    }

    if (!isNaN(line[i])) {
      second = line[i];
      break;
    }
  }

  let number = parseInt(first + "" + second);

  numberArray.push(number);
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
console.log("Answer: ", answer);
