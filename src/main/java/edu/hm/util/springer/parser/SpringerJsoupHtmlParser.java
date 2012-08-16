package edu.hm.util.springer.parser;

import edu.hm.util.springer.download.URLToInputStreamResolver;
import edu.hm.util.springer.model.Book;
import edu.hm.util.springer.model.Chapter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.inject.Inject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SpringerJsoupHtmlParser implements HtmlParser {

    @Inject
    private URLToInputStreamResolver urlToInputStreamResolver;

    public Book getBookFromContentsHtml(URL contentsHtml) throws IOException {
        Document doc = Jsoup.parse(urlToInputStreamResolver.getFromUrl(contentsHtml), "UTF-8", "http://www.springerlink.com");

        Book book = new Book();
        book.setTitle(getTitle(doc));
        book.setSubtitle(getSubtitleOrNull(doc));
        book.getChapters().addAll(getBookChapters(doc));

        return book;
    }

    private String getTitle(Document doc) {
        Element titleElement = getTitleElement(doc);

        String titleWithSubtitle = titleElement.text();
        return removeSubtitleIfExists(doc, titleWithSubtitle);
    }

    private Element getTitleElement(Document doc) {
        Element contentHeading = doc.getElementById("ContentHeading");
        return contentHeading.getElementsByAttributeValueStarting("title", "Link to B").get(0);
    }

    private String removeSubtitleIfExists(Document doc, String titleWithSubtitle) {
        String titleWithoutSubtitle = titleWithSubtitle;

        String subtitle = getSubtitleOrNull(doc);
        if (subtitle != null) {
            titleWithoutSubtitle = titleWithSubtitle.substring(0, titleWithSubtitle.indexOf(subtitle)).trim();
        }
        return titleWithoutSubtitle;
    }

    private String getSubtitleOrNull(Document doc) {
        Elements subtitleElement = getSubtitleElement(doc);

        String subtitle = null;
        if (!subtitleElement.isEmpty()) {
            subtitle = subtitleElement.get(0).text();
        }
        return subtitle;

    }

    private Elements getSubtitleElement(Document doc) {
        Element titleElement = getTitleElement(doc);
        return titleElement.getElementsByClass("subtitle");
    }

    private List<Chapter> getBookChapters(Document doc) throws MalformedURLException {
        List<Chapter> result = new ArrayList<Chapter>();
        List<Element> allChapterElements = getAllChapterElements(doc);
        for (int i = 0, allChapterElementsSize = allChapterElements.size(); i < allChapterElementsSize; i++) {
            Element element = allChapterElements.get(i);
            result.add(convertChapterElementToBookChapter(element, i));
        }
        return result;
    }

    private List<Element> getAllChapterElements(Document doc) {
        List<Element> result = new ArrayList<Element>();
        Element contentElement = doc.getElementById("ContentPrimary");
        // add first frontmatter
        for (Element element : contentElement.getElementsByClass("Frontmatter")) {
            result.add(element);
            break;
        }
        result.addAll(contentElement.getElementsByClass("bookChapter"));
        result.addAll(contentElement.getElementsByClass("Backmatter"));

        return result;
    }

    private Chapter convertChapterElementToBookChapter(Element element, int chapterNumber) throws MalformedURLException {
        Chapter chapter = new Chapter();
        Elements pdf = element.getElementsByClass("pdf-resource-sprite");
        Element chapterElement = element.getElementsByClass("title").get(0);
        chapter.setName(chapterElement.text());
        chapter.setPdfURL(new URL(pdf.attr("abs:href")));
        chapter.setChapterNumber(chapterNumber);
        return chapter;
    }
}
