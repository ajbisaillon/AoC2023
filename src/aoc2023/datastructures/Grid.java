package aoc2023.datastructures;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * All rows are the same length
 */
public class Grid<T> {

    final List<List<Cell<T>>> grid;

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
        int nextRow = getNumberOfRows();
        List<Cell<T>> cells = new ArrayList<>();
        for (int j = 0; j < row.size(); j++) {
            cells.add(new Cell<>(nextRow, j, row.get(j)));
        }

        grid.add(cells);

    }

    public List<Cell<T>> getRow(int index) {
        return grid.get(index);
    }

    public Cell<T> findFirst(String value) {
        for (List<Cell<T>> rows : grid) {
            for (Cell<T> cell : rows) {
                if (cell.value.equals(value)) return cell;
            }
        }
        return null;
    }

    public Cell<T> getCell(int row, int col) {
        if ((row < 0 || row > getNumberOfRows()) || (col < 0 || col > getNumberOfColumns())) return null;
        return grid.get(row).get(col);
    }

    public Set<String> getNeighbors(Cell<T> cell) {
        final Set<String> neighbors = new HashSet<>();
        for (int i = cell.i - 1; i <= cell.i + 1; i++) {
            for (int j = cell.j - 1; j <= cell.j + 1; j++) {
                if ((i < 0 || i >= grid.size())
                        || (j < 0 || j >= grid.get(0).size())
                        || (i == cell.i && j == cell.j)
                ) {
                    continue;
                }
                neighbors.add(grid.get(i).get(j).value);

            }
        }

        return neighbors;
    }

    public void print() {
        for (List<Cell<T>> row : grid) {
            for (Cell<T> cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();

        }
    }
}
