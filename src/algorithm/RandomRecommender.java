package algorithm;

import DataReader.*;
import view.MovieEntry;

import java.util.*;

public class RandomRecommender implements RecommendationMethod {
    @Override
    public List<Map.Entry<Integer, Double>> getTopMovies(Map<Integer, Map<Integer, Double>> users, Map<Integer, Double> newUser) {
        DataReader dataReader = new DataReader("resources/");

        Map<Integer, Movie> movies = dataReader.getMovies();
        List<MovieEntry> entries = new ArrayList<>();
        Random random = new Random();
        List<Integer> movieIds = new ArrayList<Integer>(movies.keySet());
        List<Map.Entry<Integer, Double>> result = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Integer movie = movieIds.get(random.nextInt(movieIds.size()));
            result.add(new AbstractMap.SimpleEntry<Integer, Double>(movie, random.nextDouble()));
        }
        return result;
    }
}
