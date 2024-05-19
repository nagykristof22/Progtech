package Strategy;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class AgeSearchStrategy implements DogSearchStrategy {
    private static Logger logger = Logger.getLogger("Strategy.AgeSearchStrategy logger");

    @Override
    public List<String[]> search(List<String[]> dogs, String query) {
        List<String[]> result = new ArrayList<>();
        try {
            int ageQuery = Integer.parseInt(query);
            for (String[] dog : dogs) {
                int age = Integer.parseInt(dog[3]);
                if (age == ageQuery) {
                    result.add(dog);
                }
            }
            logger.info("Age search completed successfully with query: " + query);
        } catch (NumberFormatException e) {
            logger.error("Invalid age input: " + query, e);
        }
        return result;
    }
}
