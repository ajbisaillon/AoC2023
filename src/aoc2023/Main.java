package aoc2023;

import aoc2023.day01.Day01;
import aoc2023.day02.Day02;
import aoc2023.day03.Day03;
import aoc2023.day04.Day04;
import aoc2023.day05.Day05;
import aoc2023.day06.Day06;
import aoc2023.day07.Day07;
import aoc2023.day08.Day08;
import aoc2023.day09.Day09;
import aoc2023.day10.Day10;
import aoc2023.day11.Day11;

import java.util.ArrayList;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) {
        new ArrayList<>(Arrays.asList(
//                new Day01(),
//                new Day02(),
//                new Day03()
//                new Day04(),
//                new Day05(),
//                new Day06(),
//                new Day07(),
//                new Day08(),
//                new Day09(),
//                new Day10(),
                new Day11()
        )).forEach(Day::solve);
    }
}
