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


nextP, d = find_next_point(start)
steps = 1

while nextP != start:
    nextP, d = find_next_point(nextP, d)
    steps += 1

print(int(steps/2))
