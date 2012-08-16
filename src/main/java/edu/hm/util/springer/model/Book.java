package edu.hm.util.springer.model;

import java.util.ArrayList;
import java.util.List;

public class Book {

    private String title;
    private String subtitle;
    private List<Chapter> chapters = new ArrayList<Chapter>();

    public Book() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
