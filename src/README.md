# Springer Downloader

## Build
spring-downloader is built with maven. Just run the maven package command to build the jar


## Usage

The springer-downloader allows you to batch download all chapters of multiple books

1. Find the books of your interest at http://www.springerlink.com
2. Be sure that you have established a vpn connection to the lrz
   (Don't forget, to put a '!' in front of your username)
3. Provide the ISBNs of the books you want to download as an argument
   (you can find the ISBN in the URL e.g. 978-3-540-36631-7 for the book at http://www.springerlink.com/content/978-3-540-36631-7)
4. The books will be downloaded into new folders in the current directory containing all chapters
   (If you have already downloaded parts of the book, they won't be downloaded again)

Example: java -jar springer-downloader.jar 978-3-540-36631-7