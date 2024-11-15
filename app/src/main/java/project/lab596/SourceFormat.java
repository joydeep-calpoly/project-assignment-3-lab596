package project.lab596;

import java.util.logging.Logger;


public class SourceFormat {
    
    //enum values that enable the user to specify what parsing they desire
    public enum Source {
        FILE,
        URL,
    }

    public enum Format {
        SIMPLE,
        COMPLEX,
    }

    Source source;
    Format format;

    public SourceFormat(Source source, Format format){
        this.source = source;
        this.format = format;
    }

    void acceptVisitor(NewsParser newsParser, String json, Logger log){
        newsParser.visit(json, log);
    }

    /**
    * Following are public getters to fetch SourceFormat content
    * for the purpose of determining what source format the user
    * requires to be parsed
    *
    * @return Format/Source  specified field that is requested
    */
    public Format getFormat() {
        return format;
    }

    public Source getSource() {
        return source;
    }


}
