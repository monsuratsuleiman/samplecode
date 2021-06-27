package com.org.substrings;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestUtils {
    public static File createSampleBinaryFile(String text, String charsetName) throws IOException {
        Path tempFilePath = Files.createTempFile("test", "file");
        File tempFile = tempFilePath.toFile();
        try(FileOutputStream fileOutputStream = new FileOutputStream(tempFile)) {
            try(BufferedOutputStream outputStream = new BufferedOutputStream(fileOutputStream)) {
                outputStream.write(text.getBytes(charsetName));
            }
        }
        return tempFile;
    }
}
