package Day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Day8Part2 {

    public static void main(String[] args) throws Exception {
        char[] instructions;
        List<String> points = new ArrayList<>();
        Map<String, String[]> map = new ConcurrentHashMap<>();
        BufferedReader br = new BufferedReader(new FileReader("Day8\\input.txt"));

        String line = br.readLine();
        instructions = line.toCharArray();
        while (line != null) {
            if (line.length() >= 1) {
                String[] parts = line.split("[=]");

                if (parts.length == 2) {
                    String key = parts[0].trim();
                    parts[1] = parts[1].replaceAll("(,|\\(|\\))", "");
                    String[] values = parts[1].trim().split(" ");

                    if (values.length != 2) {
                        System.out.println("Error reading values");
                    }

                    if (key.length() == 3 && key.charAt(2) == 'A') {
                        points.add(key);
                    }
                    map.put(key, values);
                }
            }
            line = br.readLine();
        }

        br.close();

        List<Long> steps = new ArrayList<>();
        for (String point : points) {
            String current = point;
            boolean found = false;
            long stepCount = 0;

            while (!found) {
                for (int i = 0; i < instructions.length; i++) {
                    int instr = instructions[i] == 'R' ? 1 : 0;
                    if (map.containsKey(current)) {
                        stepCount++;

                        String next = map.get(current)[instr];

                        if (next.charAt(2) == 'Z') {
                            steps.add(stepCount);
                            found = true;
                        }
                        current = next;

                        if (current.equals(point) && i == 0) {
                            steps.add(Long.valueOf(-1));
                            found = true;
                        }
                    }
                }
            }
        }

        System.out.println(lcm(steps));

    }

    // Greatest common divisor
    static long gcd(long a, long b) {
        if (a % b != 0)
            return gcd(b, a % b);
        else
            return b;
    }

    // Least common multiple
    static long lcm(List<Long> numbers) {
        long m = 1;
        for (int i = 0; i < numbers.size(); i++) {
            m = (m * numbers.get(i)) / (gcd(m, numbers.get(i)));
        }
        return m;
    }
}
