import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Day5Part1 {

    public static void main(String args[]) throws IOException {
        String[] seeds = {};
        List<Long> locations = new ArrayList<>();
        Map<String, List<MapEntry>> map = new LinkedHashMap<>();
        BufferedReader br = new BufferedReader(new FileReader("Day5\\input.txt"));
        String line = br.readLine();

        while (line != null) {

            if (line.contains("seeds:")) {
                String[] parts = line.split(":");
                seeds = parts[1].trim().split(" ");
            }

            if (line.contains("map:")) {
                String[] parts = line.split(" ");
                map.put(parts[0], new ArrayList<MapEntry>());

                line = br.readLine();
                while (line != null && line.length() > 0) {
                    String[] values = line.trim().split(" ");
                    if (values.length != 3) {
                        System.out.println("Error reading values");
                    } else {
                        map.get(parts[0]).add(new MapEntry(Long.valueOf(values[0]), Long.valueOf(values[1]),
                                Long.valueOf(values[2])));
                    }
                    line = br.readLine();
                }
            }
            line = br.readLine();

        }

        if (seeds != null) {
            for (int i = 0; i < seeds.length; i++) {
                long source = Long.valueOf(seeds[i]);
                for (Map.Entry<String, List<MapEntry>> set : map.entrySet()) {
                    for (MapEntry e : set.getValue()) {
                        if (e.isInRange(source)) {
                            source = e.map(source);
                            break;
                        }
                    }
                }
                locations.add(source);
            }

        }

        long min = locations.get(0);
        for (Long location : locations) {
            if (location < min) {
                min = location;
            }
        }

        System.out.println("Location: " + min);
        br.close();

    }

}
