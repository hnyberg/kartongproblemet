package com.hannes.boxfinder.service;

import com.hannes.boxfinder.model.Article;
import com.hannes.boxfinder.model.Box;

import java.util.*;
import java.util.regex.Pattern;

public class BoxFinder {

    public static final String DENIED = "Upphämtning krävs";
    public static final String BOX_PREFIX = "Kartong nr";
    public static final Pattern INPUT_PATTERN = Pattern.compile("^\\d+\\s+st artikel\\s+\\d+$");

    public static String findBox(String[] args) {

        List<String> arguments = transformInputToListOfArguments(args);
        boolean allInputHasCorrectFormat = verifyThatNoInputHasIncorrectFormat(arguments);
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

    static Boolean verifyThatNoInputHasIncorrectFormat(List<String> arguments) {
        return arguments.stream()
                .filter(arg -> arg == null
                        || arg.isBlank()
                        || !INPUT_PATTERN.matcher(arg.toLowerCase().trim()).matches())
                .toList()
                .isEmpty();
    }

    static Map<Integer, Integer> getArticleSizesFromInput(List<String> args) {
        Map<Integer, Integer> sizes = new HashMap<>();
        args.forEach(arg -> {
            String[] splitArg = arg.split(" ");
            int amountToAdd = Integer.parseInt(splitArg[0]);
            int articleNr = Integer.parseInt(splitArg[splitArg.length - 1]);
            Article article = Article.getarticleFromNr(articleNr);
            if (article == null) {
                System.out.println("artikel " + articleNr + " kunde ej hittas och ignoreras färmed");
                return;
            }
            int articleSize = article.getLength();
            Integer nrOfArticelsSoFar = sizes.getOrDefault(articleSize, 0);
            nrOfArticelsSoFar += amountToAdd;
            sizes.put(articleSize, nrOfArticelsSoFar);
        });
        return sizes;
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

    private static boolean fitArticles(Box box, Map<Integer, Integer> articleSizesLeft) {

        Deque<Integer> articlesSorted = new ArrayDeque<>();

        Map<Integer, Integer> availableSpacePerRow = new HashMap<>();
        for (int i = 1; i <= box.getWidth(); i++) {
            availableSpacePerRow.put(i, box.getLength());
        }

        availableSpacePerRow.forEach((row, spaceLeft) -> {

        });

        return articleSizesLeft.entrySet().stream().noneMatch(entry -> entry.getValue() > 0);
    }
}
