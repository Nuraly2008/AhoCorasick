
import java.util.*;

public class AhoCorasick {

    static class Node {
        Map<Character, Node> children = new HashMap<>();
        Node failure;
        Node output;
        String pattern;
    }

    private final Node root = new Node();

    public AhoCorasick(List<String> patterns) {
        for (String pattern : patterns) {
            insert(pattern);
        }
        buildFailureLinks();
    }

    private void insert(String pattern) {
        Node current = root;

        for (char c : pattern.toCharArray()) {
            current.children.putIfAbsent(c, new Node());
            current = current.children.get(c);
        }

        current.pattern = pattern;
    }

    private void buildFailureLinks() {
        Queue<Node> queue = new LinkedList<>();
        root.failure = root;

        for (Node child : root.children.values()) {
            child.failure = root;
            queue.add(child);
        }

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            for (char c : current.children.keySet()) {
                Node child = current.children.get(c);
                Node failure = current.failure;

                while (failure != root &&
                        !failure.children.containsKey(c)) {
                    failure = failure.failure;
                }

                child.failure = failure.children.getOrDefault(c, root);

                if (child.failure.pattern != null) {
                    child.output = child.failure;
                } else {
                    child.output = child.failure.output;
                }

                queue.add(child);
            }
        }
    }

    public Map<String, List<Integer>> search(String text) {
        Map<String, List<Integer>> result = new HashMap<>();
        Node current = root;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            while (current != root &&
                    !current.children.containsKey(c)) {
                current = current.failure;
            }

            current = current.children.getOrDefault(c, root);

            Node match = current;

            while (match != null) {
                if (match.pattern != null) {
                    int index = i - match.pattern.length() + 1;

                    result.computeIfAbsent(match.pattern,
                            k -> new ArrayList<>()).add(index);
                }

                match = match.output;
            }
        }

        return result;
    }
}
