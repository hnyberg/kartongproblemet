package com.hannes.boxfinder.service;

import com.hannes.boxfinder.model.Article;
import com.hannes.boxfinder.model.Box;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BoxFinderUnitTest {

    @Test
    void findSuitableBox_example1() {
        Map<Integer, Integer> input = Map.of(
                Article.SEVEN.getLength(), 6,
                Article.FOUR.getLength(), 2,
                Article.ONE.getLength(), 4
        );
        assertEquals(Box.TWO, BoxFinder.findSuitableBox(input));
    }
}