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
        List<String> allLines = lines.toList();
        List<Long> seeds = Arrays.stream(allLines.get(0)
                        .split(":")[1].trim()
                        .split(" "))
                .map(Long::parseLong).toList();
        List<String> allMapLines = allLines.stream().skip(1).toList();

        List<Interval> seedIntervals = new ArrayList<>();
        for (int i = 0; i < seeds.size(); i = i + 2) {
            Long seedStart = seeds.get(i);
            Long seedRange = seeds.get(i + 1);
            seedIntervals.add(new Interval(seedStart, seedStart + seedRange - 1));
        }
        seedIntervals.sort(Comparator.comparingLong((Interval i) -> i.start));


        for (long j = 0L; j < Long.MAX_VALUE; j++) {
            long current = j;
//            if (j % 1000000 == 0) System.out.println(j);

            boolean processed = false;
            for (int k = allMapLines.size() - 1; k > 0; k--) {
                String line = allMapLines.get(k);

                if (line.contains("map")) {
                    processed = false;
                    continue;
                }

                if (line.isBlank() || processed) continue;


                List<Long> numbers = Arrays.stream(line.trim().split(" ")).map(Long::parseLong).toList();
                GardenMap gardenMap = new GardenMap(numbers.get(1), numbers.get(0), numbers.get(2));
                if (current >= gardenMap.sourceStart && current < (gardenMap.sourceStart + gardenMap.range)) {
                    current = gardenMap.destinationStart + (current - gardenMap.sourceStart);
                    processed = true;
                }
            }

            for (Interval seedInterval : seedIntervals) {
                if (current >= seedInterval.start && current <= seedInterval.end) {
                    return j;
                }
            }
        }
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

    static class Interval implements Comparable<Interval> {
        Long start;
        Long end;

        public Interval(long start, long end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Interval o) {
            return this.start.compareTo(o.start);
        }

        @Override
        public String toString() {
            return "(" + start + "," + end + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Interval interval = (Interval) o;
            return Objects.equals(start, interval.start) && Objects.equals(end, interval.end);
        }

        @Override
        public int hashCode() {
            return Objects.hash(start, end);
        }
    }

}
