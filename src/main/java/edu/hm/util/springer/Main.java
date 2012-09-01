package edu.hm.util.springer;

import com.google.inject.Guice;
import com.google.inject.Injector;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        printInfo();
        if (args.length == 0 || "-help".equals(args[0])) {
            printHelp();
            System.exit(0);
        }

        Injector injector = Guice.createInjector();
        Processor processor = injector.getInstance(Processor.class);

        processor.processBatchDownload(args);
    }

    private static void printInfo() {
        System.out.println("###################################################");
        System.out.println("## springer-downloader 1.0.1                     ##");
        System.out.println("## Batch download all chapters of multiple books ##");
        System.out.println("###################################################");
        System.out.println();
    }

    private static void printHelp() {
        System.out.println("1. Find the books of your interest at http://www.springerlink.com");
        System.out.println("2. Be sure that you have established a vpn connection to the lrz");
        System.out.println("   (Don't forget, to put a '!' in front of your username)");
        System.out.println("3. Provide the ISBNs of the books you want to download as an argument");
        System.out.println("   (you can find the ISBN in the URL e.g. 978-3-540-36631-7 for the book at http://www.springerlink.com/content/978-3-540-36631-7)");
        System.out.println("4. The books will be downloaded into new folders in the current directory containing all chapters");
        System.out.println("   (If you have already downloaded parts of the book, they won't be downloaded again)");
    }
}
