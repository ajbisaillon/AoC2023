package aoc2023.day15;

import aoc2023.Day;

import java.util.Arrays;
import java.util.stream.Stream;

public class Day15 extends Day {
    public Day15() {
        super("src/aoc2023/day15/input.txt");
    }

    @Override
    public Object partOne(Stream<String> lines) {
        // input is assumed to be nonempty
        return Arrays.stream(lines.findFirst().get().split(",")).map(step -> {
            int current = 0;
            for (int ch : step.toCharArray()) {
                current += ch;
                current *= 17;
                current %= 256;
            }
            return current;
        }).reduce(0, Integer::sum);
    }

    @Override
    public Object partTwo(Stream<String> lines) {
        return null;
    }
}
