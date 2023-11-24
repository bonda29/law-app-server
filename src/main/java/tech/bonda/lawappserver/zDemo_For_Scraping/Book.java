package tech.bonda.lawappserver.zDemo_For_Scraping;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Book {
    private String title;
    private String imageUrl;
    private String price;
    private String availability;

    @Override
    public String toString() {
        return String.format("Title: %s | " + "Image URL: %s | " + "Price: %s | " + "Availability: %s\n",
                title, imageUrl, price, availability);
    }
}
