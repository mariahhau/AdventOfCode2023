package Day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Day8Part1 {

    public static void main(String args[]) throws IOException {
        char[] instructions;
        Map<String, String[]> map = new HashMap<>();
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
                    map.put(key, values);
                }
            }
            line = br.readLine();
        }

        int steps = 1;
        boolean found = false;
        String currentKey = "AAA";
        while (!found) {
            for (int i = 0; i < instructions.length; i++) {
                int instr = instructions[i] == 'R' ? 1 : 0;

                if (map.containsKey(currentKey)) {
                    currentKey = map.get(currentKey)[instr];
                }

                if (currentKey.equals("ZZZ")) {
                    found = true;
                    break;
                }
                steps++;
            }
        }

        System.out.println(steps);
        br.close();
    }
}
