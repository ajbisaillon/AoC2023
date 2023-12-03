package aoc2023;

import aoc2023.day01.Day01;
import aoc2023.day02.Day02;

import java.util.ArrayList;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) {
        new ArrayList<>(Arrays.asList(
//                new Day01(),
                new Day02()
        )).forEach(Day::solve);
    }
}
