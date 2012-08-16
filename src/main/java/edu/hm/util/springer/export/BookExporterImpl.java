package edu.hm.util.springer.export;

import edu.hm.util.springer.model.Book;
import edu.hm.util.springer.model.Chapter;
import edu.hm.util.springer.properties.SpringerProperties;
import edu.hm.util.springer.util.SpringerFileUtils;
import org.apache.commons.io.FileUtils;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class BookExporterImpl implements BookExporter {
    private static final NumberFormat numberFormat = new DecimalFormat("00");
    private static final String FILE_EXTENSION = ".pdf";

    @Inject
    private SpringerProperties springerProperties;


    public void exportBook(Book book) throws IOException {
        List<Chapter> chapters = book.getChapters();
        for (Chapter chapter : chapters) {
            String filename = getFileName(book.getTitle(), chapter);

            if (!SpringerFileUtils.doesFileExistAndHaveContent(filename)) {
                FileUtils.writeByteArrayToFile(new File(filename), chapter.getPdf());
            }
        }
    }

    @Override
    public String getFileName(String bookTitle, Chapter chapter) {
        String chapterFileName = SpringerFileUtils.escapeForFileSystem(chapter.getName());
        chapterFileName = numberFormat.format(chapter.getChapterNumber()) + " " + chapterFileName;
        chapterFileName = truncateChapterFileNameIfNecessary(chapterFileName);
        chapterFileName += FILE_EXTENSION;

        return "./" + SpringerFileUtils.escapeForFileSystem(bookTitle) + "/" + chapterFileName;
    }

    private String truncateChapterFileNameIfNecessary(String chapterFileName) {
        int maxSize = springerProperties.getMaxChapterFileNameSize();
        maxSize -= FILE_EXTENSION.length();
        int currentSize = chapterFileName.length();
        chapterFileName = chapterFileName.substring(0, Math.min(maxSize, currentSize));
        return chapterFileName;
    }


}
