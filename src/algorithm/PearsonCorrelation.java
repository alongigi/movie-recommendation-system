package algorithm;


import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import java.util.*;


public class PearsonCorrelation implements RecommendationMethod {
    private final static PearsonsCorrelation PC = new PearsonsCorrelation();

    private double getCorrelation(double[] userA, double[] userB) {
        return Math.abs(Correlation(userA, userB));
    }
////userrating
    //   Map<Integer, Map<Integer, Double>> //all users


    @Override
    public List<Map.Entry<Integer, Double>> getTopMovies(Map<Integer, Map<Integer, Double>> users, Map<Integer, Double> newUser) {
        PriorityQueue<UserRating> pq = new PriorityQueue<>((o1, o2) -> Double.compare(o1.getCorrelation(), o2.getCorrelation()));
        double maxCorrelation = 0;
        for (Map.Entry<Integer, Map<Integer, Double>> u : users.entrySet()) {
            Set<Integer> matchMovies = new HashSet<Integer>(newUser.keySet());
            matchMovies.retainAll(u.getValue().keySet());
            double[] newUserRating = new double[matchMovies.size()];
            double[] oldUserRating = new double[matchMovies.size()];
            int counter = 0;
            for (Integer m : matchMovies) {
                newUserRating[counter] = newUser.get(m);
                oldUserRating[counter] = u.getValue().get(m);
                counter++;
            }
            if (matchMovies.size() < 3)
                continue;
            double correlation = getCorrelation(newUserRating, oldUserRating);
            pq.add(new UserRating(u.getValue(), u.getKey(), correlation));

        }
        List<Map.Entry<Integer, Double>> topMovies = new ArrayList();
        while (topMovies.size() < 10) {
            UserRating temp = pq.poll();
            Map<Integer, Double> tempRates = temp.getRates();
            for (Map.Entry<Integer, Double> e : tempRates.entrySet()) {
                if (!newUser.keySet().contains(e.getKey()))
                    topMovies.add(e);
                if (topMovies.size() >= 10)
                    break;
            }
        }
        return topMovies;
    }


    public static double Correlation(double[] xs, double[] ys) {
        //TODO: check here that arrays are not null, of the same length etc

        double sx = 0.0;
        double sy = 0.0;
        double sxx = 0.0;
        double syy = 0.0;
        double sxy = 0.0;

        int n = xs.length;

        for (int i = 0; i < n; ++i) {
            double x = xs[i];
            double y = ys[i];

            sx += x;
            sy += y;
            sxx += x * x;
            syy += y * y;
            sxy += x * y;
        }

        // covariation
        double cov = sxy / n - sx * sy / n / n;
        // standard error of x
        double sigmax = Math.sqrt(sxx / n - sx * sx / n / n);
        // standard error of y
        double sigmay = Math.sqrt(syy / n - sy * sy / n / n);

        // correlation is just a normalized covariation
        return cov / sigmax / sigmay;
    }
}