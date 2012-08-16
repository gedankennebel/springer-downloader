package edu.hm.util.springer.parser;

import com.google.inject.ImplementedBy;
import edu.hm.util.springer.model.Book;

import java.io.IOException;
import java.net.URL;

@ImplementedBy(SpringerJsoupHtmlParser.class)
public interface HtmlParser {

    Book getBookFromContentsHtml(URL contentsHtml) throws IOException;
}
