package project.lab596;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Utils {

    public Utils(){}

    /**
    * Method used to delegate the specific parser and file required based on the SourceFormat provided
    * 
    * @param SourceFormat Format and Source specifying the manner of parsing desired
    */
    public void callSourceFormat(SourceFormat sf) throws IOException{
        
        NewsParser cnp = new ComplexNewsParser();
        NewsParser snp = new SimpleNewsParser();

        Logger log = createLog();
        String file;
        if (sf.getFormat() == SourceFormat.Format.SIMPLE && sf.getSource() == SourceFormat.Source.FILE){
            file = new String(Files.readAllBytes(new File("src\\main\\resources\\simple.json").toPath()));
            sf.acceptVisitor(snp, file, log);
        }
        else if(sf.getFormat() == SourceFormat.Format.COMPLEX && sf.getSource() == SourceFormat.Source.FILE){
            file = new String(Files.readAllBytes(new File("src\\main\\resources\\newsapi.json").toPath()));
            sf.acceptVisitor(cnp, file, log);
        }
        else if(sf.getFormat() == SourceFormat.Format.COMPLEX && sf.getSource() == SourceFormat.Source.URL){
            file = apiCall();
            sf.acceptVisitor(cnp, file, log);
        }
        else{
            log.info("Invalid Format + Source Combo Provided");
        }
    }

    private Logger createLog(){
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

    String apiCall(){
        //to read from config file that has api key
        Properties prop = new Properties();
        try {
            prop.load(Files.newBufferedReader(Paths.get("src\\main\\java\\project\\lab596\\config\\env.config")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        String API_URL = "https://newsapi.org/v2/top-headlines?country=us&apiKey=" + prop.getProperty("NewsAPIKey");
        URL obj;

        try {
            obj = new URL(API_URL);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
}
