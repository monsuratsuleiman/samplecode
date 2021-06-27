package com.org.substrings;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.MalformedInputException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;


class FileSubstringsExtractorTest {
    @Test
    void shouldThrowWhenFileDoesNotExists() throws Exception {
        InMemoryItemTracker mockTracker = mock(InMemoryItemTracker.class);
        FileSubstringsExtractor fileSubstringsExtractor = new FileSubstringsExtractor();

        Assertions.assertThrows(Exception.class, ()-> {
                fileSubstringsExtractor.extract(Path.of(UUID.randomUUID().toString()),
                    32, 5, mockTracker);
        });
    }

    @Test
    void shouldThrowWhenInvalidCharset() throws Exception {
        InMemoryItemTracker mockTracker = mock(InMemoryItemTracker.class);
        FileSubstringsExtractor fileSubstringsExtractor = new FileSubstringsExtractor();

        File inputFile = TestUtils.createSampleBinaryFile("Hello", "utf-8");

        Exception exception = Assertions.assertThrows(Exception.class, ()-> {
                fileSubstringsExtractor.extract(inputFile.toPath(),
                    12, 5, mockTracker);
        });

        Assertions.assertEquals("Invalid Character Length", exception.getMessage());
    }

    @Test
    void shouldThrowWhenInputCharacterLengthDoesNotMatchFileCharacterLength() throws Exception {
        InMemoryItemTracker mockTracker = mock(InMemoryItemTracker.class);

        File inputFile = TestUtils.createSampleBinaryFile("Hello", "utf-8");
        FileSubstringsExtractor fileSubstringsExtractor = new FileSubstringsExtractor();

        Assertions.assertThrows(MalformedInputException.class, () -> {
            fileSubstringsExtractor.extract(inputFile.toPath(),
                    32, 5, mockTracker);
        });
    }

    @Test
    void shouldExtract0SubstringWhenStringLengthLessThanMinimumSubstringLength() throws Exception {
        InMemoryItemTracker mockTracker = mock(InMemoryItemTracker.class);

        File inputFile = TestUtils.createSampleBinaryFile("Hell", "utf-8");
        FileSubstringsExtractor fileSubstringsExtractor = new FileSubstringsExtractor();

        fileSubstringsExtractor.extract(inputFile.toPath(),
                8, 5, mockTracker);

        verify(mockTracker, times(1)).addAll(any());
        verify(mockTracker, times(1)).addAll(List.of());
    }

    @Test
    void shouldExtractOnlyOneSubstringWhenStringLengthIsEqualToMinimumSubstringLength() throws Exception {
        InMemoryItemTracker mockTracker = mock(InMemoryItemTracker.class);

        File inputFile = TestUtils.createSampleBinaryFile("Hello", "utf-8");
        FileSubstringsExtractor fileSubstringsExtractor = new FileSubstringsExtractor();

        fileSubstringsExtractor.extract(inputFile.toPath(),
                8, 5, mockTracker);

        verify(mockTracker, times(1)).addAll(any());
        verify(mockTracker, times(1)).addAll(List.of("Hello"));
    }

    @Test
    void shouldExtract0SubstringWhenStringLengthWithoutSpaceIsLessThanMinimumSubstringLength() throws Exception {
        InMemoryItemTracker mockTracker = mock(InMemoryItemTracker.class);

        File inputFile = TestUtils.createSampleBinaryFile("Hell ", "utf-8");
        FileSubstringsExtractor fileSubstringsExtractor = new FileSubstringsExtractor();

        fileSubstringsExtractor.extract(inputFile.toPath(),
                8, 5, mockTracker);

        verify(mockTracker, times(1)).addAll(any());
        verify(mockTracker, times(1)).addAll(List.of());
    }

    @Test
    void shouldExtractAllSubstringWhenStringLengthIsGreaterThanMinimumSubstringLength() throws Exception {
        InMemoryItemTracker mockTracker = mock(InMemoryItemTracker.class);

        File inputFile = TestUtils.createSampleBinaryFile("Hello W", "utf-8");
        FileSubstringsExtractor fileSubstringsExtractor = new FileSubstringsExtractor();

        fileSubstringsExtractor.extract(inputFile.toPath(),
                8, 5, mockTracker);

        verify(mockTracker, times(3)).addAll(any());
        verify(mockTracker, times(1)).addAll(List.of("Hello", "Hello ", "Hello W"));
        verify(mockTracker, times(1)).addAll(List.of("ello W"));
        verify(mockTracker, times(1)).addAll(List.of("llo W"));
    }

}


