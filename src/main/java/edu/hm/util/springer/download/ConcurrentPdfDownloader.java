package edu.hm.util.springer.download;

import edu.hm.util.springer.model.Book;
import edu.hm.util.springer.model.Chapter;
import edu.hm.util.springer.properties.SpringerProperties;
import edu.hm.util.springer.util.AbstractRunnableWithRetry;
import org.apache.commons.io.IOUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class ConcurrentPdfDownloader implements PdfDownloader {

    @Inject
    private URLToInputStreamResolver urlToInputStreamResolver;
    @Inject
    private Logger log;
    @Inject
    private SpringerProperties springerProperties;
    private ExecutorService executorService;

    @Override
    public void downloadPdfs(Book book) {
        executorService = Executors.newFixedThreadPool(springerProperties.getMaxConcurrentPdfDownloads());
        for (final Chapter chapter : book.getChapters()) {
            downloadPdfForChapter(chapter);
        }
    }

    private void downloadPdfForChapter(Chapter chapter) {
        try {
            executorService.submit(new PdfDownloadRunnable(chapter)).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void shutdown() {
        executorService.shutdown();
    }


    private class PdfDownloadRunnable extends AbstractRunnableWithRetry {

        private Chapter chapter;

        protected PdfDownloadRunnable(Chapter chapter) {
            super(springerProperties.getMaxRetries());
            this.chapter = chapter;
        }

        @Override
        protected void runInternal() {
            try {
                chapter.setPdf(downloadPdf(chapter));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private byte[] downloadPdf(Chapter chapter) throws IOException {
            byte[] pdf;
            InputStream pdfInputStream = urlToInputStreamResolver.getFromUrl(chapter.getPdfURL());
            try {
                pdf = IOUtils.toByteArray(pdfInputStream);
            } finally {
                pdfInputStream.close();
            }
            return pdf;
        }

        @Override
        protected boolean shouldRetry() {
            return chapter.getPdf() == null || chapter.getPdf().length == 0;
        }

        @Override
        protected void onFailure(int retries) {
            log.warning("'" + chapter.getName() + ".pdf' still empty after " + retries + " retries!");
        }

        @Override
        protected void onRetry(int retries) {
            log.info("retrying " + chapter.getName() + ": " + retries);
        }
    }

}
