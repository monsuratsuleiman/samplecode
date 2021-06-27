package com.org.substrings;

import java.nio.file.Path;

public class Main {
    private FileSubstringsExtractor extractor =  new FileSubstringsExtractor();
    void process(String[] arguments) {
        try {

            InMemoryItemTracker inMemoryItemTracker = new InMemoryItemTracker();
            doProcess(arguments, inMemoryItemTracker);
            inMemoryItemTracker.getItemsByCount((count) -> count > 1)
                    .forEach(System.out::println);

        } catch (Exception e){
            System.out.println("A problem occurred. " + e.getMessage());
        }

    }

    private void doProcess(String[] arguments, InMemoryItemTracker inMemoryItemTracker) throws Exception {
        if(arguments.length < 2 || arguments.length > 3) {
            System.out.printf("Only 3 input required. received:%d%n", arguments.length);
            return;
        }

        Path filePath = Path.of(arguments[0]);
        int characterLength = getIntFrom(arguments[1]);
        int minimumStringLength = 5;
        if(arguments.length >  2) {
            minimumStringLength = getIntFrom(arguments[2]);
        }
        extractor.extract(filePath, characterLength, minimumStringLength, inMemoryItemTracker);
    }

    int getIntFrom(String stringValue) throws Exception {
        try {
            return Integer.parseInt(stringValue);
        } catch (Exception e){
            throw new Exception("Invalid value:" + stringValue + ". expected int", e);
        }
    }

    public static void main(String[] args) throws Exception {
        new Main().process(args);
    }

}
