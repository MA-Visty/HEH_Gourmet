package be.heh.gourmet.utils;

import jakarta.validation.constraints.NotNull;

public final class Pair<F, S> {
    @NotNull
    private F first;
    @NotNull
    private S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public static <F, S> Pair<F, S> of(F first, S second) {
        return new Pair<>(first, second);
    }

    public F first() {
        return first;
    }

    public void setFirst(@NotNull F first) {
        this.first = first;
    }

    public S second() {
        return second;
    }

    public void setSecond(@NotNull S second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "Pair[" + "first=" + first + ", " + "second=" + second + ']';
    }
}
