package aoc2023.datastructures;

import java.util.Objects;

public class Tuple3<X, Y, Z> {

    public X first;
    public Y second;

    public Z third;

    public Tuple3(X first, Y second, Z third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tuple3<?, ?, ?> tuple3)) return false;
        return Objects.equals(first, tuple3.first) && Objects.equals(second, tuple3.second) && Objects.equals(third, tuple3.third);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second, third);
    }
}
