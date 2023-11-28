package com.villvay.sparkprocessor;

import com.villvay.sparkprocessor.file.Processor;

public class SparkApplication {

    public static void main(String[] args) {
        Processor processor = new Processor();
        processor.start();
    }
}