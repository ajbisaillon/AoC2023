package aoc2023.day13;

import aoc2023.Day;
import aoc2023.datastructures.Grid;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day13 extends Day {
    public Day13() {
        super("src/aoc2023/day13/input.txt");
    }

    @Override
    public Object partOne(Stream<String> lines) {
        List<String> allLines = lines.collect(Collectors.toList());
        allLines.add("");

        int totalColumns = 0;
        Grid<String> current = new Grid<>();
        for (String line : allLines) {
            if (line.isBlank()) {
                totalColumns += countSymmetry(current);
                current = new Grid<>();
            } else {
                current.addRow(Arrays.stream(line.split("")).toList());
            }
        }

        return totalColumns;
    }

    @Override
    public Object partTwo(Stream<String> lines) {
        List<String> allLines = lines.collect(Collectors.toList());
        allLines.add("");

        int totalColumns = 0;
        Grid<String> current = new Grid<>();
        for (String line : allLines) {
            if (line.isBlank()) {
                totalColumns += countSymmetry(current);
                current = new Grid<>();
            } else {
                current.addRow(Arrays.stream(line.split("")).toList());
            }
        }

        return totalColumns;
    }

    private int countSymmetry(Grid<String> grid) {
        int rowResult = countHorizontalReflectionRows(grid);
        int colResult = countVerticalReflectionColumns(grid);
        return rowResult + colResult;
    }

    int countHorizontalReflectionRows(Grid<String> grid) {
        int result = 0;
        for (int i = 0; i < grid.getNumberOfRows() - 1; i++) {
            int differences = grid.getNumberOfDifferencesInRow(i, i + 1);
            if (differences == 0 || differences == 1) {
                int start = i + 1;
                int top = i;
                int bottom = i + 1;

                boolean finished = false;
                boolean flippedFound = differences == 1;
                boolean areRowsEqual = true;
                while (!finished && areRowsEqual) {
                    top -= 1;
                    bottom += 1;

                    if (top < 0 || bottom > grid.getNumberOfRows() - 1) {
                        result = start;
                        finished = true;
                    } else {
                        areRowsEqual = grid.areRowValuesEqual(top, bottom);
                        if (!flippedFound && !areRowsEqual) {
                            areRowsEqual = grid.getNumberOfDifferencesInRow(top, bottom) == 1;
                            flippedFound = grid.getNumberOfDifferencesInRow(top, bottom) == 1;
                            System.out.printf("top: %s, bottom: %s are off by one\n", top, bottom);
                        }
                    }
                }
                if (finished && flippedFound) {
                    break;
                } else {
                    result = 0;
                }
            }
        }
        return result * 100;
    }

    int countVerticalReflectionColumns(Grid<String> grid) {
        int result = 0;
        for (int i = 0; i < grid.getNumberOfColumns() - 1; i++) {
            int differences = grid.getNumberOfDifferencesInColumn(i, i + 1);
            if (differences == 0 || differences == 1) {
                int start = i + 1; // need number of columns
                int left = i;
                int right = i + 1;

                boolean finished = false;
                boolean flippedFound = differences == 1;
                boolean areColumnsEqual = true;
                while (!finished && areColumnsEqual) {
                    left -= 1;
                    right += 1;

                    if (left < 0 || right > grid.getNumberOfColumns() - 1) {
                        result = start;
                        finished = true;
                    } else {
                        areColumnsEqual = grid.areColumnValuesEqual(left, right);
                        if (!flippedFound && !areColumnsEqual) {
                            areColumnsEqual = grid.getNumberOfDifferencesInColumn(left, right) == 1;
                            flippedFound = grid.getNumberOfDifferencesInColumn(left, right) == 1;
                        }
                    }
                }
                if (finished && flippedFound) {
                    break;
                } else {
                    result = 0;
                }
            }
        }
        return result;
    }
}
