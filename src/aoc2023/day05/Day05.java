package aoc2023.day05;

import aoc2023.Day;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day05 extends Day {

    public Day05() {
        super("src/aoc2023/Day05/input.txt");
    }

    @Override
    public Object partOne(Stream<String> lines) {
        List<String> allLines = lines.toList();
        List<Long> seeds = Arrays.stream(allLines.get(0)
                        .split(":")[1].trim()
                        .split(" "))
                .map(Long::parseLong).collect(Collectors.toList());

        Map<Long, Long> currentMap = new HashMap<>();
        List<Long> currentValues = seeds;
        Set<Integer> processed = new HashSet<>();
        for (String line : allLines.stream().skip(1).toList()) {
            if (line.isBlank()) {
                continue;
            }

            if (line.contains("map")) {
                processed.clear();
                continue;
            }
            List<Long> numbers = Arrays.stream(line.trim().split(" ")).map(Long::parseLong).toList();

            GardenMap gardenMap = new GardenMap(numbers.get(0), numbers.get(1), numbers.get(2));
            for (int i = 0; i < seeds.size(); i++) {
                if (processed.contains(i)) continue;
                Long current = seeds.get(i);
                if (current >= gardenMap.sourceStart && current < (gardenMap.sourceStart + gardenMap.range)) {
                    Long next = gardenMap.destinationStart + (current - gardenMap.sourceStart);
                    seeds.set(i, next);
                    processed.add(i);
                }
            }
        }

        return currentValues.stream().min(Long::compareTo).get();
    }

    @Override
    public Object partTwo(Stream<String> lines) {
        return null;
    }

    static class GardenMap {
        long destinationStart;
        long sourceStart;
        long range;

        public GardenMap(long destinationStart, long sourceStart, long range) {
            this.destinationStart = destinationStart;
            this.sourceStart = sourceStart;
            this.range = range;
        }

        @Override
        public String toString() {
            return "GardenMap{" +
                    "destinationStart=" + destinationStart +
                    ", sourceStart=" + sourceStart +
                    ", range=" + range +
                    '}';
        }
    }

}
