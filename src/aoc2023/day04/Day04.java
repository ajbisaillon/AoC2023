package aoc2023.day04;

import aoc2023.Day;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        final Pattern cardPattern = Pattern.compile("Card\\s*(\\d+):(.*)\\|(.*)");
        Map<Integer, Integer> cards = new HashMap<>();
        lines.forEach(line -> {
            final Matcher cardMatcher = cardPattern.matcher(line);
            if (!cardMatcher.find()) {
                return;
            }
            int cardNumber = Integer.parseInt(cardMatcher.group(1));
            Set<String> winningNumbers = Arrays
                    .stream(cardMatcher.group(2).split(" "))
                    .filter(x -> !x.isEmpty())
                    .collect(Collectors.toSet());
            Set<String> gameNumbers = Arrays
                    .stream(cardMatcher.group(3).split(" "))
                    .filter(x -> !x.isEmpty())
                    .collect(Collectors.toSet());
            gameNumbers.retainAll(winningNumbers);
            if (cards.containsKey(cardNumber)) {
                cards.put(cardNumber, cards.get(cardNumber) + 1);
            } else {
                cards.put(cardNumber, 1);
            }
            for (int i = 1; i <= gameNumbers.size(); i++) {
                cards.put(cardNumber + i, cards.getOrDefault(cardNumber + i, 0) + cards.get(cardNumber));
            }
        });
        return cards.values().stream().reduce(0, Integer::sum);
    }
}
