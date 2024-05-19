package Strategy;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class BreedSearchStrategy implements DogSearchStrategy {
    private static Logger logger = Logger.getLogger("Strategy.BreedSearchStrategy logger");

    @Override
    public List<String[]> search(List<String[]> dogs, String query) {
        List<String[]> result = new ArrayList<>();
        for (String[] dog : dogs) {
            if (dog[2].equalsIgnoreCase(query)) {
                result.add(dog);
            }
        }
        logger.info("Breed search completed successfully with query: " + query);
        return result;
    }
}
