#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

int main()
{
    FILE *filep;
    char line[200];
    int total = 0;

    filep = fopen("./input.txt", "r");
    if (filep == NULL)
    {
        printf("Error opening file");
        return -1;
    }

    while (fgets(line, 200, filep) != NULL)
    {
        char winningNum[50], numbers[150];
        int winArray[20], numArray[50];
        int i = 0, n = 0;
        int points = 0;

        strtok(line, ":|");
        strcpy(winningNum, strtok(NULL, ":|"));
        strcpy(numbers, strtok(NULL, ":|"));

        char *num = strtok(winningNum, " ");

        while (NULL != num)
        {
            winArray[n++] = atoi(num);
            num = strtok(NULL, " ");
        }

        num = strtok(numbers, " ");

        while (NULL != num)
        {
            numArray[i++] = atoi(num);
            num = strtok(NULL, " ");
        }

        for (int j = 0; j < i; j++)
        {
            for (int m = 0; m < n; m++)
            {
                if (numArray[j] == winArray[m])
                {
                    if (points == 0)
                    {
                        points = 1;
                    }
                    else
                    {
                        points *= 2;
                    }
                }
            }
        }
        total += points;
    }
    printf("\ntotal: %d\n", total);
    fclose(filep);
    return 0;
}
