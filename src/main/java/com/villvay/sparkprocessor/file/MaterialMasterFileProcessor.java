package com.villvay.sparkprocessor.file;

import com.villvay.sparkprocessor.file.utils.FileUtils;
import com.villvay.sparkprocessor.schema.FileSchema;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.File;
import java.util.Properties;

public class MaterialMasterFileProcessor implements FileProcessor {
    private final Properties properties;
    private final SparkSession spark;

    public MaterialMasterFileProcessor(Properties properties, SparkSession spark) {
        this.properties = properties;
        this.spark = spark;
    }

    @Override
    public void processFile(File file) {

        String delimiter = properties.getProperty("pim.item.delimiter");
        String sinkUrl = properties.getProperty("pim.item.sink.url");

        Dataset<Row> data = spark.read()
                .option("header", true)
                .schema(FileSchema.getItemFileSchema())
                .option("delimiter", delimiter)
                .csv(file.getPath());

        sendDataToSink(data, sinkUrl);
        FileUtils.moveFile(file, ProcessStatus.DONE);
    }

    private void sendDataToSink(Dataset<Row> data, String sinkUrl) {
        data.show(20);
        System.out.println("Send data to sink: " + sinkUrl);
    }
}
