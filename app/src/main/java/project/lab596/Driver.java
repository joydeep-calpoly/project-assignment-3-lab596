package project.lab596;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Driver {
    public static void main(String[] args){
        Logger log = createLog();
        String file;
        try {
            file = new String(Files.readAllBytes(new File("src\\main\\resources\\bad.json").toPath()));
            Parser.parseNews(file, log);
            file = new String(Files.readAllBytes(new File("src\\main\\resources\\example.json").toPath()));
            Parser.parseNews(file, log);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static Logger createLog(){
        //Set up logger for bad articles
        try{
            Logger logger = Logger.getLogger(News.class.getName());
            FileHandler filehandler = new FileHandler("src\\main\\resources\\invalid_articles.log");
            filehandler.setFormatter(new SimpleFormatter());
            logger.addHandler(filehandler);
            logger.setLevel(Level.WARNING);
            return logger;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
