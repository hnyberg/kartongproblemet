package com.hannes.boxfinder.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Box {
    ONE(1, 4, 5),
    TWO(2, 8, 12),
    THREE(3, 12, 20);

    private final int name;
    private final int width;
    private final int length;

    public int getSize() {
        return width * length;
    }
}
