import re
waysToWin = []

with open("Day6\\input.txt", "r", encoding="utf-8") as file:
    times = list(map(int, re.findall(r'\d+', file.readline())))
    distances = list(map(int, re.findall(r'\d+', file.readline())))
    file.close()


for (t, d) in zip(times, distances):
    temp = []
    for s in range(1, t+1):
        distance = s * (t - s)
        if distance > d:
            temp.append(distance)
    waysToWin.append(len(temp))


result = 1

for num in waysToWin:
    result = result * num

print(result)
