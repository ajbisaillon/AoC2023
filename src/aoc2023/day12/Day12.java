package aoc2023.day12;

import aoc2023.Day;
import aoc2023.datastructures.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day12 extends Day {
    public Day12() {
        super("src/aoc2023/Day12/input.txt");
    }

    @Override
    public Object partOne(Stream<String> lines) {
        return lines.map(line -> line.split(" ")).map(parts -> new Tuple2<>(
                parts[0],
                Arrays.stream(parts[1].split(",")).map(Integer::parseInt).toList()))
                .map(record -> {
                    // find the indices of the unknowns
                    List<Integer> unknownIndices = getIndicesOfUnknowns(record.first, "?");
                    // try every combination for those unknown positions
                    // and check whether it's a valid combination based on the numbers given
                    return tryAllCombinations(record.first, unknownIndices, record.second);
                }).reduce(0, Integer::sum);
    }

    @Override
    public Object partTwo(Stream<String> lines) {
        return lines.map(line -> line.split(" ")).map(parts -> new Tuple2<>(
                        parts[0],
                        Arrays.stream(parts[1].split(",")).map(Integer::parseInt).toList()))
                .map(record -> {
                    // find the indices of the unknowns
                    List<Integer> unknownIndices = getIndicesOfUnknowns(record.first, "?");
                    // try every combination for those unknown positions
                    // and check whether it's a valid combination based on the numbers given
                    return tryAllCombinations(record.first, unknownIndices, record.second);
                }).reduce(0, Integer::sum);
    }


    List<List<String>> getAllCombinations(Integer numberOfUnknowns) {
        List<List<String>> allCombinations = new ArrayList<>();
        for (long i = 0; i < (1L << numberOfUnknowns); i++) {
            List<String> combination = new ArrayList<>();
            for (int j = 0; j < numberOfUnknowns; j++) {
                if ((i >> j) % 2 == 1) {
                    combination.add(".");
                } else {
                    combination.add("#");
                }
            }
            allCombinations.add(combination);
        }
        return allCombinations;
    }

    List<Integer> getIndicesOfUnknowns(String line, String unknownSymbol) {
        List<Integer> indices = new ArrayList<>();
        int index = line.indexOf(unknownSymbol);
        while (index >= 0) {
            indices.add(index);
            index = line.indexOf(unknownSymbol, index + 1);
        }
        return indices;
    }

    boolean isCombinationValid(String springsPossibility, List<Integer> groupSizes) {
        int runLength = 0;
        int groupSizeIndex = 0;
        int stringIndex = 0;
        while (stringIndex < springsPossibility.length()) {
            if (springsPossibility.charAt(stringIndex) == '#') {
                runLength++;
            } else {
                if (runLength == groupSizes.get(groupSizeIndex)) {
                    groupSizeIndex++;
                    runLength = 0;
                } else if (runLength != 0) {
                    return false;
                }

            }
            stringIndex++;
            if (groupSizeIndex >= groupSizes.size()) {
                break;
            }
        }
        if (groupSizeIndex < groupSizes.size() && runLength == groupSizes.get(groupSizeIndex)) {
            groupSizeIndex++;
        }
        if (groupSizeIndex != groupSizes.size()) {
            return false;
        }
        return springsPossibility.indexOf("#", stringIndex) == -1;
    }

    Integer tryAllCombinations(String record, List<Integer> unknownIndices, List<Integer> groupSizes) {
        int validCombinations = 0;
        boolean valid = false;
        List<List<String>> allCombinations = getAllCombinations(unknownIndices.size());

        for (List<String> combination : allCombinations) {
            int unknown = 0;
            StringBuilder substitutedRecord = new StringBuilder(record);
            for (String springValue : combination) {
                substitutedRecord.setCharAt(unknownIndices.get(unknown++), springValue.charAt(0));
            }

            String str = substitutedRecord.toString();
            valid = isCombinationValid(str, groupSizes);
            if (valid) {
                validCombinations++;
            }
        }

        System.out.println(record + " " + validCombinations);
        return validCombinations;
    }
}












