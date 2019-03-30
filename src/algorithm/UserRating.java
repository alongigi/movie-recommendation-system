package algorithm;

import java.util.Map;

public class UserRating {
    private Map<Integer, Double> rates;
    private int ID;
    private double correlation;

    public UserRating(Map<Integer, Double> rates, int ID, double correlation) {
        this.rates = rates;
        this.ID = ID;
        this.correlation = correlation;
    }

    public Map<Integer, Double> getRates() {
        return rates;
    }

    public int getID() {
        return ID;
    }

    public double getCorrelation() {
        return correlation;
    }

    public void setRates(Map<Integer, Double> rates) {
        this.rates = rates;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setCorrelation(double correlation) {
        this.correlation = correlation;
    }
}
