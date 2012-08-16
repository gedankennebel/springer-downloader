package edu.hm.util.springer.properties;

import com.google.inject.ImplementedBy;


@ImplementedBy(SpringerPropertiesFileImpl.class)
public interface SpringerProperties {
    String getBookUrlByIsbn(String isbn);

    int getMaxRetries();

    int getMaxConcurrentPdfDownloads();

    int getMaxChapterFileNameSize();
}
