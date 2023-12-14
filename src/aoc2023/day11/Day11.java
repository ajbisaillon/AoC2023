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

        duplicateEmptyRows(grid);
        duplicateEmptyColumns(grid);
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
    }

    @Override
    public Object partTwo(Stream<String> lines) {
        return null;
    }

    void duplicateEmptyRows(Grid<Object> grid) {
        for (int i = grid.getNumberOfRows() - 1; i >= 0; i--) {
            boolean empty = true;
            for (int j = 0; j < grid.getNumberOfColumns(); j++) {
                if (!grid.getCell(i, j).value.equals(".")) {
                    empty = false;
                    break;
                }
            }
            if (empty) {
                grid.addRowAndFill(i, ".");
            }

        }
    }

    void duplicateEmptyColumns(Grid<Object> grid) {
        for (int j = grid.getNumberOfColumns() - 1; j >= 0; j--) {
            boolean empty = true;
            for (int i = 0; i < grid.getNumberOfRows(); i++) {
                if (!grid.getCell(i, j).value.equals(".")) {
                    empty = false;
                    break;
                }
            }
            if (empty) {
                grid.addColumnAndFill(j, ".");
            }

        }
    }
}
