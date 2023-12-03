const fs = require("node:fs");
const path = require("node:path");

const cubes = {
  red: 12,
  green: 13,
  blue: 14,
};

const data = fs.readFileSync(path.resolve(__dirname, "./input.txt"), {
  encoding: "utf-8",
  flag: "r",
});

const dataArray = data.toString().split("\r\n");

const processLine = function (pLine) {
  let gameId;

  try {
    const rounds = pLine.split(";");
    const regex = /Game\s\d+\:/;
    const game = rounds[0].match(regex)[0];
    gameId = parseInt(game.match(/\d+/)[0]);

    rounds[0] = rounds[0].replace(regex, "");

    for (const round of rounds) {
      const colorArray = round.split(",");
      for (const color of colorArray) {
        const result = color.trim().split(" ");
        if (cubes[result[1]] < result[0]) {
          return 0;
        }
      }
    }
  } catch (e) {
    console.log(e);
  }

  return gameId;
};

let answer = 0;

dataArray.forEach((line, i) => {
  const result = processLine(line, i);
  !isNaN(result) ? (answer += result) : console.log("Result was not a number");
});

console.log(answer);
