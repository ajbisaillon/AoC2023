package aoc2023.day11;

import aoc2023.Day;
import aoc2023.datastructures.Cell;
import aoc2023.datastructures.Grid;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day11 extends Day {
    public Day11() {
        super("src/aoc2023/Day11/input.txt");
    }

    @Override
    public Object partOne(Stream<String> lines) {
        return null;
        /*
        Grid<Object> grid = new Grid<>();
        lines.map(line -> line.split("")).toList().forEach(charList -> grid.addRow(List.of(charList)));

//        grid.print();
//        System.out.println();
//        grid.addRow(newRow, 3);
//        grid.print();
//        System.out.println();
//        grid.addRowAndFill(3, "-");
//        grid.print();
//        System.out.println();
//        grid.addColumnAndFill(2, "*");
//        grid.print();
//        System.out.println();

        duplicateEmptyRows(grid, 1);
        duplicateEmptyColumns(grid, 1);
//        grid.print();
//        System.out.println();

        List<Cell<Object>> galaxies = new ArrayList<>();
        for (int i = 0; i < grid.getNumberOfRows(); i++) {
            for (int j = 0; j < grid.getNumberOfColumns(); j++) {
                Cell<Object> cell = grid.getCell(i, j);
                if (cell.value.equals("#")) {
                    galaxies.add(cell);
                }
            }
        }

        int totalOfMinDistances = 0;
        for (int h = 0; h < galaxies.size(); h++) {
            for (int k = h + 1; k < galaxies.size(); k++) {
                Cell<Object> galaxyOne = galaxies.get(h);
                Cell<Object> galaxyTwo = galaxies.get(k);
                int minDistance = Math.abs(galaxyOne.i - galaxyTwo.i) + Math.abs(galaxyOne.j - galaxyTwo.j);
                totalOfMinDistances += minDistance;
            }
        }

        return totalOfMinDistances;
        */
    }

    @Override
    public Object partTwo(Stream<String> lines) {
        final long startTime = System.currentTimeMillis();

        Grid<Object> grid1 = new Grid<>();
        Grid<Object> grid2 = new Grid<>();
        lines.map(line -> line.split("")).toList().forEach(charList -> {
            grid1.addRow(List.of(charList));
            grid2.addRow(List.of(charList));
        });

        int x1 = 5;
        int y1 = 0;
        int x2 = 10;
        int y2 = 0;
        expandEmptyRowsNTimes(grid1, x1);
        expandEmptyColumnsNTimes(grid1, x1);

        List<Cell<Object>> galaxies1 = new ArrayList<>();
        for (int i = 0; i < grid1.getNumberOfRows(); i++) {
            for (int j = 0; j < grid1.getNumberOfColumns(); j++) {
                Cell<Object> cell = grid1.getCell(i, j);
                if (cell.value.equals("#")) {
                    galaxies1.add(cell);
                }
            }
        }

        for (int h = 0; h < galaxies1.size(); h++) {
            for (int k = h + 1; k < galaxies1.size(); k++) {
                Cell<Object> galaxyOne = galaxies1.get(h);
                Cell<Object> galaxyTwo = galaxies1.get(k);
                int minDistance = Math.abs(galaxyOne.i - galaxyTwo.i) + Math.abs(galaxyOne.j - galaxyTwo.j);
                y1 += minDistance;
            }
        }

        expandEmptyRowsNTimes(grid2, x2);
        expandEmptyColumnsNTimes(grid2, x2);

        List<Cell<Object>> galaxies2 = new ArrayList<>();
        for (int i = 0; i < grid2.getNumberOfRows(); i++) {
            for (int j = 0; j < grid2.getNumberOfColumns(); j++) {
                Cell<Object> cell = grid2.getCell(i, j);
                if (cell.value.equals("#")) {
                    galaxies2.add(cell);
                }
            }
        }

        for (int h = 0; h < galaxies2.size(); h++) {
            for (int k = h + 1; k < galaxies2.size(); k++) {
                Cell<Object> galaxyOne = galaxies2.get(h);
                Cell<Object> galaxyTwo = galaxies2.get(k);
                int minDistance = Math.abs(galaxyOne.i - galaxyTwo.i) + Math.abs(galaxyOne.j - galaxyTwo.j);
                y2 += minDistance;
            }
        }

        final long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime) + " milliseconds");


        long expansionFactor = 1000000;
        long slope = (y2 - y1) / (x2 - x1);
        return slope * (expansionFactor - x1) + y1;
    }

    void expandEmptyRowsNTimes(Grid<Object> grid, int n) {
        for (int i = grid.getNumberOfRows() - 1; i >= 0; i--) {
            boolean empty = true;
            for (int j = 0; j < grid.getNumberOfColumns(); j++) {
                if (!grid.getCell(i, j).value.equals(".")) {
                    empty = false;
                    break;
                }
            }
            if (empty) {
                for (int d = 1; d < n; d++) {
                    grid.addRowAndFill(i, ".");
                }
            }

        }
    }

    void expandEmptyColumnsNTimes(Grid<Object> grid, int n) {
        for (int j = grid.getNumberOfColumns() - 1; j >= 0; j--) {
            boolean empty = true;
            for (int i = 0; i < grid.getNumberOfRows(); i++) {
                if (!grid.getCell(i, j).value.equals(".")) {
                    empty = false;
                    break;
                }
            }
            if (empty) {
                for (int d = 1; d < n; d++) {
                    grid.addColumnAndFill(j, ".");
                }
            }

        }
    }
}
