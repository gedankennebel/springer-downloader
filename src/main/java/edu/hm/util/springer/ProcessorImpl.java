package edu.hm.util.springer;

import edu.hm.util.springer.download.PdfDownloader;
import edu.hm.util.springer.export.BookExporter;
import edu.hm.util.springer.model.Book;
import edu.hm.util.springer.model.Chapter;
import edu.hm.util.springer.parser.HtmlParser;
import edu.hm.util.springer.properties.SpringerProperties;
import edu.hm.util.springer.util.SpringerFileUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ProcessorImpl implements Processor {
    @Inject
    private HtmlParser htmlParser;
    @Inject
    private PdfDownloader pdfDownloader;
    @Inject
    private BookExporter bookExporter;
    @Inject
    private SpringerProperties springerProperties;

    private ExecutorService executorService = Executors.newCachedThreadPool();

    public void processBatchDownload(String... isbns) {
        try {
            for (final String isbn : isbns) {
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            processBatchDownload(isbn);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } finally {
            shutdown();
        }
    }

    private void shutdown() {
        executorService.shutdown();
        try {
            executorService.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pdfDownloader.shutdown();
    }

    private void processBatchDownload(String isbn) throws IOException {
        String url = springerProperties.getBookUrlByIsbn(isbn);

        long start = System.currentTimeMillis();
        System.out.println("Downloading and parsing contents page: " + isbn);
        Book book = htmlParser.getBookFromContentsHtml(new URL(url));
        System.out.println("Downloading PDFs: " + book.getTitle());
        tryToGetPdfsFromFileSystem(book);
        pdfDownloader.downloadPdfs(book);
        System.out.println("Writing to disk: " + book.getTitle());
        bookExporter.exportBook(book);
        System.out.println("Downloading " + book.getTitle() + " took " + (System.currentTimeMillis() - start) + " ms!");
    }

    private void tryToGetPdfsFromFileSystem(Book book) {
        for (Chapter chapter : book.getChapters()) {
            String fileName = bookExporter.getFileName(book.getTitle(), chapter);
            chapter.setPdf(SpringerFileUtils.readFilenameToByteArrayOrNull(fileName));
        }
    }


}
