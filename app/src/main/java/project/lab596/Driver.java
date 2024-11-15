package project.lab596;

import java.io.IOException;

public class Driver {
    public static void main(String[] args){

        Utils util = new Utils();

        SourceFormat sf1 = new SourceFormat(SourceFormat.Source.FILE, SourceFormat.Format.SIMPLE);
        SourceFormat sf2 = new SourceFormat(SourceFormat.Source.FILE, SourceFormat.Format.COMPLEX);
        SourceFormat sf3 = new SourceFormat(SourceFormat.Source.URL, SourceFormat.Format.COMPLEX);
        
        try {
            util.callSourceFormat(sf3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
