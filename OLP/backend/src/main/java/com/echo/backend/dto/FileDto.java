package com.echo.backend.dto;

public class FileDto {
    private String fileName;
    private String folder;

    public FileDto(String fileName, String folder) {
        this.fileName = fileName;
        this.folder = folder;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFolder() {
        return folder;
    }
}
