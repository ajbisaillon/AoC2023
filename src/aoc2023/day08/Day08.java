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
        return null;
    }
//        List<String> allLines = lines.toList();
//        List<String> directions = Arrays.stream(allLines.get(0).split("")).toList();
//
//        Map<String, Node> network = new HashMap<>();
//        Node start = null;
//
//        for (String line : allLines.stream().skip(2).toList()) {
//
//            // make 3 nodes and add to map
//            final String regex = "([A-Z]{3}) = \\(([A-Z]{3}), ([A-Z]{3})\\)";
//            final Pattern pattern = Pattern.compile(regex);
//            final Matcher matcher = pattern.matcher(line);
//
//            if (matcher.find()) {
//                Node root = new Node(matcher.group(1));
//                Node left = new Node(matcher.group(2));
//                Node right = new Node(matcher.group(3));
//
//                root.leftPointer = left;
//                root.rightPointer = right;
//                if (start == null) {
//                    start = root;
//                }
//                network.put(root.value, root);
//            }
//        }
//
//        // traverse based on instructions
//        int steps = 0;
//        assert start != null;
//        String currentNodeValue = start.value;
//        final String TARGET_VALUE = "ZZZ";
//
//
//        int i = 0;
//        while (!currentNodeValue.equals(TARGET_VALUE)) {
//            if (directions.get(i).equals("L")) {
//                currentNodeValue = network.get(currentNodeValue).leftPointer.value;
//            }
//            if (directions.get(i).equals("R")) {
//                currentNodeValue = network.get(currentNodeValue).rightPointer.value;
//            }
//            i = (i + 1) % directions.size();
//            steps++;
//        }
//
//        return steps;
//    }

    @Override
    public Object partTwo(Stream<String> lines) {
        List<String> allLines = lines.toList();
        List<String> directions = Arrays.stream(allLines.get(0).split("")).toList();

        Map<String, Node> network = new HashMap<>();
        List<Node> currentNodes = new ArrayList<>();

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
                    currentNodes.add(root);
                }
            }
        }
        System.out.println(currentNodes);

        // traverse for each branch based on instructions
        long steps = 0L;

        int i = 0;
        while (!allTargetsReached(currentNodes)) {
            List<Node> nextNodes = new ArrayList<>();
            for (Node currentNode : currentNodes) {
                if (directions.get(i).equals("L")) {
                    currentNode = network.get(currentNode.value).leftPointer;
                }
                if (directions.get(i).equals("R")) {
                    currentNode = network.get(currentNode.value).rightPointer;
                }
                nextNodes.add(currentNode);
            }
            currentNodes = nextNodes;
            i = (i + 1) % directions.size();
            steps++;

            List<Node> filteredNodes = currentNodes.stream().filter(node -> node.value.endsWith("Z")).toList();
            if (filteredNodes.size() != 0) {
                System.out.println(filteredNodes);
                return steps;
            }
        }

        return steps;
    }

    boolean allTargetsReached(List<Node> currentNodes) {
        for (Node node : currentNodes) {
            if (!node.value.endsWith("Z")) {
                return false;
            }
        }
        return true;
    }
}
