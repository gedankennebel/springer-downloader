package edu.hm.util.springer.download;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


public class FakeUserAgentDownloader implements URLToInputStreamResolver {

    public InputStream getFromUrl(URL url) throws IOException {
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
        return urlConnection.getInputStream();
    }


}
