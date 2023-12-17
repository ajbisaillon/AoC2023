package aoc2023.day12;

import aoc2023.Day;
import aoc2023.datastructures.Tuple2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class Day12 extends Day {
    private final HashMap<Tuple2<String, List<Integer>>, Long> cache = new HashMap<>();

    public Day12() {
        super("src/aoc2023/Day12/input.txt");
    }

    @Override
    public Object partOne(Stream<String> lines) {
        return lines.map(line -> line.split(" ")).map(parts -> new Tuple2<>(
                        parts[0] + ".",
                        Arrays.stream(parts[1].split(",")).map(Integer::parseInt).toList()))
                .map(record -> countArrangements(record.first, 0, record.second)).reduce(0L, Long::sum);
    }

    @Override
    public Object partTwo(Stream<String> lines) {
        this.cache.clear();
        return lines.map(line -> line.split(" ")).map(parts -> {
                    String input = parts[0] + "?";
                    String groups = parts[1] + ",";
                    String repeatedInput = input.repeat(4) + parts[0] + ".";
                    String repeatedGroups = groups.repeat(5);
                    return new Tuple2<>(
                            repeatedInput,
                            Arrays.stream(repeatedGroups.split(",")).map(Integer::parseInt).toList());
                })
                .map(record -> countArrangements(record.first, 0, record.second)).reduce(0L, Long::sum);
    }


    long countArrangements(String input, int runLength, List<Integer> groups) {
        Tuple2<String, List<Integer>> state = new Tuple2<>(input, groups);

        if (runLength == 0 && cache.containsKey(state)) {
            return cache.get(state);
        }

        if (input.isBlank()) {
            if (groups.isEmpty()) {
                return 1;
            } else {
                return 0;
            }
        }

        char current = input.charAt(0);
        long arrangements = 0;
        if (current == '?') {
            arrangements = countArrangements("." + input.substring(1), runLength, groups)
                    + countArrangements("#" + input.substring(1), runLength, groups);
        } else if (current == '#') {
            if (groups.isEmpty()) {
                arrangements = 0;
            } else {
                arrangements = countArrangements(input.substring(1), runLength + 1, groups);
            }
        } else {
            if (!groups.isEmpty() && runLength == groups.get(0)) {
                List<Integer> newGroups = groups.subList(1, groups.size());
                arrangements = countArrangements(input.substring(1), 0, newGroups);
            } else if (runLength == 0) {
                arrangements = countArrangements(input.substring(1), runLength, groups);
            } else {
                arrangements = 0;
            }
        }

        // only cache solution if not in the middle of a run
        if (runLength == 0) {
            cache.put(state, arrangements);
        }
        return arrangements;
    }

}












