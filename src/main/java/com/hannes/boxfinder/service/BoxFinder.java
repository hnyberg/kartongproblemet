package com.hannes.boxfinder.service;

import com.hannes.boxfinder.model.Article;
import com.hannes.boxfinder.model.Box;

import java.util.*;
import java.util.regex.Pattern;

public class BoxFinder {

    public static final String DENIED = "Upphämtning krävs";
    public static final String BOX_PREFIX = "kartong nr";
    public static final Pattern INPUT_PATTERN = Pattern.compile("^\\d+\\s+st artikel\\s+\\d+$");

    public static String findBox(String[] args) throws ArticleNotFoundException {

        List<String> arguments = transformInputToListOfArguments(args);
        boolean allInputHasCorrectFormat = hasOnlyValidInput(arguments);
        if (!allInputHasCorrectFormat) {
            return "minst 1 indata är i inkorrekt format";
        }

        Map<Integer, Integer> articlesSizes = getArticleSizesFromInput(arguments);
        Box box = findSuitableBox(articlesSizes);
        return box == null ? DENIED : BOX_PREFIX + " " + box.getName();
    }

    static List<String> transformInputToListOfArguments(String[] args) {
        String inputAsOneString = String.join(" ", args);
        return Arrays.stream(inputAsOneString.split(","))
                .map(String::trim)
                .toList();
    }

    static Boolean hasOnlyValidInput(List<String> arguments) {
        return arguments.stream()
                .filter(arg -> arg == null
                        || arg.isBlank()
                        || !INPUT_PATTERN.matcher(arg.toLowerCase().trim()).matches())
                .toList()
                .isEmpty();
    }

    static Map<Integer, Integer> getArticleSizesFromInput(List<String> args) throws ArticleNotFoundException {
        Map<Integer, Integer> articlesBySize = new TreeMap<>(Comparator.reverseOrder());
        for (String arg : args) {
            String[] splitArg = arg.split(" ");
            int amountToAdd = Integer.parseInt(splitArg[0]);
            int articleNr = Integer.parseInt(splitArg[splitArg.length - 1]);
            Article article = Article.getArticleFromNr(articleNr);
            if (article == null) {
                throw new ArticleNotFoundException("artikel " + articleNr + " ej giltig artikel");
            }
            int articleSize = article.getSize();
            Integer nrArticlesSoFar = articlesBySize.getOrDefault(articleSize, 0);
            articlesBySize.put(articleSize, nrArticlesSoFar + amountToAdd);
        }
        return articlesBySize;
    }

    static Box findSuitableBox(Map<Integer, Integer> articleSizes) {
        List<Box> boxesBySize = Arrays.stream(Box.values()).sorted(Comparator.comparing(Box::getSize)).toList();
        for (Box box : boxesBySize) {
            if (!checkIfTotalVolumeIsEnough(box, articleSizes)) {
                continue;
            }
            boolean allArticlesFit = fitArticles(box, articleSizes);
            if (allArticlesFit) {
                return box;
            }
        }
        return null;
    }

    private static boolean checkIfTotalVolumeIsEnough(Box box, Map<Integer, Integer> articlesSizes) {
        int totalVolumeNeed = 0;
        for (Map.Entry<Integer, Integer> entry : articlesSizes.entrySet()) {
            totalVolumeNeed += entry.getKey() * entry.getValue();
        }
        return box.getSize() >= totalVolumeNeed;
    }

    private static boolean fitArticles(Box box, Map<Integer, Integer> articlesGroupedBySize) {

        HashMap<Integer, Integer> modifiableArticleMap = new HashMap<>(articlesGroupedBySize);

        Map<Integer, Integer> availableSpacePerRow = new HashMap<>();
        for (int i = 1; i <= box.getWidth(); i++) {
            availableSpacePerRow.put(i, box.getLength());
        }

        //  Fyll ut varje rad i kartongen. Sätt in så stora artiklar som möjligt per ledigt utrymme
        for (Map.Entry<Integer, Integer> boxEntry : availableSpacePerRow.entrySet()) {
            Integer spaceLeftOnRow = boxEntry.getValue();

            Iterator<Map.Entry<Integer, Integer>> articleIterator = modifiableArticleMap.entrySet().iterator();
            while (spaceLeftOnRow > 0 && articleIterator.hasNext()) {
                Map.Entry<Integer, Integer> articleEntry = articleIterator.next();
                Integer articleSize = articleEntry.getKey();
                if (articleSize > spaceLeftOnRow) {
                    continue;
                }

                Integer availableArticles = articleEntry.getValue();
                int nrOfArticlesThatFit = Math.min(spaceLeftOnRow / articleSize, availableArticles);
                if (nrOfArticlesThatFit > 0) {
                    spaceLeftOnRow -= nrOfArticlesThatFit * articleSize;
                    availableArticles -= nrOfArticlesThatFit;
                    if (availableArticles == 0) {
                        articleIterator.remove();
                    } else {
                        articleEntry.setValue(availableArticles);
                    }
                }
            }

            boxEntry.setValue(spaceLeftOnRow);
        }

        return modifiableArticleMap.entrySet().stream().noneMatch(entry -> entry.getValue() > 0);
    }
}
