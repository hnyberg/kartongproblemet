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

    private final int size;

    public static Article getArticleFromNr(int number) {
        if (number <= values().length) {
            return values()[number - 1];
        }
        return null;
    }
}
