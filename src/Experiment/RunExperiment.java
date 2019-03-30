package Experiment;

import DataReader.DataReader;
import algorithm.PearsonCorrelation;
import algorithm.RandomRecommender;
import algorithm.RecommendationMethod;

import java.util.*;

public class RunExperiment {
    public static void main(String[] args) {
        List<Double> ourAlgAccuracy = new ArrayList<>();
        List<Double> otherAlgAccuracy = new ArrayList<>();
//        List<Double> ourAlgRMSE = new ArrayList<>();
//        List<Double> otherAlgRMSE = new ArrayList<>();
        RecommendationMethod pc = new PearsonCorrelation();
        RecommendationMethod randomRecommender = new RandomRecommender();
        DataReader dataReader = new DataReader("resources/");
        Map<Integer, Map<Integer, Double>> rates = dataReader.getRates();
        Set<Integer> users = rates.keySet();
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            Map<Integer, Map<Integer, Double>> totalRatesForTests = new HashMap<>(rates);
            Integer user_index = random.nextInt(users.size());
            Integer user = (Integer) users.toArray()[user_index];
            Map<Integer, Double> userRates = totalRatesForTests.get(user);
            Map<Integer, Double> userRatesForTrain = new HashMap<>(userRates);
            Map<Integer, Double> userRatesForTest = new HashMap<>();
            int size = userRates.keySet().size();
            for (int j = 0; j < 0.2 * size; j++) {
                int index = random.nextInt(userRates.keySet().size());
                Integer key = (Integer) userRates.keySet().toArray()[index];
                userRatesForTest.put(key, userRates.get(key));
                userRatesForTrain.remove(key);
            }

            totalRatesForTests.remove(user);

            List<Map.Entry<Integer, Double>> topMovies = pc.getTopMovies(totalRatesForTests, userRatesForTrain);
            Double acc = getAccuracy(userRatesForTest, topMovies);
            ourAlgAccuracy.add(acc);
            double rmse = getRmse(userRatesForTest, topMovies);
//            ourAlgRMSE.add(rmse);

            topMovies = randomRecommender.getTopMovies(totalRatesForTests, userRatesForTrain);
            Double acc2 = getAccuracy(userRatesForTest, topMovies);
            otherAlgAccuracy.add(acc2);
            double rmse2 = getRmse(userRatesForTest, topMovies);
//            otherAlgRMSE.add(rmse2);
        }
        double ourAccAvg = calcAvg(ourAlgAccuracy);
//        double ourRmseAvg = calcAvg(ourAlgRMSE);
        double otherAccAvg = calcAvg(otherAlgAccuracy);
//        double otherRmseAvg = calcAvg(otherAlgRMSE);
        System.out.println("Our Algorithm accuracy: " + ourAccAvg);
//        System.out.println("Our Algorithm rmse: " + ourRmseAvg);
        System.out.println("Other Algorithm accuracy: " + otherAccAvg);
//        System.out.println("Other Algorithm rmse: " + otherRmseAvg);
    }

    private static double calcAvg(List<Double> ourAlgAccuracy) {
        return ourAlgAccuracy.stream().mapToDouble(a -> a).average().getAsDouble();
    }

    private static double getRmse(Map<Integer, Double> userRatesForTest, List<Map.Entry<Integer, Double>> topMovies) {
        double sum = 0;
        int count = 0;
        for (Map.Entry<Integer, Double> movie : topMovies) {
            if (userRatesForTest.containsKey(movie.getKey())) {
                sum += Math.pow(movie.getValue() - userRatesForTest.get(movie.getKey()).doubleValue(), 2);
                count ++;
            }
        }
        if(count == 0)
            return 0;
        return Math.sqrt(sum / count);
    }

    private static Double getAccuracy(Map<Integer, Double> userRatesForTest, List<Map.Entry<Integer, Double>> topMovies) {
        double hits = 0;
        for (Map.Entry<Integer, Double> movie : topMovies) {
            if (userRatesForTest.containsKey(movie.getKey())) {
                hits++;
            }
        }
        return hits / topMovies.size();
    }
}
