package tech.bonda.lawappserver.zDemo_For_Scraping;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
@SuppressWarnings("DataFlowIssue")
public class Demo {

    public static void main(String[] args) {
        //Scrapes books and quotes from https://toscrape.com/
        getBooks(50);
       // getQuotes(3);
    }

    private static void getBooks(int numberOfPagesToScrape) {
        String baseUrl = "https://books.toscrape.com/catalogue/page-" + "{PAGE_NUMBER}" + ".html";
        for (int i = 1; i <= numberOfPagesToScrape; i++)
        {
            String url = baseUrl.replace("{PAGE_NUMBER}", String.valueOf(i));
            try
            {
                Document doc = Jsoup.connect(url).get();
                Elements books = doc.select(".product_pod");

                for (Element product : books)
                {
                    String title = product.selectFirst("h3 a").attr("title");
                    String imageUrl = "https://books.toscrape.com" + product.selectFirst("div.image_container img").attr("src").replace("../","/");
                    System.out.println("IMAGEURL" + imageUrl);
                    String price = product.selectFirst("p.price_color").text();
                    String availability = product.selectFirst("p.availability").text();
                    if (availability.equals("In stock"))
                    {
                        availability = "Available";
                    }
                    else
                    {
                        availability = "Not Available";
                    }
                    Book book = Book.builder()
                            .title(title)
                            .imageUrl(imageUrl)
                            .price(price)
                            .availability(availability)
                            .build();
                    System.out.print(book);
                }
                System.out.println("Page " + i + " done!" + "\n" + "-------------------------");
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private static void getQuotes(int numberOfPagesToScrape) {
        String baseUrl = "https://quotes.toscrape.com/page/" + "{PAGE_NUMBER}" + "/";
        for (int i = 1; i <= numberOfPagesToScrape; i++)
        {
            String url = baseUrl.replace("{PAGE_NUMBER}", String.valueOf(i));
            try
            {
                Document doc = Jsoup.connect(url).get();
                Elements quotes = doc.select(".quote");

                for (Element quote : quotes)
                {
                    String text = quote.selectFirst("span.text").text();
                    String author = quote.selectFirst("small.author").text();
                    List<String> tags = quote.select("a.tag").stream().map(Element::text).toList();
                    Quote quoteObj = Quote.builder()
                            .quote(text)
                            .author(author)
                            .tags(tags)
                            .build();
                    System.out.print(quoteObj);
                }
                System.out.println("Page " + i + " done!" + "\n" + "-------------------------");
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}

