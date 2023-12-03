package aoc2023.day03;

import aoc2023.Cell;

public class PartNumber {
    final int mValue;
    final Cell mStart;
    final Cell mEnd;

    public PartNumber(int value, Cell start, Cell end) {
        mValue = value;
        mStart = start;
        mEnd = end;
    }
}
