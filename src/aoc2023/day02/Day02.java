package aoc2023.day02;

import aoc2023.Day;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day02 extends Day {
    final int redMax = 12;
    final int greenMax = 13;
    final int blueMax = 14;

    public Day02() {
        super("src/aoc2023/Day02/input.txt");
    }

    @Override
    public Object partOne(Stream<String> lines) {
        return lines.map(this::checkGameValidity).reduce(0, Integer::sum);
    }

    @Override
    public Object partTwo(Stream<String> lines) {
        return null;
    }

    Integer checkGameValidity(String line) {
        String[] gameParts = line.split(":");

        final Pattern gameIdPattern = Pattern.compile("(\\d+)");
        final Matcher gameIdMatcher = gameIdPattern.matcher(gameParts[0]);

        if (!gameIdMatcher.find()) {
            return 0;
        }
        Integer gameId = Integer.parseInt(gameIdMatcher.group(1));

        String[] rounds = gameParts[1].split(";");

        final Pattern redPattern = Pattern.compile("(\\d+) red");
        final Pattern greenPattern = Pattern.compile("(\\d+) green");
        final Pattern bluePattern = Pattern.compile("(\\d+) blue");
        for (String round : rounds) {
            final Matcher redMatcher = redPattern.matcher(round);
            final Matcher greenMatcher = greenPattern.matcher(round);
            final Matcher blueMatcher = bluePattern.matcher(round);

            if (redMatcher.find()) {
                if (Integer.parseInt(redMatcher.group(1)) > redMax) {
                    return 0;
                }
            }
            if (greenMatcher.find()) {
                if (Integer.parseInt(greenMatcher.group(1)) > greenMax) {
                    return 0;
                }
            }
            if (blueMatcher.find()) {
                if (Integer.parseInt(blueMatcher.group(1)) > blueMax) {
                    return 0;
                }
            }
        }
        return gameId;
    }
}
