package aoc2023.day14;

import aoc2023.Day;
import aoc2023.datastructures.Grid;
import aoc2023.datastructures.Tuple3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day14 extends Day {
    public Day14() {
        super("src/aoc2023/day14/input.txt");
    }

    @Override
    public Object partOne(Stream<String> lines) {
        List<String> allLines = lines.toList();

        Grid<Object> grid = new Grid<>();
        for (String line : allLines) {
                grid.addRow(Arrays.stream(line.split("")).toList());
        }

        for (int j = 0; j < grid.getNumberOfColumns(); j++) {
            List<Tuple3<Integer, Integer, Integer>> sections = getColumnSections(grid, j);
            for (Tuple3<Integer, Integer, Integer> section : sections) {
                rollRocksInSection(grid, j, section);
            }
        }

        return calculateTotalLoad(grid);
    }

    @Override
    public Object partTwo(Stream<String> lines) {
        return null;
    }

    // for a column, returns sections denoted by start and end row indices
    List<Tuple3<Integer, Integer, Integer>> getColumnSections(Grid<Object> grid, int colIndex) {
        List<Tuple3<Integer, Integer, Integer>> sections = new ArrayList<>();

        int start = 0;
        int end = 0;
        int rocks = 0;
        for (int i = 0; i < grid.getNumberOfRows(); i++) {
            String current = grid.getCell(i, colIndex).value;
            System.out.println(current);
            if (current.equals("O")) {
                rocks++;
                end++;
            } else if (current.equals(".")) {
                end++;
            } else {
                // must be '#'
                if (start < end) {
                    sections.add(new Tuple3<>(start, end, rocks));
                }
                end++;
                start = end;
                rocks = 0;
            }
        }
        if (start < end) {
            sections.add(new Tuple3<>(start, end, rocks));
        }
        return sections;
    }

    void rollRocksInSection(Grid<Object> grid, int colIndex, Tuple3<Integer, Integer, Integer> columnSection) {
        grid.fillColumnRange(colIndex, columnSection.first, columnSection.first + columnSection.third, "O");
        grid.fillColumnRange(colIndex, columnSection.first + columnSection.third, columnSection.second, ".");
    }

    int calculateTotalLoad(Grid<Object> grid) {
        int total = 0;
        for (int i = 0; i < grid.getNumberOfRows(); i++) {
            int loadFactor = grid.getNumberOfRows() - i;
            int numOfRocks = grid.getRow(i).stream().mapToInt(cell -> cell.value.equals("O") ? 1 : 0).sum();
            total += loadFactor * numOfRocks;
        }
        return total;
    }

}
