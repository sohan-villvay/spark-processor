package com.villvay.sparkprocessor.file;

import com.villvay.sparkprocessor.file.utils.FileUtils;
import com.villvay.sparkprocessor.schema.FileSchema;
import com.villvay.sparkprocessor.util.PropertiesLoader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.File;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Processor {

    static Properties properties = PropertiesLoader.loadProperties();

    public void start() {
        SparkSession spark = SparkSession.builder()
                .appName("SparkBatchApplication")
                .master("local[*]")
                .getOrCreate();

        ProcessorFactory processorFactory = new ProcessorFactory(properties, spark);
        long delay = Long.parseLong(properties.getProperty("pim.source.delay"));

        while (true) {
            processPendingFiles(processorFactory);
            try {
                TimeUnit.SECONDS.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
                spark.stop();
            }
        }
    }

    private void processPendingFiles(ProcessorFactory fileProcessorFactory) {
        String inputDirectory = properties.getProperty("pim.source.location.pending");
        File directory = new File(inputDirectory);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    FileProcessor fileProcessor = fileProcessorFactory.createFileProcessor(file);
                    fileProcessor.processFile(file);
                }
            }
        }
    }

}
