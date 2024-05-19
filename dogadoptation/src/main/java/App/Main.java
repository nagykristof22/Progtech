package App;

import org.apache.log4j.Logger;
import Index.IndexPage;


public class Main {
    private static Logger logger = Logger.getLogger("App.Main logger");
    public static void main(String[] args){
        IndexPage form = new IndexPage();
        logger.info("Index page start");
    }

}