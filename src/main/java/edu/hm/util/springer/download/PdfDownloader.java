package edu.hm.util.springer.download;

import com.google.inject.ImplementedBy;
import edu.hm.util.springer.model.Book;


@ImplementedBy(ConcurrentPdfDownloader.class)
public interface PdfDownloader {
    void downloadPdfs(Book book);

    void shutdown();
}
