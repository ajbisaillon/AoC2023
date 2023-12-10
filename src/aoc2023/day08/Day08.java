package aoc2023.day08;

import aoc2023.Day;
import aoc2023.Node;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day08 extends Day {
    public Day08() {
        super("src/aoc2023/Day08/input.txt");
    }

    @Override
    public Object partOne(Stream<String> lines) {
        List<String> allLines = lines.toList();
        List<String> directions = Arrays.stream(allLines.get(0).split("")).toList();

        Map<String, Node> network = new HashMap<>();
        Node start = null;

        for (String line : allLines.stream().skip(2).toList()) {

            // make 3 nodes and add to map
            final String regex = "([A-Z]{3}) = \\(([A-Z]{3}), ([A-Z]{3})\\)";
            final Pattern pattern = Pattern.compile(regex);
            final Matcher matcher = pattern.matcher(line);

            if (matcher.find()) {
                Node root = new Node(matcher.group(1));
                Node left = new Node(matcher.group(2));
                Node right = new Node(matcher.group(3));

                root.leftPointer = left;
                root.rightPointer = right;
                if (start == null) {
                    start = root;
                }
                network.put(root.value, root);
            }
        }

        // traverse based on instructions
        int steps = 0;
        assert start != null;
        String currentNodeValue = start.value;
        final String TARGET_VALUE = "ZZZ";


        int i = 0;
        while (!currentNodeValue.equals(TARGET_VALUE)) {
            if (directions.get(i).equals("L")) {
                currentNodeValue = network.get(currentNodeValue).leftPointer.value;
            }
            if (directions.get(i).equals("R")) {
                currentNodeValue = network.get(currentNodeValue).rightPointer.value;
            }
            i = (i + 1) % directions.size();
            steps++;
        }

        return steps;
    }

    @Override
    public Object partTwo(Stream<String> lines) {
        List<String> allLines = lines.toList();
        List<String> directions = Arrays.stream(allLines.get(0).split("")).toList();

        Map<String, Node> network = new HashMap<>();
        List<Node> startNodes = new ArrayList<>();

        for (String line : allLines.stream().skip(2).toList()) {

            // make 3 nodes and add to map
            final String regex = "([A-Z0-9]{3}) = \\(([A-Z0-9]{3}), ([A-Z0-9]{3})\\)";
            final Pattern pattern = Pattern.compile(regex);
            final Matcher matcher = pattern.matcher(line);

            if (matcher.find()) {
                Node root = new Node(matcher.group(1));
                Node left = new Node(matcher.group(2));
                Node right = new Node(matcher.group(3));

                root.leftPointer = left;
                root.rightPointer = right;
                network.put(root.value, root);
                if (root.value.endsWith("A")) {
                    startNodes.add(root);
                }
            }
        }

        // traverse for each branch based on instructions
        long totalSteps = 1L;
        long steps = 0L;

        for (Node currentNode : startNodes) {
            int i = 0;
            while (!targetReached(currentNode)) {
                if (directions.get(i).equals("L")) {
                    currentNode = network.get(currentNode.value).leftPointer;
                }
                if (directions.get(i).equals("R")) {
                    currentNode = network.get(currentNode.value).rightPointer;
                }
                i = (i + 1) % directions.size();
                steps++;
            }

            totalSteps = calculateLeastCommonMultiple(
                    totalSteps,
                    steps,
                    calculateHighestCommonFactor(totalSteps, steps)
            );
            steps = 0;
        }
        return totalSteps;
    }

    boolean targetReached(Node node) {
        return node.value.endsWith("Z");
    }

    long calculateHighestCommonFactor(long x, long y) {
        long min = Math.min(x, y);
        for (long i = min; i > 0; i--) {
            if (x % i == 0 && y % i == 0) {
                return i;
            }
        }
        return min;
    }

    long calculateLeastCommonMultiple(long x, long y, long hcf) {
        return x * y / hcf;
    }
}
