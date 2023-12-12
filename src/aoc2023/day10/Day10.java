package aoc2023.day10;

import aoc2023.Day;
import aoc2023.datastructures.Cell;
import aoc2023.datastructures.Grid;

import java.util.Arrays;
import java.util.stream.Stream;

public class Day10 extends Day {
    public Day10() {
        super("src/aoc2023/Day10/input.txt");
    }

    @Override
    public Object partOne(Stream<String> lines) {
        Grid<Integer> grid = new Grid<>();

        lines.map(line -> Arrays.stream(line.split("")).toList()).forEach(grid::addRow);

        Cell<Integer> start = grid.findFirst("S");

        Cell<Integer> north = grid.getCell(start.i - 1, start.j);
        Cell<Integer> east = grid.getCell(start.i, start.j + 1);
        Cell<Integer> south = grid.getCell(start.i + 1, start.j);
        Cell<Integer> west = grid.getCell(start.i, start.j - 1);

        Pipe pathOneStart = null;
        Pipe pathTwoStart = null;

        if (north != null) {
            switch (north.value) {
                case "|" -> pathOneStart = new Pipe(north, Direction.NORTH);
                case "7" -> pathOneStart = new Pipe(north, Direction.WEST);
                case "F" -> pathOneStart = new Pipe(north, Direction.EAST);
                default -> {
                }
            }
        }


        if (east != null) {
            switch (east.value) {
                case "-" -> {
                    Pipe pipe = new Pipe(east, Direction.EAST);
                    if (pathOneStart == null) {
                        pathOneStart = pipe;
                    } else {
                        pathTwoStart = pipe;
                    }
                }
                case "7" -> {
                    Pipe pipe = new Pipe(east, Direction.SOUTH);
                    if (pathOneStart == null) {
                        pathOneStart = pipe;
                    } else {
                        pathTwoStart = pipe;
                    }
                }
                case "J" -> {
                    Pipe pipe = new Pipe(east, Direction.NORTH);
                    if (pathOneStart == null) {
                        pathOneStart = pipe;
                    } else {
                        pathTwoStart = pipe;
                    }
                }
            }
        }

        if (south != null) {
            switch (south.value) {
                case "|" -> {
                    Pipe pipe = new Pipe(south, Direction.SOUTH);
                    if (pathOneStart == null) {
                        pathOneStart = pipe;
                    } else {
                        pathTwoStart = pipe;
                    }
                }
                case "J" -> {
                    Pipe pipe = new Pipe(south, Direction.WEST);
                    if (pathOneStart == null) {
                        pathOneStart = pipe;
                    } else {
                        pathTwoStart = pipe;
                    }
                }
                case "L" -> {
                    Pipe pipe = new Pipe(south, Direction.EAST);
                    if (pathOneStart == null) {
                        pathOneStart = pipe;
                    } else {
                        pathTwoStart = pipe;
                    }
                }
            }
        }

        if (west != null) {
            switch (west.value) {
                case "-" -> {
                    Pipe pipe = new Pipe(west, Direction.WEST);
                    if (pathOneStart == null) {
                        pathOneStart = pipe;
                    } else {
                        pathTwoStart = pipe;
                    }
                }
                case "F" -> {
                    Pipe pipe = new Pipe(west, Direction.SOUTH);
                    if (pathOneStart == null) {
                        pathOneStart = pipe;
                    } else {
                        pathTwoStart = pipe;
                    }
                }
                case "L" -> {
                    Pipe pipe = new Pipe(west, Direction.NORTH);
                    if (pathOneStart == null) {
                        pathOneStart = pipe;
                    } else {
                        pathTwoStart = pipe;
                    }
                }
            }
        }

        if (pathOneStart == null || pathTwoStart == null) {
            return null;
        }

        pathOneStart.cell.extra = 1;
        pathTwoStart.cell.extra = 1;

        Pipe pathOneCurrent = pathOneStart;
        Pipe pathTwoCurrent = pathTwoStart;

        Pipe pathOneNext = getNext(grid,pathOneCurrent);
        Pipe pathTwoNext = getNext(grid, pathTwoCurrent);

        if (pathOneNext == null || pathTwoNext == null) {
            return null;
        }


        while (pathOneNext.cell.extra == null || pathTwoNext.cell.extra == null) {
            if (pathOneNext.cell.extra == null) {
                pathOneNext.cell.extra = pathOneCurrent.cell.extra + 1;
                pathOneCurrent = pathOneNext;
                pathOneNext = getNext(grid, pathOneNext);

            }

            if (pathTwoNext.cell.extra == null) {
                pathTwoNext.cell.extra = pathTwoCurrent.cell.extra + 1;
                pathTwoCurrent = pathTwoNext;
                pathTwoNext = getNext(grid, pathTwoNext);
            }

        }

        return Math.max(pathOneNext.cell.extra, pathTwoNext.cell.extra);
    }


    @Override
    public Object partTwo(Stream<String> lines) {
        return null;
    }

    private Pipe getNext(Grid<Integer> grid, Pipe pipe) {
        Cell<Integer> cell = null;
        switch (pipe.outputDirection) {
            case NORTH -> {
                cell = grid.getCell(pipe.cell.i - 1, pipe.cell.j);
                return switch (cell.value) {
                    case "|" ->  new Pipe(cell, Direction.NORTH);
                    case "7" -> new Pipe(cell, Direction.WEST);
                    case "F" -> new Pipe(cell, Direction.EAST);
                    default -> null;
                };
            }
            case EAST -> {
                cell = grid.getCell(pipe.cell.i, pipe.cell.j + 1);
                return switch (cell.value) {
                    case "-" -> new Pipe(cell, Direction.EAST);
                    case "7" -> new Pipe(cell, Direction.SOUTH);
                    case "J" -> new Pipe(cell, Direction.NORTH);
                    default -> null;
                };
            }
            case SOUTH -> {
                cell = grid.getCell(pipe.cell.i + 1, pipe.cell.j);
                return switch (cell.value) {
                    case "|" -> new Pipe(cell, Direction.SOUTH);
                    case "J" -> new Pipe(cell, Direction.WEST);
                    case "L" -> new Pipe(cell, Direction.EAST);
                    default -> null;
                };
            }
            case WEST -> {
                cell = grid.getCell(pipe.cell.i, pipe.cell.j - 1);
                return switch (cell.value) {
                    case "-" -> new Pipe(cell, Direction.WEST);
                    case "F" -> new Pipe(cell, Direction.SOUTH);
                    case "L" -> new Pipe(cell, Direction.NORTH);
                    default -> null;
                };
            }
        }

        return null;
    }


    enum Direction {
        NORTH, EAST, SOUTH, WEST;
    }


    class Pipe {
        final Cell<Integer> cell;
        final Direction outputDirection;

        public Pipe(Cell<Integer> cell, Direction outputDirection) {
            this.cell = cell;
            this.outputDirection = outputDirection;
        }

        @Override
        public String toString() {
            return "Pipe{" +
                    "cell=" + cell +
                    ", outputDirection=" + outputDirection +
                    '}';
        }
    }
}
