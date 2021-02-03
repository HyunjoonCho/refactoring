package org.hyunjoon.refactoring.model;

import java.util.Objects;

public class Play {
    private final String name;
    private final Type type;

    public Play(String name, Type type) {
        this.name = Objects.requireNonNull(name, "name");
        this.type = Objects.requireNonNull(type, "type");
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }
}
