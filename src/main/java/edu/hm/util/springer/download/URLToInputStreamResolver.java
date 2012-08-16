package edu.hm.util.springer.download;

import com.google.inject.ImplementedBy;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


@ImplementedBy(FakeUserAgentDownloader.class)
public interface URLToInputStreamResolver {
    InputStream getFromUrl(URL url) throws IOException;
}
