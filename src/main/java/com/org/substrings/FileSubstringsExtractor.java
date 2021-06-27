package com.org.substrings;

import kotlin.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FileSubstringsExtractor {
    void extract(Path path, int characterLength, int minimumSubstringLength, InMemoryItemTracker substringsTracker) throws Exception {
        if (!path.toFile().exists()) {
            throw new Exception(String.format("Path: %s does not exists", path));
        }

        String charset = getValidCharset(characterLength);
        boolean keepExtracting = true;
        int skip = 0;

        while (keepExtracting) {
            Pair<List<String>, Integer> iterationStringsAndMaxLength = extractSubstrings(path, charset, minimumSubstringLength, skip);
            substringsTracker.addAll(iterationStringsAndMaxLength.getFirst());
            Integer iterationMaxStringLength = iterationStringsAndMaxLength.getSecond();
            if (iterationMaxStringLength > minimumSubstringLength && iterationMaxStringLength > 1) {
                skip++;
            } else {
                keepExtracting = false;
            }
        }

    }

    private Pair<List<String>, Integer> extractSubstrings(Path path, String charset, int minimumSubstringLength, int skip) throws IOException {
        int charInt;
        char charValue;
        String currentText = "";
        ArrayList<String> allSubstrings = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path, Charset.forName(charset))) {
            reader.skip(skip);
            while ((charInt = reader.read()) != -1) {
                charValue = (char) charInt;
                String stringValue = String.valueOf(charValue);
                currentText = currentText + stringValue;
                if (currentText.trim().length() >= minimumSubstringLength) {
                    allSubstrings.add(currentText);
                }
            }
        }

        return new Pair(allSubstrings, currentText.trim().length());
    }

    private String getValidCharset(int characterLength) throws Exception {
        Set<Integer> validCharacterLengths = Set.of(8, 16, 32);
        if (!validCharacterLengths.contains(characterLength)) {
            throw new Exception("Invalid Character Length");
        }

        return String.format("utf-%d", characterLength);
    }
}
