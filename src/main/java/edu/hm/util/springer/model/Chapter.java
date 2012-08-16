package edu.hm.util.springer.model;

import java.io.File;
import java.net.URL;

public class Chapter {

    private Book book;
    private String name;
    private URL pdfURL;
    private byte[] pdf;
    private File file;
    private int chapterNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URL getPdfURL() {
        return pdfURL;
    }

    public void setPdfURL(URL pdfURL) {
        this.pdfURL = pdfURL;
    }

    public byte[] getPdf() {
        return pdf;
    }

    public void setPdf(byte[] pdf) {
        this.pdf = pdf;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(int chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "book=" + book +
                ", name='" + name + '\'' +
                ", pdfURL=" + pdfURL +
                ", pdf=" + pdf +
                ", file=" + file +
                ", chapterNumber=" + chapterNumber +
                '}';
    }
}
