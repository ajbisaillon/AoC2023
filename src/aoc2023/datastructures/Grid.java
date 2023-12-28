package aoc2023.datastructures;

import java.util.*;

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

    public void addRow(List<String> row, int insertAtIndex) {
        // insert the new row into the grid, then update i,j of cells in following rows

        List<Cell<T>> cells = new ArrayList<>();
        for (int j = 0; j < row.size(); j++) {
            cells.add(new Cell<>(insertAtIndex, j, row.get(j)));
        }
        grid.add(insertAtIndex, cells);

        for (int i = insertAtIndex + 1; i < this.getNumberOfRows(); i++) {
            for (int j = 0; j < this.getNumberOfColumns(); j++) {
                Cell cell = this.getCell(i, j);
                cell.i = cell.i + 1;
            }
        }
    }

    public void addRowAndFill(int insertAtIndex, String fillWith) {
        List<Cell<T>> cells = new ArrayList<>();
        for (int j = 0; j < this.getNumberOfColumns(); j++) {
            cells.add(new Cell<>(insertAtIndex, j, fillWith));
        }
        grid.add(insertAtIndex, cells);

        for (int i = insertAtIndex + 1; i < this.getNumberOfRows(); i++) {
            for (int j = 0; j < this.getNumberOfColumns(); j++) {
                Cell<T> cell = this.getCell(i, j);
                cell.i = cell.i + 1;
            }
        }
    }

    public void addColumn(List<String> column, int insertAtIndex) {
        for (int i = 0; i < this.getNumberOfColumns(); i++) {
            grid.get(i).add(insertAtIndex, new Cell<>(i, insertAtIndex, column.get(i)));
        }

        for (int i = 0; i < this.getNumberOfRows(); i++) {
            for (int j = insertAtIndex + 1; j < this.getNumberOfColumns(); j++) {
                Cell<T> cell = this.getCell(i, j);
                cell.j = cell.j + 1;
            }
        }
    }

    public void addColumnAndFill(int insertAtIndex, String fillWith) {
        for (int i = 0; i < this.getNumberOfRows(); i++) {
            grid.get(i).add(insertAtIndex, new Cell<>(i, insertAtIndex, fillWith));
        }

        for (int i = 0; i < this.getNumberOfRows(); i++) {
            for (int j = insertAtIndex + 1; j < this.getNumberOfColumns(); j++) {
                Cell<T> cell = this.getCell(i, j);
                cell.j = cell.j + 1;
            }
        }
    }

    public void fillRowRange(int rowIndex, int colStart, int colEnd, String fillWith) {
        List<Cell<T>> row = getRow(rowIndex);
        for (int i = colStart; i < colEnd; i++) {
            Cell<T> cell = row.get(i);
            cell.value = fillWith;
        }
    }

    public void fillColumnRange(int colIndex, int rowStart, int rowEnd, String fillWith) {
        for (int i = rowStart; i < rowEnd; i++) {
            Cell<T> cell = this.getCell(i, colIndex);
            cell.value = fillWith;
        }
    }

    public List<Cell<T>> getRow(int index) {
        return grid.get(index);
    }

    public List<Cell<T>> getColumn(int index) {
        final List<Cell<T>> column = new ArrayList<>();
        for (List<Cell<T>> row : grid) {
            column.add(row.get(index));
        }
        return column;
    }

    public boolean areRowValuesEqual(int first, int second) {
        List<Cell<T>> firstRow = getRow(first);
        List<Cell<T>> secondRow = getRow(second);

        if (firstRow.size() != secondRow.size()) return false;

        for (int i = 0; i < firstRow.size(); i++) {
            if (!firstRow.get(i).value.equals(secondRow.get(i).value)) return false;
        }

        return true;
    }

    public boolean areColumnValuesEqual(int first, int second) {
        List<Cell<T>> firstColumn = getColumn(first);
        List<Cell<T>> secondColumn = getColumn(second);

        if (firstColumn.size() != secondColumn.size()) return false;

        for (int i = 0; i < firstColumn.size(); i++) {
            if (!firstColumn.get(i).value.equals(secondColumn.get(i).value)) return false;
        }

        return true;
    }

    public Integer getNumberOfDifferencesInRow(int first, int second) {
        int differences = 0;
        List<Cell<T>> firstRow = getRow(first);
        List<Cell<T>> secondRow = getRow(second);

        if (firstRow.size() != secondRow.size()) return null;

        for (int i = 0; i < firstRow.size(); i++) {
            if (!firstRow.get(i).value.equals(secondRow.get(i).value)) differences++;
        }

        return differences;
    }

    public Integer getNumberOfDifferencesInColumn(int first, int second) {
        int differences = 0;
        List<Cell<T>> firstColumn = getColumn(first);
        List<Cell<T>> secondColumn = getColumn(second);

        if (firstColumn.size() != secondColumn.size()) return null;

        for (int i = 0; i < firstColumn.size(); i++) {
            if (!firstColumn.get(i).value.equals(secondColumn.get(i).value)) differences++;
        }

        return differences;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Grid<?> grid1)) return false;
        return grid.equals(grid1.grid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grid);
    }
}
