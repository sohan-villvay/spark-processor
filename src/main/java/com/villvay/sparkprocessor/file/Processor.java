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

        loadFile(spark);
    }

    private void processData(File file, Dataset<Row> data){
        data.show(20);
        FileUtils.moveFile(file, ProcessStatus.DONE);
    }

    private void loadFile(SparkSession spark){
        String inputDirectory = properties.getProperty("pim.types[0].source.location.pending");
        String outputDirectory = properties.getProperty("pim.types[0].source.location.done");

        while (true) {
            File directory = new File(inputDirectory);
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        readFile(spark, file);
                    }
                }
            }
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
                spark.stop();
            }
        }
    }

    private void readFile(SparkSession spark, File file){
        String delimiter = properties.getProperty("pim.types[0].delimiter");
        Dataset<Row> data = spark.read()
                .option("header", true)
                .schema(FileSchema.getItemFileSchema())
                .option("delimiter", delimiter)
                .csv(file.getPath());

        processData(file, data);
    }

}
