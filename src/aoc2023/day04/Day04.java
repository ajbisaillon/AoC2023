package aoc2023.day04;

import aoc2023.Day;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day04 extends Day {
    public Day04() {
        super("src/aoc2023/Day04/input.txt");
    }

    @Override
    public Object partOne(Stream<String> lines) {
        return lines.map(line -> {
            String numbers = line.substring(line.indexOf(":") + 1);
            String[] parts = numbers.split("\\|");
            Set<String> winningNumbers = Arrays
                    .stream(parts[0].split(" "))
                    .filter(x -> !x.isEmpty())
                    .collect(Collectors.toSet());
            Set<String> gameNumbers = Arrays
                    .stream(parts[1].split(" "))
                    .filter(x -> !x.isEmpty())
                    .collect(Collectors.toSet());
            gameNumbers.retainAll(winningNumbers);
            if (gameNumbers.size() == 0) {
                return 0;
            }
            return (int) Math.pow(2, gameNumbers.size() - 1);
        }).reduce(0, Integer::sum);
    }

    @Override
    public Object partTwo(Stream<String> lines) {
        return null;
    }
}
