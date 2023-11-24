package tech.bonda.lawappserver.zDemo_For_Scraping;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Quote {
    private String quote;
    private String author;
    private List<String> tags;

    @Override
    public String toString() {
        return String.format("Quote: %s | " + "Author: %s | " + "Tags: %s\n",
                quote, author, tags);
    }

}
