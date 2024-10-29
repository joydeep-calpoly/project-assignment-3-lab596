package project.lab596;

import java.net.URL;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleNews {

    private String description;
    private String publishedAt;
    private String title;
    private URL url;
    
    @JsonCreator
    public SimpleNews(@JsonProperty("description") String description,
            @JsonProperty("publishedAt") String publishedAt,
            @JsonProperty("title") String title,
            @JsonProperty("url") URL url) {
        this.description = description;
        this.publishedAt = publishedAt;
        this.title = title;
        this.url = url;
    }
    
    /**
    *  Verifies that an article is considered valid by ensuring that
    *  the essential fields are not null
    *
    * @return boolean  whether or not the article is valid depending on
    *                  the essential fields are present
    */
    boolean isValid(){
        return title != null && description != null && publishedAt != null && url != null;
    }

    /**
    * An override of the toString method specifically to log an article
    * that is missing essential content
    *
    * @return String  A string containing the id and name of the article
    *                 in order to easily identify a article with missing content
    */
    @Override
    public String toString() {
        return title + " " + publishedAt;
    }

    /**
    * Following are public getters to fetch essential SimpleNews content
    * for the purpose of printing articles in the SimpleNewsParser class. Printing
    * is done in the parser class to ensure logging simultaniously
    *
    * @return String/Instant/URL  specified field that is requested
    */
    public String getDescription() {
        return description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public URL getUrl() {
        return url;
    }
}
