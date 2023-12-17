package aoc2023.day09;

import aoc2023.Day;
import aoc2023.day03.PartNumber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Day09 extends Day {
    public Day09() {
        super("src/aoc2023/Day09/input.txt");
    }

    @Override
    public Object partOne(Stream<String> lines) {
        List<List<Long>> allHistoryEntries = lines
                .map(line -> Arrays.stream(line.split(" "))
                        .map(Long::parseLong).toList()
                ).toList();

        long predictionsTotal = 0L;
        for (List<Long> entries : allHistoryEntries) {
            List<Long> lastValues = new ArrayList<>();
            lastValues.add(entries.get(entries.size() - 1));

            while (!isZeroedOut(entries)) {
                entries = calculateNextSequence(entries);
                lastValues.add(entries.get(entries.size() - 1));
            }

            Collections.reverse(lastValues);
            Long prediction = lastValues.stream().reduce(0L, Long::sum);
            predictionsTotal += prediction;
        }
        return predictionsTotal;
    }

    @Override
    public Object partTwo(Stream<String> lines) {
        List<List<Long>> allHistoryEntries = lines
                .map(line -> Arrays.stream(line.split(" "))
                        .map(Long::parseLong).toList()
                ).toList();

        long predictionsTotal = 0L;
        for (List<Long> entries : allHistoryEntries) {
            List<Long> firstValues = new ArrayList<>();
            firstValues.add(entries.get(0));

            while (!isZeroedOut(entries)) {
                entries = calculateNextSequence(entries);
                firstValues.add(entries.get(0));
            }

            firstValues.remove(firstValues.size() - 1);
            Collections.reverse(firstValues);
            Long prediction = firstValues.stream().reduce(0L, (total, current) -> current - total);
            predictionsTotal += prediction;
        }
        return predictionsTotal;
    }

    List<Long> calculateNextSequence(List<Long> sequence) {
        List<Long> newSequence = new ArrayList<>();
        for (int i = 1; i < sequence.size(); i++) {
            long difference = sequence.get(i) - sequence.get(i - 1);
            newSequence.add(difference);
        }
        return newSequence;
    }

    boolean isZeroedOut(List<Long> sequence) {
        return sequence.stream().allMatch(x -> x == 0L);
    }
}
