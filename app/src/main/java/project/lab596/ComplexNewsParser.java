package project.lab596;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import project.lab596.News.Article;

public class ComplexNewsParser implements NewsParser{

    /**
    * This method goes uses ObjectMapper to parse the json provided with jacksondbind type of News.class
    * It then calls the provideNews helper method to print the articles out
    *
    * @param Josn,Logger Json string that is the format of the News.class to get parsed
    *                    And logger specified in Driver used to log the invalid articles
    * @return List<Article> returns a list of the good articles
    */
    @Override
    public List<Article> parseNews(String json, Logger logger){
        ObjectMapper mapper = new ObjectMapper();
        //Allows Instant to be parsed
        mapper.registerModule(new JavaTimeModule());

        try{
            News news1 = mapper.readValue(json, News.class);
            return(provideNews(news1, logger));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    /**
    * This method goes through all articles parsed, simultaneously printing out good article
    * and adding them to the goodArticles list and also logging inValid articles
    *
    * @param News,Logger News object that is what JacksonBind parses and places in News object
    *                    And logger specified in parseNews used to log the invalid articles
    * @return List<Article> returns a list of the good articles
    */
    private List<Article> provideNews(News news, Logger logger){
        List<Article> articles = news.getArticles();
        List<Article> goodArticles = new ArrayList<>();

        for (Article article : articles){
            if(article.isValid()){
                System.out.println();
                System.out.println("#################################");
                System.out.println(String.format("> Article Title: %s", article.getTitle()));
                System.out.println(String.format("> Description: %s", article.getDescription()));
                System.out.println(String.format("> Published Date: %s", article.getPublishedAt()));
                System.out.println(String.format("> Url: %s", article.getUrl()));
                goodArticles.add(article);
            }
            else{
                logger.warning(article.toString());
            }
        }
        return goodArticles;
    }
    
}
