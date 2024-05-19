package Strategy;

import java.util.List;

public interface DogSearchStrategy {
    List<String[]> search(List<String[]> dogs, String query);
}
