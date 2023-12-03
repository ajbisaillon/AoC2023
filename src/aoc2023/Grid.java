package aoc2023;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * All rows are the same length
 */
public class Grid {

    final List<List<String>> grid;

    public Grid() {
        grid = new ArrayList<>();
    }

    public int getNumberOfRows() {
        return grid.size();
    }

    public int getNumberOfColumns() {
        try {
            return grid.get(0).size();
        } catch (IndexOutOfBoundsException e) {
            return 0;
        }
    }

    public void addRow(List<String> row) {
        grid.add(row);
    }

    public String getCell(Cell cell) {
        // TODO: Constrain to grid bounds
        return grid.get(cell.i).get(cell.j);
    }

    public Set<String> getNeighbors(Cell cell) {
        final Set<String> neighbors = new HashSet<>();
        for (int i = cell.i - 1; i <= cell.i + 1; i++) {
            for (int j = cell.j - 1; j <= cell.j + 1; j++) {
                if ((i < 0 || i >= grid.size())
                        || (j < 0 || j >= grid.get(0).size())
                        || (i == cell.i && j == cell.j)
                ) {
                    continue;
                }
                neighbors.add(grid.get(i).get(j));

            }
        }

        return neighbors;
    }

    public void print() {
        for (List<String> row : grid) {
            for (String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();

        }
    }
}
