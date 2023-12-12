package aoc2023.datastructures;

import java.util.Objects;

public class Cell<T> {
    final public int i;
    final public int j;

    final public String value;

    public T extra;

    public Cell(int i, int j, String value) {
        this(i, j, value, null);
    }

    public Cell(int i, int j, String value, T extra) {
        this.i = i;
        this.j = j;
        this.value = value;
        this.extra = extra;
    }

    @Override
    public String toString() {
        return value + "@(" + i + "," + j + ") [" + extra + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell<?> cell)) return false;
        return i == cell.i && j == cell.j && value.equals(cell.value) && Objects.equals(extra, cell.extra);
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j, value, extra);
    }
}
