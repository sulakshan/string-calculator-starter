package calculator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

class Separator {
    private static final String PIPE_DELIMITER = "|";
    private static final String PIPE_DELIMITER_ESCAPED = "\\|";
    private static final String ESCAPED_RANGE = "\\[";
    private static final String EMPTY_STRING = "";
    public static final int START_INDEX_OFFSET = 3;
    private Pattern customDelimitersValidator = Pattern.compile("//(\\[(\\D+)])+\n.*");
    private Matcher customDelimitersMatcher;
    private int startIndex;

    List<String> getNumbers(String string) {
        String delimiters = getDelimiters(string);
        return separateNumbers(string, delimiters);
    }

    private List<String> separateNumbers(String string, String delimiters) {
        if (areCustomDelimitersValid(string)) {
            string = string.substring(startIndex);
        }
        return asList(string.split(delimiters));
    }

    private String getDelimiters(String string) {
        String delimiters = ",|\n";
        if (areCustomDelimitersValid(string)) {
            delimiters += PIPE_DELIMITER + getEscapedChars();
        }
        return delimiters;
    }

    private String getEscapedChars() {
        String bracketsRemoved = removeBrackets(getCustomDelimiters());
        return Pattern.compile(PIPE_DELIMITER_ESCAPED) // defines pattern which will split input string into substrings
                .splitAsStream(bracketsRemoved)        // splits string with customDelimiters into separate substrings
                .map(Pattern::quote)                   // escapes all special chars
                .collect(joining(PIPE_DELIMITER));     // collects result back into one string
    }


    private String getCustomDelimiters() {
        String customDelimiters = customDelimitersMatcher.group(1);
        setStartIndex(customDelimiters);
        return customDelimiters;
    }

    private String removeBrackets(String customDelimiters) {
        return customDelimiters.replaceFirst(ESCAPED_RANGE, EMPTY_STRING)
                .replaceAll(ESCAPED_RANGE, PIPE_DELIMITER)
                .replaceAll("]", EMPTY_STRING);
    }

    private void setStartIndex(String customDelimiters) {
        startIndex = customDelimiters.length() + START_INDEX_OFFSET;
    }

    private boolean areCustomDelimitersValid(String string) {
        customDelimitersMatcher = customDelimitersValidator.matcher(string);
        return customDelimitersMatcher.matches();
    }
}