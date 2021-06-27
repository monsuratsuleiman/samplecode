package com.org.substrings;

import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class InMemoryItemTracker {
    private HashMap<String, Integer> tracker = new HashMap<>();

    InMemoryItemTracker addAll(List<String> items){
        items.forEach((item) -> {
            Integer itemCount = tracker.get(item);
            if(itemCount != null) {
                tracker.put(item, itemCount + 1);
            }
            else {
                tracker.put(item, 1);
            }
        });
        return this;
    }

    Stream<String> getItemsByCount(Predicate<Integer> predicate){
        return tracker.entrySet().stream().filter((entry) -> predicate.test(entry.getValue()))
                .map((entry -> entry.getKey()));
    }
}
