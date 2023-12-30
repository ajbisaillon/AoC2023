package aoc2023.day15;

import aoc2023.Day;

import java.util.*;
import java.util.stream.Stream;

public class Day15 extends Day {
    public Day15() {
        super("src/aoc2023/day15/input.txt");
    }

    @Override
    public Object partOne(Stream<String> lines) {
        // input is assumed to be nonempty
        return Arrays.stream(lines.findFirst().get().split(",")).map(step -> {
            int current = 0;
            for (int ch : step.toCharArray()) {
                current += ch;
                current *= 17;
                current %= 256;
            }
            return current;
        }).reduce(0, Integer::sum);
    }

    @Override
    public Object partTwo(Stream<String> lines) {
        final List<List<String>> boxes = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            boxes.add(new ArrayList<>());
        }

        Arrays.stream(lines.findFirst().get().split(",")).forEach(step -> {
            String label = "";
            String focalLength = null;
            if (step.contains("=")) {
                String[] parts = step.split("=");
                label = parts[0];
                focalLength = parts[1];
            } else if (step.contains("-")) {
                String[] parts = step.split("-");
                label = parts[0];
            }

            int boxNumber = 0;
            for (int ch : label.toCharArray()) {
                boxNumber += ch;
                boxNumber *= 17;
                boxNumber %= 256;
            }

            List<String> box = boxes.get(boxNumber);
            String finalLabel = label;
            Optional<String> found = box.stream().filter(lensLabel -> lensLabel.startsWith(finalLabel)).findFirst();
            if (focalLength != null) {
                if (found.isPresent()) {
                    int index = box.indexOf(found.get());
                    box.set(index, String.format("%s %s", label, focalLength));
                } else {
                    box.add(String.format("%s %s", label, focalLength));
                }
            } else {
                found.ifPresent(box::remove);
            }
        });


        int result = 0;
        for (int i = 0; i < boxes.size(); i++) {
            List<String> box = boxes.get(i);
            for (int j = 0; j < box.size(); j++) {
                String lens = box.get(j);
                int fl = Integer.parseInt(lens.substring(lens.length() - 1));
                result += (i + 1) * (j + 1) * fl;
            }
        }
        return result;
    }
}
