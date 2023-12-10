package aoc2023.day06;

import aoc2023.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day06 extends Day {
    public Day06() {
        super("src/aoc2023/Day06/input.txt");
    }

    @Override
    public Object partOne(Stream<String> lines) {
        int waysToWinProduct = 1;
        List<Race> boatRaces = new ArrayList<>();

        List<List<String>> raceNumbers = lines.map(line -> {
            String[] parts = line.split(":");
            return Arrays.stream(parts[1].split(" ")).toList();
        }).toList();

        List<Integer> times = raceNumbers.get(0)
                .stream().filter(time -> !time.isBlank())
                .map(Integer::parseInt).toList();
        List<Integer> distances = raceNumbers.get(1)
                .stream().filter(distance -> !distance.isBlank())
                .map(Integer::parseInt).toList();

        if (times.size() != distances.size()) {
            System.out.println("Problem with parsing input!");
            return null;
        }
        for (int i = 0; i < times.size(); i++) {
            boatRaces.add(new Race(times.get(i), distances.get(i)));
        }


        for (Race race : boatRaces) {
            int waysToWin = 0;
            int distanceToBeat = race.distance;
            int timeAllowed = race.time;

            for (int ms = 1; ms < timeAllowed; ms++) {
                int speed = ms;
                int distanceTraveled = (timeAllowed - ms) * speed;
                if (distanceTraveled > distanceToBeat) {
                    waysToWin++;
                }
            }
//            System.out.printf("Ways to win %s = %d\n", race, waysToWin);
            waysToWinProduct *= waysToWin;
        }
        return waysToWinProduct;
    }

    @Override
    public Object partTwo(Stream<String> lines) {
        final long startTime = System.currentTimeMillis();

        // total possibilities - ways to lose = ways to win
        // (more ways to win than lose in this case)
        long totalWaysToWin = 0;
        long totalWaysToLose = 0;

        List<List<String>> raceNumbers = lines.map(line -> {
            String[] parts = line.split(":");
            return Arrays.stream(parts[1].split(" ")).toList();
        }).toList();

        long time = Long.parseLong(raceNumbers.get(0)
                .stream().filter(timeDigits -> !timeDigits.isBlank())
                .reduce("", (accumulated, current) -> accumulated + current)
        );
        long distance = Long.parseLong(raceNumbers.get(1)
                .stream().filter(distanceDigits -> !distanceDigits.isBlank())
                .reduce("", (accumulated, current) -> accumulated + current)
        );

        long ms = 0;
        while ((time - ms) * ms <= distance) {
            totalWaysToLose++;
            ms++;
        }
        ms = time;
        while ((time - ms) * ms <= distance) {
            totalWaysToLose++;
            ms--;
        }

//        for (long i = 1; i < time; i++) {
//            if ((time - i) * i > distance) {
//                totalWaysToWin++;
//            }
//        }

        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime) + " milliseconds");
        return time - totalWaysToLose;
    }

    static class Race {
        Integer time;
        Integer distance;

        public Race(Integer time, Integer distance) {
            this.time = time;
            this.distance = distance;
        }

        @Override
        public String toString() {
            return "Race{" +
                    "time=" + time +
                    ", distance=" + distance +
                    '}';
        }
    }

}
