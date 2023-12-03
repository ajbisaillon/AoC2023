package aoc2023.day03;

import aoc2023.Cell;
import aoc2023.Day;
import aoc2023.Grid;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Day03 extends Day {
    final Set<String> specialCharacters = Set.of("@", "#", "$", "%", "&", "*", "-", "+", "=", "/");

    public Day03() {
        super("src/aoc2023/Day03/input.txt");
    }

    @Override
    public Object partOne(Stream<String> lines) {
        final Grid grid = new Grid();
        lines.forEach(line -> grid.addRow(Arrays.stream(line.split("")).toList()));

        int total = 0;
        for (int i = 0; i < grid.getNumberOfRows(); i++) {
            StringBuilder numberBuffer = new StringBuilder();
            boolean symbolFound = false;
            for (int j = 0; j < grid.getNumberOfColumns(); j++) {
                final Cell cell = new Cell(i, j);
                String cellContent = grid.getCell(cell);

                if (Character.isDigit(cellContent.charAt(0))) {
                    numberBuffer.append(cellContent);
                    Set<String> neighbors = grid.getNeighbors(cell);
                    neighbors.retainAll(specialCharacters);
                    if (!neighbors.isEmpty()) {
                        symbolFound = true;
                    }

                    if (j == grid.getNumberOfColumns() - 1) {
                        int partNumber = Integer.parseInt(numberBuffer.toString());
                        if (symbolFound) {
                            total += partNumber;
                            symbolFound = false;
                        }
                    }
                } else if (!Character.isDigit(cellContent.charAt(0)) && !numberBuffer.isEmpty()) {
                    int partNumber = Integer.parseInt(numberBuffer.toString());
                    if (symbolFound) {
                        total += partNumber;
                        symbolFound = false;
                    }
                    numberBuffer = new StringBuilder();
                }
            }
        }

        return total;
    }

    @Override
    public Object partTwo(Stream<String> lines) {
        return null;
    }
}
