package project.lab596;

import java.util.List;
import java.util.logging.Logger;

public interface NewsParser {
    
    //news parse function that takes the json it needs to parse and the logger and returns either the single simple news or
    //list of complex articles
    <T> List<T> parseNews(String json, Logger logger);

    void visit(String json, Logger logger);
}
