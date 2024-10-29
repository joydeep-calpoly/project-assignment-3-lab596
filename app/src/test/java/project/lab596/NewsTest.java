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
    
    /**
    * Setting up the logger for each of the tests
    */
    @BeforeAll
    static void setUp() throws Exception {
        // Set up logger to capture invalid articles
        logger = Logger.getLogger(News.class.getName());
        FileHandler fileHandler = new FileHandler("src\\test\\resources\\invalid_articles_test.log");
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);
        logger.setUseParentHandlers(false);
    }

    
    /**
     * Parsing a single valid complex article from json and ensure that it is valid and returned properly
     * @result A complex news parser can parse a single valid article
     */
    @Test
    void testValidNewsParsingSingle() throws IOException {

        String content = new String(Files.readAllBytes(new File("src/test/resources/valid_news.json").toPath()));
        ComplexNewsParser cnp = new ComplexNewsParser();

        List<Article> goodArticles = cnp.parseNews(content, logger);

        assertNotNull(goodArticles);
        assertEquals(1, goodArticles.size());
        assertTrue(goodArticles.get(0).isValid());
        String toString = "Test1 Test";
        assertEquals(toString, goodArticles.get(0).toString());    
    }

    /**
     * Parsing a single valid complex article from string and ensure it is valid and returned properly
     * @result A complex news parser can parse a single valid article from a string like format
     */
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
        
        ComplexNewsParser cnp = new ComplexNewsParser();

        List<Article> goodArticles = cnp.parseNews(json, logger);

        assertEquals(1, goodArticles.size());
        assertTrue(goodArticles.get(0).isValid());
        String toString = "Test Test";
        assertEquals(toString, goodArticles.get(0).toString());    
    }

    /**
     * Parsing an empty string to ensure that we get an empty array of good articles
     * @result Ensures that the empty string edge case returns an empty list of good articles
     */
    @Test
    void EmptyString() {
        String json = "{\r\n" + //
                        "}";
        
        ComplexNewsParser cnp = new ComplexNewsParser();

        List<Article> goodArticles = cnp.parseNews(json, logger);

        assertEquals(0, goodArticles.size());
        assertTrue(goodArticles.isEmpty());
    }

    /**
     * Parsing multiple valid articles and ensure that all of them are good articles and validated properly
     * @result Ensures that ComplexParser can deal with multiple articles at the same time that are valid
     */
    @Test
    void testValidNewsParsingMultiple() throws IOException {

        String content = new String(Files.readAllBytes(new File("src/test/resources/valid_news2.json").toPath()));

        ComplexNewsParser cnp = new ComplexNewsParser();

        List<Article> goodArticles = cnp.parseNews(content, logger);

        assertNotNull(goodArticles);
        assertEquals(4, goodArticles.size());

        //go through each article and if a specific article is not valid print its id for debugging
        goodArticles.stream().forEach(article -> assertTrue(article.isValid(),
            String.format("Article failed validation: %s", article.toString())));
    }

     /**
     * Parsing a single bad article and making sure that no articles are present in good articles
     * @result Ensures that ComplexParser can deal with bad articles
     */
    @Test
    void testInvalidNewsParsingSingle() throws IOException {

        String content = new String(Files.readAllBytes(new File("src/test/resources/invalid_news.json").toPath()));

        ComplexNewsParser cnp = new ComplexNewsParser();

        List<Article> goodArticles = cnp.parseNews(content, logger);

        assertEquals(0, goodArticles.size());
    }

    
    /**
     * Testing a mix of good and bad articles and making sure that the correct number of good articles are present
     * and that the articles placed within good articles are in fact valid
     * @result Ensures that ComplexParser can deal with a mix of good and bad articles
     */
    @Test
    void testMultipleSources() throws IOException {

        String content = new String(Files.readAllBytes(new File("src/test/resources/mixed_news.json").toPath()));

        ComplexNewsParser cnp = new ComplexNewsParser();

        List<Article> goodArticles = cnp.parseNews(content, logger);

        assertEquals(5, goodArticles.size());
        
        //go through each article and if a specific article is not valid print its id for debugging
        goodArticles.stream().forEach(article -> assertTrue(article.isValid(),
            String.format("Article failed validation: %s", article.toString())));
    }
}
