import re
import math

with open("Day6\\input.txt", "r", encoding="utf-8") as file:
    time = "".join(re.findall(r'\d+', file.readline()))
    distance = "".join(re.findall(r'\d+', file.readline()))
    file.close()

a = -1
b = int(time)
c = -1 * int(distance)

d = (b**2) - (4*a*c)

zero_p1 = (-b-math.sqrt(d))/(2*a)
zero_p2 = (-b+math.sqrt(d))/(2*a)

result = math.floor(zero_p1 - zero_p2)
print(result)
