package teamApt;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class MainReader {

    private static HashMap<String, List<String>> networkPrefixes = new HashMap<>();
    private static HashMap<String, Integer> networkCount = new HashMap<>();

    public static void main(String[] args) {
        assignValuesToMap();
        try {
            BufferedReader reader = readFrom("C:\\dev\\airtimeReader\\PhoneNumbers.txt");
            readEachLine(reader);
            printNetworkSummary();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public static BufferedReader readFrom(String filePath) throws IOException {
        return Files.newBufferedReader(Paths.get(filePath));
    }

    public static void readEachLine(BufferedReader reader) throws IOException {
        reader.lines().forEach(MainReader::hasMatchingSubstring);
    }

    private static <K, V> Stream<Map.Entry<K, V>> mapToStream (Map<K, V> map) {
        return map.entrySet().stream();
    }

    private static void hasMatchingSubstring(String line) {

        Stream<Map.Entry<String, List<String>>> stream = mapToStream(networkPrefixes);

        stream.forEach(set -> set.getValue().forEach((prefix) -> {
            if (line.startsWith(prefix)) {
                networkCount.put(set.getKey(),networkCount.get(set.getKey()) +line.split(",").length);
            }
        })
        );
    }

    public static void assignValuesToMap(){
        networkPrefixes.put("mtn", List.of("0703","0706","0803","0806", "0810", "0813", "0814", "0816", "0903", "0906","0916", "0916", "07025","07026","0704"));
        networkPrefixes.put("airtel", List.of("0701","0708","0802","0808","0812","0901","0902","0904","0907","0912"));
        networkPrefixes.put("globacom", List.of("0705","0805","0807","0811","0815","0905","0915"));
        networkPrefixes.put("etisalat", List.of("0809","0817","0818","0909","0908"));

        networkCount.put("mtn", 0);
        networkCount.put("airtel", 0);
        networkCount.put("globacom", 0);
        networkCount.put("etisalat", 0);
    }

    private static void printNetworkSummary(){
        Stream<Map.Entry<String, Integer>> stream = mapToStream(networkCount);
        stream.forEach(set -> {
            System.out.println(set.getKey() + "\t| " + set.getValue());
        });
    }
}
