package com.villvay.sparkprocessor.file;

import org.apache.spark.sql.SparkSession;

import java.io.File;
import java.util.Properties;

public class ProcessorFactory {
    private final Properties properties;
    private final SparkSession spark;

    public ProcessorFactory(Properties properties, SparkSession spark) {
        this.properties = properties;
        this.spark = spark;
    }

    public FileProcessor createFileProcessor(File file) {
        FileType fileType = FileType.getFileType(file.getName());
        switch (fileType) {
            case MATERIAL_MASTER:
                return new MaterialMasterFileProcessor(properties, spark);
            case GROUP:
//                return new GroupFileProcessor(properties, spark);

            default:
                throw new IllegalArgumentException("Unsupported file type: " + fileType);
        }
    }
}
