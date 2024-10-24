package project.lab596;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import project.lab596.News.Article;

class NewsTest {

    private static Logger logger;
    
    @BeforeAll
    static void setUp() throws Exception {
        // Set up logger to capture invalid articles
        logger = Logger.getLogger(News.class.getName());
        FileHandler fileHandler = new FileHandler("src\\test\\resources\\invalid_articles_test.log");
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);
        logger.setUseParentHandlers(false);
    }

    @Test
    void testValidNewsParsingSingle() throws IOException {

        String content = new String(Files.readAllBytes(new File("src/test/resources/valid_news.json").toPath()));

        List<Article> goodArticles = Parser.parseNews(content, logger);

        assertNotNull(goodArticles);
        assertEquals(1, goodArticles.size());
        assertTrue(goodArticles.get(0).isValid());
        String toString = "Test1 Test";
        assertEquals(toString, goodArticles.get(0).toString());    
    }

    @Test
    void testValidNewsParsingfromString() {
        String json = "{\r\n" + //
                        "    \"status\": \"ok\",\r\n" + //
                        "    \"totalResults\": 1,\r\n" + //
                        "    \"articles\": [\r\n" + //
                        "      {\r\n" + //
                        "        \"source\": {\r\n" + //
                        "          \"id\": \"Test\",\r\n" + //
                        "          \"name\": \"Test\"\r\n" + //
                        "        },\r\n" + //
                        "        \"author\": \"Test Guy\",\r\n" + //
                        "        \"title\": \"Test-Article\",\r\n" + //
                        "        \"description\": \"Test description of an article.\",\r\n" + //
                        "        \"url\": \"https://www.test.com\",\r\n" + //
                        "        \"urlToImage\": \"https://test.jpg\",\r\n" + //
                        "        \"publishedAt\": \"2005-03-24T22:35:00Z\",\r\n" + //
                        "        \"content\": \"Test content\"\r\n" + //
                        "      }\r\n" + //
                        "    ]\r\n" + //
                        "}";
        
        List<Article> goodArticles = Parser.parseNews(json, logger);

        assertEquals(1, goodArticles.size());
        assertTrue(goodArticles.get(0).isValid());
        String toString = "Test Test";
        assertEquals(toString, goodArticles.get(0).toString());    
    }

    @Test
    void EmptyString() {
        String json = "{\r\n" + //
                        "}";
        
        List<Article> goodArticles = Parser.parseNews(json, logger);

        assertEquals(0, goodArticles.size());
        assertTrue(goodArticles.isEmpty());
    }

    @Test
    void testValidNewsParsingMultiple() throws IOException {

        String content = new String(Files.readAllBytes(new File("src/test/resources/valid_news2.json").toPath()));

        List<Article> goodArticles = Parser.parseNews(content, logger);

        assertNotNull(goodArticles);
        assertEquals(4, goodArticles.size());

        //go through each article and if a specific article is not valid print its id for debugging
        goodArticles.stream().forEach(article -> assertTrue(article.isValid(),
            String.format("Article failed validation: %s", article.toString())));
    }

    @Test
    void testInvalidNewsParsingSingle() throws IOException {

        String content = new String(Files.readAllBytes(new File("src/test/resources/invalid_news.json").toPath()));

        List<Article> goodArticles = Parser.parseNews(content, logger);

        assertEquals(0, goodArticles.size());
    }

    @Test
    void testMultipleSources() throws IOException {

        String content = new String(Files.readAllBytes(new File("src/test/resources/mixed_news.json").toPath()));

        List<Article> goodArticles = Parser.parseNews(content, logger);

        assertEquals(5, goodArticles.size());
        
        //go through each article and if a specific article is not valid print its id for debugging
        goodArticles.stream().forEach(article -> assertTrue(article.isValid(),
            String.format("Article failed validation: %s", article.toString())));
    }
}
