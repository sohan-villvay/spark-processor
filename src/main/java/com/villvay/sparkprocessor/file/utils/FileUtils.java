package com.villvay.sparkprocessor.file.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileUtils {
    public static void moveFile(File file, String outputDirectory){
        try {
            Path source = file.toPath();
            Path destination = new File(outputDirectory, file.getName()).toPath();
            Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
