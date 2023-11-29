package com.villvay.sparkprocessor.file;

public enum FileType {
    MATERIAL_MASTER("us5_material_master"),
    GROUP("content_upload");

    private final String fileName;

    FileType(String fileName) {
        this.fileName = fileName;
    }

    public static FileType getFileType(String fileName) {
        for (FileType fileType : values()) {
            if (fileName.toLowerCase().contains(fileType.fileName)) {
                return fileType;
            }
        }
        throw new IllegalArgumentException("Unsupported file name: " + fileName);
    }
}
