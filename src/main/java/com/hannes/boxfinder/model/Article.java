package com.hannes.boxfinder.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Article {

    ONE(1),
    TWO(2),
    THREE(4),
    FOUR(6),
    FIVE(8),
    SIX(9),
    SEVEN(12),
    EIGHT(5),
    NINE(9);

    private final int length;

    public static Article getarticleFromNr(int number) {
        return switch (number) {
            case 1 -> ONE;
            case 2 -> TWO;
            case 3 -> THREE;
            case 4 -> FOUR;
            case 5 -> FIVE;
            case 6 -> SIX;
            case 7 -> SEVEN;
            case 8 -> EIGHT;
            case 9 -> NINE;
            default -> null;
        };
    }
}
