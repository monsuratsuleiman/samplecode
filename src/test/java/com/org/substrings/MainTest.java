package com.org.substrings;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;

import static org.mockito.Mockito.mock;

class MainTest {

    @Test
    void shouldDisplayInvalidInputMessageWhenArgumentsLengthIs0() throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Main.main(new String[0]);

        Assertions.assertEquals("Only 3 input required. received:0\n", outputStream.toString());
    }

    @Test
    void shouldDisplayInvalidInputMessageWhenArgumentsLengthIs1() throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Main.main(new String[]{"/pa"});

        Assertions.assertEquals("Only 3 input required. received:1\n", outputStream.toString());
    }


    @Test
    void shouldDisplayInvalidInputMessageWhenCharacterLengthIsNotInteger() throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Main.main(new String[]{"/pa", "1dsdfsdf"});

        Assertions.assertEquals("A problem occurred. Invalid value:1dsdfsdf. expected int\n", outputStream.toString());
    }

    @Test
    void shouldDisplayInvalidInputMessageWhenMinimumSubStringLengthIsNotInteger() throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Main.main(new String[]{"/pa", "12", "xxxx"});

        Assertions.assertEquals("A problem occurred. Invalid value:xxxx. expected int\n", outputStream.toString());
    }

    @Test
    void shouldDisplayResultForAValidInput() throws Exception {
        PrintStream original = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        String validInput  = "\nHello world, this world is a really beautiful place. A place like home.\n";
        File inputFile = TestUtils.createSampleBinaryFile(validInput, "utf-8");

        Main.main(new String[]{inputFile.getAbsolutePath(), "8", "5"});

        System.setOut(original);
        System.out.println(outputStream.toString());
        String[] splitOutput = outputStream.toString().split("\n");
        Assertions.assertTrue(Arrays.equals(new String[]{" place", " world", "world", "place"},
                splitOutput));
    }
}