package aoc2023.day01;

import aoc2023.Day;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class Day01 extends Day {

    Map<String, String> numberMap = Map.of(
        "one", "1",
        "two", "2",
        "three", "3",
        "four", "4",
        "five", "5",
        "six", "6",
        "seven", "7",
        "eight", "8",
        "nine", "9"
    );

    public Day01() {
        super("src/aoc2023/Day01/input.txt");
    }

    @Override
    public Object partOne(Stream<String> lines) {
        return lines.map(this::getDigitsFromLine).reduce(0, Integer::sum);
    }

    @Override
    public Object partTwo(Stream<String> lines) {
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
        return Integer.parseInt(getNumberFromLine(line, false) + getNumberFromLine(line, true));
    }

    String getNumberFromLine(String line, boolean reverse) {
        CharacterIterator it = new StringCharacterIterator(line);
        StringBuilder buffer = new StringBuilder();
        Character ch;
        if (reverse) {
            ch = it.last();
        } else {
            ch = it.first();
        }
        while (ch != CharacterIterator.DONE) {
            if (Character.isDigit(ch)) {
                return String.valueOf(ch);
            }
            if (reverse) {
                buffer.insert(0, ch);
            } else {
                buffer.append(ch);
            }
            Optional<String> result = findNumberWord(buffer);
            if (result.isPresent()) {
                return numberMap.get(result.get());
            }
            if (reverse) {
                ch = it.previous();
            } else {
                ch = it.next();
            }
        }
        return null;
    }

    Optional<String> findNumberWord(StringBuilder buffer) {
        String finalBuffer = buffer.toString();
        return numberMap.keySet().stream().filter(finalBuffer::contains).findFirst();
    }

}
