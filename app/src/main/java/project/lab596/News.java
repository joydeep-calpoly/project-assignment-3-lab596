package project.lab596;

import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class News {
    static class Article {
        private static class Source {
            private String id;
            private String name;

            @JsonCreator
            private Source(@JsonProperty("id") String id,
                    @JsonProperty("name") String name) {
                this.id = id;
                this.name = name;
            }
        }

        private String author;
        private String title;
        private String description;
        private URL url;
        private URL urlToImage;
        private Instant publishedAt;
        private String content;
        private Source source;

        @JsonCreator
        private Article(@JsonProperty("author") String author,
                @JsonProperty("title") String title,
                @JsonProperty("description") String description,
                @JsonProperty("url") URL url,
                @JsonProperty("urlToImage") URL urlToImage,
                @JsonProperty("publishedAt") Instant publishedAt,
                @JsonProperty("content") String content,
                @JsonProperty("source") Source source) {
            this.author = author;
            this.title = title;
            this.description = description;
            this.url = url;
            this.urlToImage = urlToImage;
            this.publishedAt = publishedAt;
            this.content = content;
            this.source = source;
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
        * Following are public getters to fetch essential article content
        * for the purpose of printing articles in the Parser class. Printing
        * is done in the parser class to ensure logging simultaniously
        *
        * @return String/Instant/URL  specified field that is requested
        */
        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public Instant getPublishedAt() {
            return  publishedAt;
        }

        public URL getUrl() {
            return url;
        }

        /**
        * An override of the toString method specifically to log an article
        * that is missing esential content
        *
        * @return String  A string containing the id and name of the article
        *                 in order to easily identify a article with missing content
        */
        @Override
        public String toString() {
            return source.id + " " + source.name;
        }
    }

    private String status;
    private int totalResults;
    private ArrayList<Article> articles;

    @JsonCreator
    public News(@JsonProperty("status") String status,
            @JsonProperty("totalResults") int totalResults,
            @JsonProperty("articles") List<Article> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = (ArrayList<Article>) articles;
    }

    /**
    * A getter for the articles parsed via JacksonBind. Note a clone is returned
    * to prevent mutability of the class specified object
    *
    * @return List  A list containing all articles parsed via JacksonBind
    */
    public List<Article> getArticles() {
        return (List<Article>) articles.clone();
    }
}
