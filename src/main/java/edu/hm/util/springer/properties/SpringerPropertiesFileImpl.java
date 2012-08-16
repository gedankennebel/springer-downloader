package edu.hm.util.springer.properties;

import java.io.IOException;
import java.util.Properties;

public class SpringerPropertiesFileImpl implements SpringerProperties {

    private Properties properties = new java.util.Properties();

    public SpringerPropertiesFileImpl() {
        try {
            properties.load(ClassLoader.getSystemClassLoader().getResourceAsStream("springer.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBookUrlByIsbn(String isbn) {
        return properties.get("urlScheme").toString().replace(properties.get("isbnPlaceholder").toString(), isbn);
    }

    @Override
    public int getMaxRetries() {
        return Integer.parseInt(properties.getProperty("maxRetries"));
    }

    @Override
    public int getMaxConcurrentPdfDownloads() {
        return Integer.parseInt(properties.getProperty("maxConcurrentPdfDownloads"));
    }

    @Override
    public int getMaxChapterFileNameSize() {
        return Integer.parseInt(properties.getProperty("maxChapterFileNameSize"));
    }
}
