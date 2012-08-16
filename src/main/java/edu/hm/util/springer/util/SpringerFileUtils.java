package edu.hm.util.springer.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public final class SpringerFileUtils {

    private SpringerFileUtils() {
    }

    public static String escapeForFileSystem(String string) {
        char[] prohibitedChars = new char[]{'\\', '/', ':', '<', '>', '*', '?', '|', '"'};
        for (char prohibitedChar : prohibitedChars) {
            string = string.replace(prohibitedChar, '-');
        }
        return string;
    }

    public static boolean doesFileExistAndHaveContent(String filename) {
        return !isEmpty(readFilenameToByteArrayOrNull(filename));
    }

    public static boolean isEmpty(byte[] bytes) {
        return bytes == null || bytes.length == 0;
    }

    public static byte[] readFilenameToByteArrayOrNull(String filename) {
        try {
            return FileUtils.readFileToByteArray(new File(filename));
        } catch (IOException e) {
            return null;
        }
    }
}
