package aoc2023.day01;

import aoc2023.Day;

import java.util.stream.Stream;

public class Day01 extends Day<Integer, Integer> {

    public Day01() {
        super("src/aoc2023/Day01/day01_input.txt");
    }

    public Day01(String inputFilePath) {
        super(inputFilePath);
    }

    @Override
    public Integer partOne() {

        Stream<String> lines = getLinesStream();

        return lines.map(this::getNumberFromLine).reduce(0, Integer::sum);
    }

    @Override
    public Integer partTwo() {
        return null;
    }

    Integer getNumberFromLine(String line) {
        Character first = null;
        Character last = null;

        for (char ch : line.toCharArray()) {
            if (Character.isDigit(ch)) {
                if (first == null) {
                    first = ch;
                }
                last = ch;
            }
        }

        return Integer.parseInt("" + first + last);
    }

}
