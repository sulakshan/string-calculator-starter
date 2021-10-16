package calculator;

import java.util.List;

import static java.util.stream.Collectors.joining;

class StringCalculator {

    int add(String string) {
        if (string.isEmpty()) {
            return 0;
        }
        Separator separator = new Separator();
        List<String> numbersList = separator.getNumbers(string);
        checkForNegativeNumbers(numbersList);
        return sumArray(numbersList);
    }

    private void checkForNegativeNumbers(List<String> numbersList) {
        String negatives = numbersList.stream()
                .filter(s -> s.contains("-"))
                .collect(joining(","));
        if (!negatives.isEmpty()) {
            throw new IllegalArgumentException("Negatives not allowed: " + negatives);
        }
    }

    private int sumArray(List<String> numbersList) {
        return numbersList.stream()
                .filter(s -> Integer.parseInt(s) <= 1000)
                .mapToInt(Integer::parseInt)
                .sum();
    }
}