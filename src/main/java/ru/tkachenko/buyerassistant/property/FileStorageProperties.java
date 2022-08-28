package ru.tkachenko.buyerassistant.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {

    private String uploadDir;

    private String loaDir;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public String getLoaDir() {
        return loaDir;
    }

    public void setLoaDir(String loaDir) {
        this.loaDir = loaDir;
    }
}
