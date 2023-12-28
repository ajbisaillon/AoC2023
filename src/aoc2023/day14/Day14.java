package aoc2023.day14;

import aoc2023.Day;
import aoc2023.datastructures.Cell;
import aoc2023.datastructures.Grid;
import aoc2023.datastructures.Tuple3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class Day14 extends Day {
    enum Direction {
        NORTH,
        WEST,
        SOUTH,
        EAST
    }

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
                rollRocksInSection(grid, Direction.NORTH, j, section);
            }
        }

        return calculateTotalLoad(grid);
    }

    @Override
    public Object partTwo(Stream<String> lines) {
        List<String> allLines = lines.toList();

        var grid = new Grid<>();
        for (String line : allLines) {
            grid.addRow(Arrays.stream(line.split("")).toList());
        }

        var cycles = new HashMap<Integer, Integer>();
        Direction direction = Direction.NORTH;
        int i;
        i = 1;
        // Get the grid into the right state before skipping ahead
        while (i <= 452) {
            switch (direction) {
                case NORTH, SOUTH -> {
                    for (int j = 0; j < grid.getNumberOfColumns(); j++) {
                        List<Tuple3<Integer, Integer, Integer>> sections = getColumnSections(grid, j);
                        for (var section : sections) {
                            rollRocksInSection(grid, direction, j, section);
                        }
                    }
                }
                case EAST, WEST -> {
                    for (int j = 0; j < grid.getNumberOfRows(); j++) {
                        List<Tuple3<Integer, Integer, Integer>> sections = getRowSections(grid, j);
                        for (var section : sections) {
                            rollRocksInSection(grid, direction, j, section);
                        }
                    }
                }
            }

            if (direction == Direction.EAST) {
                if (!cycles.containsKey(grid.hashCode())) {
                    cycles.put(grid.hashCode(), i);
                } else {
                    System.out.printf("found cycle %s -> %s: %s\n", cycles.get(grid.hashCode()), i, calculateTotalLoad(grid));
                    cycles.put(grid.hashCode(), i);

                }
            }


            direction = Direction.values()[(direction.ordinal() + 1) % Direction.values().length];
            i++;
        }

        // Skip until a full repeated cycle cannot be completed
        i = 999999882;
        while (i <= 1000000000) {
            switch (direction) {
                case NORTH, SOUTH -> {
                    for (int j = 0; j < grid.getNumberOfColumns(); j++) {
                        List<Tuple3<Integer, Integer, Integer>> sections = getColumnSections(grid, j);
                        for (var section : sections) {
                            rollRocksInSection(grid, direction, j, section);
                        }
                    }
                }
                case EAST, WEST -> {
                    for (int j = 0; j < grid.getNumberOfRows(); j++) {
                        List<Tuple3<Integer, Integer, Integer>> sections = getRowSections(grid, j);
                        for (var section : sections) {
                            rollRocksInSection(grid, direction, j, section);
                        }
                    }
                }
            }

            System.out.println(calculateTotalLoad(grid));

            direction = Direction.values()[(direction.ordinal() + 1) % Direction.values().length];
            i++;
        }

        return calculateTotalLoad(grid);
    }

    // for a column, returns sections denoted by start and end row indices
    List<Tuple3<Integer, Integer, Integer>> getColumnSections(Grid<Object> grid, int colIndex) {
        List<Tuple3<Integer, Integer, Integer>> sections = new ArrayList<>();

        int start = 0;
        int end = 0;
        int rocks = 0;
        for (int i = 0; i < grid.getNumberOfRows(); i++) {
            String current = grid.getCell(i, colIndex).value;
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

    List<Tuple3<Integer, Integer, Integer>> getRowSections(Grid<Object> grid, int rowIndex) {
        List<Tuple3<Integer, Integer, Integer>> sections = new ArrayList<>();

        int start = 0;
        int end = 0;
        int rocks = 0;

        List<Cell<Object>> row = grid.getRow(rowIndex);

        for (Cell<Object> objectCell : row) {
            String current = objectCell.value;
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

    void rollRocksInSection(Grid<Object> grid, Direction direction, int colOrRowIndex, Tuple3<Integer, Integer, Integer> section) {
        switch (direction) {
            case NORTH -> {
                grid.fillColumnRange(colOrRowIndex, section.first, section.first + section.third, "O");
                grid.fillColumnRange(colOrRowIndex, section.first + section.third, section.second, ".");
            }
            case EAST -> {
                grid.fillRowRange(colOrRowIndex, section.first, section.second - section.third, ".");
                grid.fillRowRange(colOrRowIndex, section.second - section.third, section.second, "O");
            }
            case SOUTH -> {
                grid.fillColumnRange(colOrRowIndex, section.first, section.second - section.third, ".");
                grid.fillColumnRange(colOrRowIndex, section.second - section.third, section.second, "O");
            }
            case WEST -> {
                grid.fillRowRange(colOrRowIndex, section.first, section.first + section.third, "O");
                grid.fillRowRange(colOrRowIndex, section.first + section.third, section.second, ".");
            }
        }

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
