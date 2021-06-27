package com.org.substrings;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


class InMemoryItemTrackerTest {

    @Test
    void shouldReturnEmptyWhenNoItems(){
        Stream<String> result = new InMemoryItemTracker().addAll(List.of())
        .getItemsByCount((count) -> true);

        Assertions.assertEquals(result.collect(Collectors.toList()), List.of());
    }

    @Test
    void shouldReturnAllItemsWhenPredicateIsAlwaysTrue(){
        Stream<String> result = new InMemoryItemTracker().addAll(List.of("x", "y", "z"))
        .getItemsByCount((count) -> true);

        Assertions.assertEquals(result.collect(Collectors.toList()), List.of("x", "y", "z"));
    }

    @Test
    void shouldReturnAllItemsWhenCountIsEqualOne(){
        Stream<String> result = new InMemoryItemTracker().addAll(List.of("x", "y", "z"))
        .getItemsByCount((count) -> count == 1);

        Assertions.assertEquals(result.collect(Collectors.toList()), List.of("x", "y", "z"));
    }

    @Test
    void shouldReturnNoItemsWhenAllItemHaveBeenAddedOnceAndPredicateIsGreaterThan1(){
        Stream<String> result = new InMemoryItemTracker().addAll(List.of("x", "y", "z"))
        .getItemsByCount((count) -> count == 2);

        Assertions.assertEquals(result.collect(Collectors.toList()), List.of());
    }

    @Test
    void shouldReturnItemsThatOccursMoreThanOnceInOneAddAllCall(){
        Stream<String> result = new InMemoryItemTracker().addAll(List.of("x", "y", "z", "x"))
        .getItemsByCount((count) -> count == 2);

        Assertions.assertEquals(result.collect(Collectors.toList()), List.of("x"));
    }

    @Test
    void shouldReturnItemsAddedWithMultipleAddAllCall(){
        Stream<String> result = new InMemoryItemTracker()
                .addAll(List.of("x", "y", "z", "x"))
                .addAll(List.of("x", "y", "z"))

        .getItemsByCount((count) -> count == 3);

        Assertions.assertEquals(result.collect(Collectors.toList()), List.of("x"));
    }
}