package aoc2023.day01;

import aoc2023.Day;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.stream.Stream;

public class Day01 extends Day<Integer, Integer> {

    LinkedHashMap<String, String> numberMap = new LinkedHashMap<>(){{
        put("one", "1");
        put("two", "2");
        put("three", "3");
        put("four", "4");
        put("five", "5");
        put("six", "6");
        put("seven", "7");
        put("eight", "8");
        put("nine", "9");
    }};

    public Day01() {
        super("src/aoc2023/Day01/input.txt");
    }

    public Day01(String inputFilePath) {
        super(inputFilePath);
    }

    @Override
    public Integer partOne() {
        Stream<String> lines = getLinesStream();
        return lines.map(this::getDigitsFromLine).reduce(0, Integer::sum);
    }

    @Override
    public Integer partTwo() {
        Stream<String> lines = getLinesStream();
        return lines.map(this::getNumbersFromLine).reduce(0, Integer::sum);
    }

    Integer getDigitsFromLine(String line) {
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

    Integer getNumbersFromLine(String line) {
        return Integer.parseInt(getFirstNumberFromLine(line) + getLastNumberFromLine(line));
    }

    String getFirstNumberFromLine(String line) {
        CharacterIterator it = new StringCharacterIterator(line);
        StringBuilder buffer = new StringBuilder();
        char ch = it.first();
        while (ch != CharacterIterator.DONE) {
            if (Character.isDigit(ch)) {
                return String.valueOf(ch);
            }
            buffer.append(ch);
            Optional<String> result = findNumberWord(buffer);
            if (result.isPresent()) {
                return numberMap.get(result.get());
            }
            ch = it.next();
        }
        return null;
    }

    String getLastNumberFromLine(String line) {
        CharacterIterator it = new StringCharacterIterator(line);
        StringBuilder buffer = new StringBuilder();
        char ch = it.last();
        while (ch != CharacterIterator.DONE) {
            if (Character.isDigit(ch)) {
                return String.valueOf(ch);
            }
            buffer.insert(0, ch);
            Optional<String> result = findNumberWord(buffer);
            if (result.isPresent()) {
                return numberMap.get(result.get());
            }
            ch = it.previous();
        }
        return null;
    }

    Optional<String> findNumberWord(StringBuilder buffer) {
        String finalBuffer = buffer.toString();
        return numberMap.keySet().stream().filter(finalBuffer::contains).findFirst();
    }

}
