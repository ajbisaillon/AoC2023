package aoc2023.datastructures;

import java.util.Objects;

public class Tuple2<X, Y> {

    public X first;
    public Y second;

    public Tuple2(X first, Y second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tuple2<?, ?> tuple2)) return false;
        return Objects.equals(first, tuple2.first) && Objects.equals(second, tuple2.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
