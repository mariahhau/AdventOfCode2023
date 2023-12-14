package Day9;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day9Part2 {
    public static void main(String[] args) {
        List<List<Long>> lines = new ArrayList<>();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader("Day9\\input.txt"));
            String line = br.readLine();
            while (null != line) {
                List<Long> numbers = Stream.of(line.split(" ")).map(Long::valueOf).collect(Collectors.toList());
                lines.add(numbers);
                line = br.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
            }
        }

        long sum = 0;

        for (List<Long> line : lines) {
            sum += calculatePrevious(line);
        }

        System.out.println("Sum: " + sum);
    }

    public static long calculatePrevious(List<Long> list) {
        if (list == null || list.size() < 1)
            return -1;

        if (list.size() == 1)
            return list.get(0);

        List<Long> newList = new ArrayList<>();
        boolean zeroDiff = true;
        Long firstDiff;

        firstDiff = Long.valueOf(list.get(1) - list.get(0));
        newList.addFirst(firstDiff);

        for (int i = 1; i < list.size() - 1; i++) {
            Long diff = Long.valueOf(list.get(i + 1) - list.get(i));
            newList.add(diff);
            if (diff != firstDiff) {
                zeroDiff = false;
            }
        }

        if (zeroDiff) {
            return (list.get(0) - firstDiff);
        } else {
            return list.get(0) - calculatePrevious(newList);
        }
    }

}
