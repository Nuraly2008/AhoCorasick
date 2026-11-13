
import java.io.PrintWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    private static final Random random = new Random(42);

    public static void main(String[] args) throws IOException {
        test();

        int[] patternCounts = {10, 100, 1000};
        int[] textLengths = {1000, 10000, 100000};

        try (PrintWriter writer = new PrintWriter("results.csv")) {

            String header =
                    "patterns,text_length,aho_avg_ns,kmp_avg_ns";

            writer.println(header);
            System.out.println(header);

            for (int count : patternCounts) {
                for (int length : textLengths) {

                    String text = randomString(length);
                    List<String> patterns =
                            generatePatterns(text, count);

                    long ahoTime = 0;
                    long kmpTime = 0;

                    for (int i = 0; i < 5; i++) {

                        long start = System.nanoTime();

                        Map<String, List<Integer>> aho =
                                new AhoCorasick(patterns).search(text);

                        ahoTime += System.nanoTime() - start;

                        start = System.nanoTime();

                        Map<String, List<Integer>> kmp =
                                KMP.search(text, patterns);

                        kmpTime += System.nanoTime() - start;

                        if (!aho.equals(kmp)) {
                            throw new IllegalStateException(
                                    "Results do not match");
                        }
                    }

                    String row = count + "," + length + ","
                            + ahoTime / 5 + "," + kmpTime / 5;

                    writer.println(row);
                    System.out.println(row);
                }
            }
        }
    }

    private static String randomString(int length) {
        StringBuilder text = new StringBuilder();

        for (int i = 0; i < length; i++) {
            text.append((char) ('a' + random.nextInt(26)));
        }

        return text.toString();
    }

    private static List<String> generatePatterns(
            String text, int count) {

        Set<String> patterns = new LinkedHashSet<>();

        while (patterns.size() < count / 2) {
            int start = random.nextInt(text.length() - 4);
            patterns.add(text.substring(start, start + 5));
        }

        while (patterns.size() < count) {
            patterns.add(randomString(5));
        }

        return new ArrayList<>(patterns);
    }

    private static void test() {

        check(
                "ushers",
                Arrays.asList("he", "she", "his", "hers"),
                Map.of(
                        "he", List.of(2),
                        "she", List.of(1),
                        "hers", List.of(2)
                )
        );

        check(
                "aaa",
                Arrays.asList("a", "aa"),
                Map.of(
                        "a", List.of(0, 1, 2),
                        "aa", List.of(0, 1)
                )
        );

        System.out.println("All tests passed");
    }

    private static void check(
            String text,
            List<String> patterns,
            Map<String, List<Integer>> expected) {

        if (!new AhoCorasick(patterns).search(text).equals(expected)
                || !KMP.search(text, patterns).equals(expected)) {

            throw new IllegalStateException("Test failed");
        }
    }
}
