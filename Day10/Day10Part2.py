pipes_up = {
    ('|', 'J', 'L', 'S'): ('|', '7', 'F', 'S')
}

pipes_down = {
    ('|', '7', 'F', 'S'): ('|', 'J', 'L', 'S')
}

pipes_right = {
    ('-', 'L', 'F', 'S'): ('-', '7', 'J', 'S')
}

pipes_left = {
    ('-', '7', 'J', 'S'): ('-', 'L', 'F', 'S')
}


# 1=right 2=left 3=up 4=down
def find_next_point(point, direction=0):

    row = point[0]
    column = point[1]

    if column < COLUMNS - 1 and direction != 2:
        for key, _ in pipes_right.items():
            if lines[row][column] in key or lines[row][column] == 'S':
                if lines[row][column+1] in pipes_right[key]:
                    return (row, column+1), 1

    if column != 0 and direction != 1:
        for key, _ in pipes_left.items():
            if lines[row][column] in key or lines[row][column] == 'S':
                if lines[row][column-1] in pipes_left[key]:
                    return (row, column-1), 2

    if row != 0 and direction != 4:
        for key, _ in pipes_up.items():
            if lines[row][column] in key or lines[row][column] == 'S':
                if lines[row-1][column] in pipes_up[key]:
                    return (row-1, column), 3

    if row < ROWS - 1 and direction != 3:
        for key, _ in pipes_down.items():
            if lines[row][column] in key or lines[row][column] == 'S':
                if lines[row+1][column] in pipes_down[key]:
                    return (row+1, column), 4

    return point, direction


start = None
lines = []
with open("Day10\\input.txt", "r", encoding="utf-8") as file:
    for i, line in enumerate(file):
        lines.append(line.strip())
        if start is None:
            for j, char in enumerate(line):
                if char == 'S':
                    start = (i, j)
    file.close()


ROWS = len(lines)
COLUMNS = len(lines[0])
path = {}
nextP, d = find_next_point(start)
path[nextP[0]] = [nextP[1]]

while nextP != start:
    nextP, d = find_next_point(nextP, d)
    if nextP[0] in path:
        path[nextP[0]].append(nextP[1])
    else:
        path[nextP[0]] = [nextP[1]]

tiles = {}
tileCount = 0

for key, value in path.items():
    sortedValues = sorted(value)
    minV = sortedValues[0]
    maxV = sortedValues[-1]
    tiles_in_key = []
    for value in range(minV+1, maxV):
        if value not in sortedValues:
            tiles_in_key.append(value)
    if len(tiles_in_key) > 0:
        tiles[key] = tiles_in_key


for key, value in tiles.items():
    for v in value:
        count = 0
        temp = None

        for p in path[key]:

            if p > v:
                pipe = lines[key][p]
                match pipe:
                    case '|':
                        count += 1
                    case 'L':
                        if temp is None:
                            temp = 'L'
                        elif temp == '7':
                            count += 1
                            temp = None
                        elif temp == 'J':
                            count += 2
                            temp = None
                        else:
                            pass
                    case '7':
                        if temp is None:
                            temp = '7'
                        elif temp == 'L':
                            count += 1
                            temp = None
                        elif temp == 'F':
                            count += 2
                            temp = None
                        else:
                            pass
                    case 'J':
                        if temp is None:
                            temp = 'J'
                        elif temp == 'F':
                            count += 1
                            temp = None
                        elif temp == 'L':
                            count += 2
                            temp = None
                        else:
                            pass
                    case 'F':
                        if temp is None:
                            temp = 'F'
                        elif temp == 'J':
                            count += 1
                            temp = None
                        elif temp == '7':
                            count += 2
                            temp = None
                        else:
                            pass
                    case _:
                        pass

        if count % 2 != 0:
            tileCount += 1


print(tileCount)
