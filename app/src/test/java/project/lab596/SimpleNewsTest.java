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

public class SimpleNewsTest {

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
     * Parsing a valid simple news from json and ensure that it is valid and returned properly
     * @result Ensures that SimpleParser can deal with a good input
     */
    @Test
    void testValidNewsParsing() throws IOException {

        String content = new String(Files.readAllBytes(new File("src/test/resources/valid_simple_news.json").toPath()));
        SimpleNewsParser snp = new SimpleNewsParser();

        List<SimpleNews> goodArticles = snp.parseNews(content, logger);

        assertNotNull(goodArticles);
        assertEquals(1, goodArticles.size());
        assertTrue(goodArticles.get(0).isValid()); 
    }

    /**
     * Parsing a invalid simple news from json and ensure that an empty collection is returned
     * @result Ensures that ComplexParser can deal with a bad input
     */
    @Test
    void testInvalidNewsParsing() throws IOException {

        String content = new String(Files.readAllBytes(new File("src/test/resources/invalid_simple_news.json").toPath()));
        SimpleNewsParser snp = new SimpleNewsParser();

        List<SimpleNews> goodArticles = snp.parseNews(content, logger);

        assertNotNull(goodArticles);
        assertEquals(0, goodArticles.size());
    }

    /**
     * Parsing a invalid simple news from json and ensure that an empty collection is returned
     * @result Ensures that ComplexParser can deal with a bad input but different missing field
     */
    @Test
    void testInvalidNewsParsing2() throws IOException {

        String content = new String(Files.readAllBytes(new File("src/test/resources/invalid_simple_news2.json").toPath()));
        SimpleNewsParser snp = new SimpleNewsParser();

        List<SimpleNews> goodArticles = snp.parseNews(content, logger);

        assertNotNull(goodArticles);
        assertEquals(0, goodArticles.size());
    }

     /**
     * Parsing a valid simple news from json and ensure that it is valid and returned properly from string
     * @result Ensures that SimpleParser can deal with a good input from String input
     */
    @Test
    void testValidNewsParsingfromString() throws IOException {

        String content = "{\r\n" + //
                        "    \"description\": \"Test description.\",\r\n" + //
                        "    \"publishedAt\": \"2001-04-16 09:53:23.709229\",\r\n" + //
                        "    \"title\": \"Test\",\r\n" + //
                        "    \"url\": \"https://canvas.calpoly.edu/courses/55411/assignments/274503\"\r\n" + //
                        "}";
        SimpleNewsParser snp = new SimpleNewsParser();

        List<SimpleNews> goodArticles = snp.parseNews(content, logger);

        assertNotNull(goodArticles);
        assertEquals(1, goodArticles.size());
        assertTrue(goodArticles.get(0).isValid()); 
    }

    /**
     * Parsing an empty simple news from json and ensure that it not valid
     * @result Ensures that SimpleParser can deal with a bad input from String input
     */
    @Test
    void testEmptyParsingfromString() throws IOException {

        String content = "{\r\n" + //
                        "    \"description\": \"Test description.\",\r\n" + //
                        "}";
        SimpleNewsParser snp = new SimpleNewsParser();

        List<SimpleNews> goodArticles = snp.parseNews(content, logger);

        assertNotNull(goodArticles);
        assertEquals(0, goodArticles.size());
    }
    
}
