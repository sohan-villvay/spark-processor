package com.villvay.sparkprocessor.file.utils;

import com.villvay.sparkprocessor.file.ProcessStatus;
import com.villvay.sparkprocessor.util.PropertiesLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

public class FileUtils {

    static Properties properties = PropertiesLoader.loadProperties();

    public static void moveFile(File file, ProcessStatus status){

        String outputDirectory = null;

        switch(status){
            case DONE:
                outputDirectory = properties.getProperty("pim.source.location.done");
                break;
            case ERROR:
                outputDirectory = properties.getProperty("pim.source.location.error");
            default:
                System.out.println("Invalid Processing Status");
        }

        try {
            Path source = file.toPath();
            Path destination = new File(outputDirectory, file.getName()).toPath();
            Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
