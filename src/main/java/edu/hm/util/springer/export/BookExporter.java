package edu.hm.util.springer.export;

import com.google.inject.ImplementedBy;
import edu.hm.util.springer.model.Book;
import edu.hm.util.springer.model.Chapter;

import java.io.IOException;


@ImplementedBy(BookExporterImpl.class)
public interface BookExporter {
    void exportBook(Book book) throws IOException;

    String getFileName(String bookTitle, Chapter chapter);
}
