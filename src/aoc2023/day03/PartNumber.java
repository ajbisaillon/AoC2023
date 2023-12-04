package aoc2023.day03;

import aoc2023.Cell;

public class PartNumber {
    final public int mValue;
    final public Cell mStart;
    final public Cell mEnd;

    public PartNumber(int value, Cell start, Cell end) {
        mValue = value;
        mStart = start;
        mEnd = end;
    }

    @Override
    public String toString() {
        return "PartNumber{" + mValue + "}";
    }
}
