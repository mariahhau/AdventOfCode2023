#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

int main()
{
    FILE *filep;
    char line[200];
    int total = 0;
    int copies[250] = {0};
    int iLine = 0;

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
        int matches = 0;

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
                    matches += 1;
                }
            }
        }

        for (int k = 0; k <= copies[iLine]; k++)
        {
            total++;
            for (int l = iLine + 1; l <= iLine + matches; l++)
            {
                copies[l]++;
            }
        }

        iLine++;
    }

    printf("\ntotal: %d\n", total);
    fclose(filep);
    return 0;
}
