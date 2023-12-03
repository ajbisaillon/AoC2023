package aoc2023;

public class Cell {
    final int i;
    final int j;

    final String mValue;

    public Cell(int i, int j, String value) {
        this.i = i;
        this.j = j;
        mValue = value;
    }

    public String getValue() {
        return mValue;
    }

    @Override
    public String toString() {
        return mValue;
    }
}
