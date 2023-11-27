package com.villvay.sparkprocessor;

import com.villvay.sparkprocessor.config.ConfigLoader;
import com.villvay.sparkprocessor.config.ValidationConfig;
import com.villvay.sparkprocessor.file.utils.FileUtils;
import com.villvay.sparkprocessor.schema.FileSchema;
import com.villvay.sparkprocessor.util.PropertiesLoader;
import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.api.java.function.ForeachFunction;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static com.villvay.sparkprocessor.validator.Validator.validateLine;

public class SparkApplication {

    public static void main(String[] args) {

        Properties properties = PropertiesLoader.loadProperties();
        String inputDirectory = properties.getProperty("pim.types[0].source.location.pending");
        String outputDirectory = properties.getProperty("pim.types[0].source.location.done");
        String delimiter = properties.getProperty("pim.types[0].delimiter");

        SparkSession spark = SparkSession.builder()
                .appName("SparkBatchApplication")
                .master("local[*]")
                .getOrCreate();

        while (true) {

            File directory = new File(inputDirectory);
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".txt")) {

                        Dataset<Row> data = spark.read()
                                .option("header", true)
                                .schema(FileSchema.getItemFileSchema())
                                .option("delimiter", delimiter)
                                .csv(file.getPath());

                        ValidationConfig config = ConfigLoader.loadConfig();

                        data.foreach((ForeachFunction<Row>) row -> validateLine(String.valueOf(row),config));
                        Dataset<String> validatedData = data.map(
                                (MapFunction<Row, String>) row -> {
                                    // Construct the CSV line dynamically based on the schema
                                    String csvLine = constructCsvLine(row, delimiter);

                                    // Apply validation logic to the CSV line
                                    return validationLibrary.validateLine(csvLine, config);
                                },
                                Encoders.STRING()
                        );


//                        // Generalize and move to a separate method
//                        Dataset<Row> invalidRows = data.filter((FilterFunction<Row>) row ->
//                                row.isNullAt(row.fieldIndex("SAP#")) || row.getString(row.fieldIndex("SAP#")).isEmpty() ||
//                                row.isNullAt(row.fieldIndex("Material#")) || row.getString(row.fieldIndex("Material#")).isEmpty() ||
//                                row.isNullAt(row.fieldIndex("MaterialGroup")) || row.getString(row.fieldIndex("MaterialGroup")).isEmpty() ||
//                                row.isNullAt(row.fieldIndex("MinimumQuantity"))|| row.getInt(row.fieldIndex("MinimumQuantity")) == 0 ||
//                                row.isNullAt(row.fieldIndex("QuantityByIncrements"))|| row.getInt(row.fieldIndex("QuantityByIncrements")) == 0
//
//                        );
//
//                        // Log invalid rows
//                        System.out.println(" Invalid Rows:");
//                        invalidRows.show();
//
//                        // Send the valid data to the Queue
//                        Dataset<Row> validRows = data.except(invalidRows);
//                        System.out.println(" Valid Rows:");
//                        validRows.show();
//
//                        // Move the processed file to the done directory
//                        FileUtils.moveFile(file, outputDirectory);
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
}