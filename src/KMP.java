
import java.util.*;

public class KMP {

    private static int[] buildLPS(String pattern) {
        int[] lps = new int[pattern.length()];
        int j = 0;

        for (int i = 1; i < pattern.length(); i++) {
            while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = lps[j - 1];
            }

            if (pattern.charAt(i) == pattern.charAt(j)) {
                j++;
            }

            lps[i] = j;
        }

        return lps;
    }

    public static Map<String, List<Integer>> search(
            String text, List<String> patterns) {

        Map<String, List<Integer>> result = new HashMap<>();

        for (String pattern : patterns) {
            int[] lps = buildLPS(pattern);
            int j = 0;

            for (int i = 0; i < text.length(); i++) {
                while (j > 0 && text.charAt(i) != pattern.charAt(j)) {
                    j = lps[j - 1];
                }

                if (text.charAt(i) == pattern.charAt(j)) {
                    j++;
                }

                if (j == pattern.length()) {
                    int index = i - pattern.length() + 1;

                    result.computeIfAbsent(pattern,
                            k -> new ArrayList<>()).add(index);

                    j = lps[j - 1];
                }
            }
        }

        return result;
    }
}
