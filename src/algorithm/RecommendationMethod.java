package algorithm;

import java.util.List;
import java.util.Map;

public interface RecommendationMethod {
    List<Map.Entry<Integer, Double>> getTopMovies(Map<Integer, Map<Integer, Double>> users, Map<Integer, Double> newUser);
}
