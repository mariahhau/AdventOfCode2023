import java.io.IOException;

public class Day5Part2 {

    public static void main(String args[]) throws IOException {

        ConversionMap cMap = new ConversionMap();
        cMap.readFile("Day5\\input.txt");
        System.out.println("Location number: " + cMap.findMinLocation());

    }

}
