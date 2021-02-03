package org.hyunjoon.refactoring.model;

import java.util.Objects;

public class Performance {
    private final String playID;
    private final int audience;

    public Performance(String playID, int audience) {
        this.playID = Objects.requireNonNull(playID, "playID");
        this.audience = audience;
    }

    public String getPlayID() {
        return playID;
    }

    public int getAudience() {
        return audience;
    }
}
