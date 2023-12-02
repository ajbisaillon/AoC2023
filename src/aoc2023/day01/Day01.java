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
        super("src/aoc2023/Day01/day01_input.txt");
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
        System.out.println(line);
        String first = null;
        String last = null;
        StringBuilder buffer = new StringBuilder();
        for (char ch : line.toCharArray()) {
            if (Character.isDigit(ch)) {
                if (first == null) {
                    first = String.valueOf(ch);
                }
                last = String.valueOf(ch);
                buffer = new StringBuilder();
            } else {
                buffer.append(ch);
                String finalBuffer = buffer.toString();
                String number = findNumberWord(finalBuffer);
                if (number != null) {
                    String digit = numberMap.get(number);
                    if (first == null) {
                        first = digit;
                    }
                    last = digit;
//                    buffer = new StringBuilder();
                }
            }
        }

        System.out.println(first + last);
        return Integer.parseInt(first + last);
    }

    String findNumberWord(String buffer) {
        CharacterIterator it = new StringCharacterIterator(buffer);
        StringBuilder substring = new StringBuilder();
        char ch = it.last();
        while (ch != CharacterIterator.DONE) {
            substring.insert(0, ch);
            String finalSubstring = substring.toString();
            Optional<String> number = numberMap.keySet().stream()
                    .filter(finalSubstring::contains).findFirst();
            if (number.isPresent()) {
                return number.get();
            }
            ch = it.previous();
        }
        return null;
    }

}
