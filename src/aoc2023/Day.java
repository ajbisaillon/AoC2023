package aoc2023;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

public abstract class Day {

    final String inputFilePath;
    public final File inputFile;

    protected Day(String inputFilePath) {
        this.inputFilePath = inputFilePath;
        this.inputFile = new File(inputFilePath);
    }

    public void solve() {
        System.out.printf("-- %s --%n", this.getClass().getSimpleName());
        System.out.printf("Part One Solution: %s%n", partOne(getLinesStream()));
        System.out.printf("Part Two Solution: %s%n", partTwo(getLinesStream()));
        System.out.println();
    }

    abstract public Object partOne(Stream<String> lines);

    abstract public Object partTwo(Stream<String> lines);

    public Stream<String> getLinesStream() {
        try {
            return Files.lines(inputFile.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
