package aoc2023.day02;

import aoc2023.Day;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day02 extends Day {
    final int maxRed = 12;
    final int maxGreen = 13;
    final int maxBlue = 14;

    public Day02() {
        super("src/aoc2023/Day02/input.txt");
    }

    @Override
    public Object partOne(Stream<String> lines) {
        return lines.map(this::checkGameValidity).reduce(0, Integer::sum);
    }

    @Override
    public Object partTwo(Stream<String> lines) {
        return lines.map(this::calculateFewestCubes).reduce(0, Integer::sum);
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
                if (Integer.parseInt(redMatcher.group(1)) > maxRed) {
                    return 0;
                }
            }
            if (greenMatcher.find()) {
                if (Integer.parseInt(greenMatcher.group(1)) > maxGreen) {
                    return 0;
                }
            }
            if (blueMatcher.find()) {
                if (Integer.parseInt(blueMatcher.group(1)) > maxBlue) {
                    return 0;
                }
            }
        }
        return gameId;
    }

    // TODO optimize
    Integer calculateFewestCubes(String line) {
        int minRed = 0;
        int minGreen = 0;
        int minBlue = 0;

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
                int numRed = Integer.parseInt(redMatcher.group(1));
                if (numRed > minRed) {
                    minRed = numRed;
                }
            }
            if (greenMatcher.find()) {
                int numGreen = Integer.parseInt(greenMatcher.group(1));
                if (numGreen > minGreen) {
                    minGreen = numGreen;
                }
            }
            if (blueMatcher.find()) {
                int numBlue = Integer.parseInt(blueMatcher.group(1));
                if (numBlue > minBlue) {
                    minBlue = numBlue;
                }
            }
        }
        return minRed * minGreen * minBlue;
    }
}
