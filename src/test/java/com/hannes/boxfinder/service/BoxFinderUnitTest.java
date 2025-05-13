package com.hannes.boxfinder.service;

import com.hannes.boxfinder.model.Article;
import com.hannes.boxfinder.model.Box;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.hannes.boxfinder.model.Article.*;
import static org.junit.jupiter.api.Assertions.*;

class BoxFinderUnitTest {

    static Stream<Arguments> findBoxCases() {
        return Stream.of(
                Arguments.of("kartong nr 2", new String[]{"6 st artikel 7, 2 st artikel 4, 4 st artikel 1"}),
                Arguments.of("minst 1 indata är i inkorrekt format", new String[]{""}),
                Arguments.of("minst 1 indata är i inkorrekt format", new String[]{"bad input"})
        );
    }

    static Stream<Arguments> transformInputToListOfArgumentsCases() {
        return Stream.of(
                Arguments.of(
                        Arrays.asList("6 st artikel 7", "2 st artikel 4", "4 st artikel 1"),
                        new String[]{"6 st artikel 7, 2 st artikel 4, 4 st artikel 1"}
                ),
                Arguments.of(Arrays.asList(""), new String[]{""})
        );
    }

    static Stream<Arguments> hasOnlyValidInputFormatCases() {
        return Stream.of(
                Arguments.of(true, Arrays.asList("6 st artikel 7", "2 st artikel 4", "4 st artikel 1")),
                Arguments.of(true, Arrays.asList("99 st artikel 99")),
                Arguments.of(true, Arrays.asList("6  st  artikel   7")),
                Arguments.of(true, Arrays.asList("2STARTIKEL3")),
                Arguments.of(false, Arrays.asList("huehuehue")),
                Arguments.of(false, Arrays.asList("X st artikel 7")),
                Arguments.of(false, Arrays.asList("6 st artikel X"))
        );
    }

    static Stream<Arguments> getArticleSizesFromInputCases() {
        return Stream.of(
                Arguments.of(
                        Map.of(12, 6, 6, 2, 1, 4),
                        Arrays.asList("6 st artikel 7", "2 st artikel 4", "4 st artikel 1")
                ),
                Arguments.of(
                        Map.of(9, 5),
                        Arrays.asList("2 st artikel 6", "3 st artikel 9")
                )
        );
    }

    static Stream<Arguments> nonExistingArticles() {
        return Stream.of(
                Arguments.of(
                        "artikel 11 ej giltig artikel",
                        Arrays.asList("2 st artikel 11", "3 st artikel 54")
                )
        );
    }

    static Stream<Arguments> findSuitableBoxCases() {
        return Stream.of(
                Arguments.of(Box.TWO, Map.of(SEVEN, 6, FOUR, 2, ONE, 4)),
                Arguments.of(Box.ONE, Map.of(THREE, 3, ONE, 1, TWO, 1)),
                Arguments.of(Box.TWO, Map.of(FIVE, 1, FOUR, 3)),
                Arguments.of(null, Map.of(SEVEN, 12, ONE, 100)),
                Arguments.of(Box.ONE, Map.of(EIGHT, 4))
        );
    }

    @ParameterizedTest
    @MethodSource("findBoxCases")
    void findBox(String expectedResult, String[] input) throws ArticleNotFoundException {
        assertEquals(expectedResult, BoxFinder.findBox(input));
    }

    @ParameterizedTest
    @MethodSource("transformInputToListOfArgumentsCases")
    void transformInputToListOfArguments(List<String> expected, String[] input) {
        assertEquals(expected, BoxFinder.transformInputToListOfArguments(input));
    }

    @ParameterizedTest
    @MethodSource("hasOnlyValidInputFormatCases")
    void hasOnlyValidInputFormat(Boolean expected, List<String> input) {
        assertEquals(expected, BoxFinder.hasOnlyValidInputFormat(input));
    }

    @ParameterizedTest
    @MethodSource("getArticleSizesFromInputCases")
    void getArticleSizesFromInput(Map<Integer, Integer> expected, List<String> input) throws ArticleNotFoundException {
        assertEquals(new HashMap<>(expected), BoxFinder.getArticleSizesFromInput(input));
    }

    @ParameterizedTest
    @MethodSource("nonExistingArticles")
    void getArticleSizesFromInput_nonExistingArticles(String exceptedErrorMessage, List<String> input) throws ArticleNotFoundException {
        ArticleNotFoundException anfe = assertThrows(ArticleNotFoundException.class, () -> BoxFinder.getArticleSizesFromInput(input), "");
        assertEquals(exceptedErrorMessage, anfe.getMessage());
    }

    @ParameterizedTest
    @MethodSource("findSuitableBoxCases")
    void findSuitableBox(Box expected, Map<Article, Integer> input) {
        HashMap<Integer, Integer> articleSizes = new HashMap<>();
        input.forEach((article, nrOfArticles) -> {
            int articleSize = article.getSize();
            int nrOfArticlesSoFar = articleSizes.getOrDefault(articleSize, 0);
            articleSizes.put(articleSize, nrOfArticlesSoFar + nrOfArticles);
        });
        assertEquals(expected, BoxFinder.findSuitableBox(articleSizes));
    }
}