package project.lab596;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SimpleNewsParser implements NewsParser {

    /**
    * This method goes uses ObjectMapper to parse the json provided with jacksondbind type of SimpleNews.class
    * It then calls the provideNews helper method to print the article out
    *
    * @param Json,Logger Json string that is the format of the SimpleNews.class to get parsed
    *                    And logger specified in Driver used to log the invalid articles
    * @return List<SimpleNews> returns a list of the good article
    */
    @Override
    public List<SimpleNews> parseNews(String json, Logger logger) {
        ObjectMapper mapper = new ObjectMapper();
        try{
            SimpleNews news1 = mapper.readValue(json, SimpleNews.class);
            return(provideNews(news1, logger));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
        
    }


    /**
    * This method goes through the article parsed and prints it out if it is valid
    * while adding it to the goodArticles list. It will log the article if it is bad
    *
    * @param SimpleNews,Logger SimpleNews object that is what JacksonBind parses and places in SimpleNews object
    *                          And logger specified in parseNews used to log the invalid article
    * @return List<SimpleNews> returns either a single list of the good article or any empty array if the article is bad
    */
    private List<SimpleNews> provideNews(SimpleNews news, Logger logger){
        
        if (news.isValid()) {
            System.out.println();
            System.out.println("#################################");
            System.out.println(String.format("> Article Title: %s", news.getTitle()));
            System.out.println(String.format("> Description: %s", news.getDescription()));
            System.out.println(String.format("> Published Date: %s", news.getPublishedAt()));
            System.out.println(String.format("> Url: %s", news.getUrl()));
            return Collections.singletonList(news);
        } else {
            logger.warning(news.toString());
        }
        return Collections.emptyList();
    }

    /**
    * This is a visit method that simply calls the parseNews method
    *
    * @param Json,Logger Json string that is the format of the SimpleNews.class to get parsed
    *                    And logger specified in Driver used to log the invalid articles
    */
    @Override
    public void visit(String json, Logger logger) {
        parseNews(json, logger);
    }
    
}
