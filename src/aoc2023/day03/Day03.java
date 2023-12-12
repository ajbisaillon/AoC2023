package aoc2023.day03;

import aoc2023.datastructures.Cell;
import aoc2023.Day;
import aoc2023.datastructures.Grid;

import java.util.ArrayList;
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
                Cell cell = grid.getCell(i, j);
                String cellContent = cell.value;

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
        final Grid grid = new Grid();
        List<Cell> starCells = new ArrayList<>();
        List<PartNumber> partNumbers = new ArrayList<>();
        lines.forEach(line -> grid.addRow(Arrays.stream(line.split("")).toList()));
        for (int i = 0; i < grid.getNumberOfRows(); i++) {
            StringBuilder buffer = new StringBuilder();
            Cell start = null;
            Cell end = null;
            for (int j = 0; j < grid.getNumberOfColumns(); j++) {
                final Cell cell = grid.getCell(i, j);
                final String cellContent = cell.value;
                boolean isDigit = Character.isDigit(cellContent.charAt(0));
                if (isDigit) {
                    buffer.append(cellContent);
                    if (start == null) {
                        start = new Cell(i, j, cellContent);
                    }
                    if (j == grid.getNumberOfColumns() - 1) {
                        end = new Cell(i, j, cellContent);
                        partNumbers.add(new PartNumber(Integer.parseInt(buffer.toString()), start, end));
                        buffer = new StringBuilder();
                        start = null;
                    }
                } else {
                    if (cellContent.equals("*")) {
                        starCells.add(new Cell(i, j, "*"));
                    }
                    if (!buffer.isEmpty()) {
                        if (start == null) {
                            start = new Cell(i, j, cellContent);
                        }
                        end = new Cell(i, j - 1, cellContent);
                        partNumbers.add(new PartNumber(Integer.parseInt(buffer.toString()), start, end));
                        buffer = new StringBuilder();
                        start = null;
                    }
                }
            }
        }
        return starCells.stream().reduce(0, (total, starCell) -> {
            List<PartNumber> result = getAdjacentPartNumbers(starCell, partNumbers);
            if (result.size() == 2) {
                return total + result.get(0).mValue * result.get(1).mValue;
            }
            return total;
        }, Integer::sum);
    }

    public List<PartNumber> getAdjacentPartNumbers(Cell starCell, List<PartNumber> partNumbers) {
        List<PartNumber> result = new ArrayList<>();
        partNumbers.forEach(partNumber -> {
            for (int i = starCell.i - 1; i <= starCell.i + 1; i++) {
                for (int j = starCell.j - 1; j <= starCell.j + 1; j++) {
                    if (i >= partNumber.mStart.i
                            && i <= partNumber.mEnd.i
                            && j >= partNumber.mStart.j
                            && j <= partNumber.mEnd.j) {
                        result.add(partNumber);
                        return;
                    }
                }
            }
        });
        return result;
    }
}
