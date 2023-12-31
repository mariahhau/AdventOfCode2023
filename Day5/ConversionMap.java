import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ConversionMap {
    private Map<String, List<MapEntry>> map = new LinkedHashMap<>();
    private BufferedReader br;
    private String[] seeds = {};

    public void readFile(String filename) throws IOException {
        br = new BufferedReader(new FileReader(filename));
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
                    map.get(parts[0]).sort(Comparator.comparing(MapEntry::getSRangeStart));

                    line = br.readLine();
                }
            }
            line = br.readLine();

        }

        br.close();
    }

    public long findMinLocation() {
        long minLocation = Integer.MAX_VALUE;
        if (seeds != null) {
            for (int i = 0; i < seeds.length - 1; i = i + 2) {
                long seedMin = Long.valueOf(seeds[i]);
                long seedMax = seedMin + Long.valueOf(seeds[i + 1]) - 1;
                long result = map(0, seedMin, seedMax);
                if (result < minLocation) {
                    minLocation = result;
                }

            }
        }
        return minLocation;
    }

    private long map(int keyIndex, final long min, final long max) {

        long result = Integer.MAX_VALUE;
        long newMin = min;
        long newMax = max;
        boolean foundMin = false;
        boolean foundMax = false;

        String key = map.keySet().toArray()[keyIndex].toString();
        List<MapEntry> sourceToDest = map.get(key);

        while (newMin != -1 && newMax != -1) {

            // find MapEntry for min
            for (MapEntry mapEntry : sourceToDest) {
                if (mapEntry.isInRange(newMin)) {
                    long temp;
                    if (mapEntry.getSRangeEnd() >= newMax) {
                        if (key.equals("humidity-to-location")) {
                            temp = mapEntry.map(newMin);
                        } else {
                            temp = map(keyIndex + 1, mapEntry.map(newMin), mapEntry.map(newMax));
                        }

                        newMax = -1;
                        newMin = -1;
                    } else {
                        if (key.equals("humidity-to-location")) {
                            temp = mapEntry.map(newMin);
                        } else {
                            temp = map(keyIndex + 1, mapEntry.map(newMin), mapEntry.map(mapEntry.getSRangeEnd()));
                        }

                        newMin = mapEntry.getSRangeEnd() + 1;
                    }

                    if (temp < result) {
                        result = temp;
                    }

                    foundMin = true;

                    break;
                } else {
                    foundMin = false;
                }
            }
            // if didn't find MapEntry for min, look for max
            if (!foundMin) {
                for (MapEntry mapEntry : sourceToDest) {
                    long temp;
                    if (mapEntry.isInRange(newMax)) {
                        if (key.equals("humidity-to-location")) {
                            temp = mapEntry.map(mapEntry.getSRangeStart());
                        } else {
                            temp = map(keyIndex + 1, mapEntry.map(mapEntry.getSRangeStart()), mapEntry.map(newMax));

                        }

                        newMax = mapEntry.getSRangeStart() - 1;
                        if (temp < result) {
                            result = temp;
                        }
                        foundMax = true;
                        break;
                    } else {
                        foundMax = false;
                    }
                }
            }
            if (!foundMin && !foundMax) {
                // assume there are no gaps in data
                long temp;
                if (key.equals("humidity-to-location")) {
                    temp = newMin;
                } else {
                    temp = map(keyIndex + 1, newMin, newMax);
                }

                if (temp < result) {
                    result = temp;
                }
                newMax = -1;
                newMin = -1;

            }
        }

        return result;

    }

    public void printMap() {
        map.forEach((key, value) -> System.out.println(key + " : " + value));
    }

    public String[] getSeeds() {
        return seeds;
    }

    public void printSeeds() {
        for (String seed : seeds) {
            System.out.println(seed);
        }
    }
}
