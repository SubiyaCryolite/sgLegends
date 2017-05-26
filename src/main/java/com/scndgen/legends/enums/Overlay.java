package com.scndgen.legends.enums;

/**
 * Created by ifunga on 15/04/2017.
 */
public enum Overlay {
    PRIMARY_MENU(0),
    STATISTICS(1),
    ACHIEVEMENT_LOCKER(2),
    TUTORIAL(3);
    private final int index;

    Overlay(int index) {
        this.index = index;
    }

    public int index() {
        return index;
    }
}
