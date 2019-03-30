package DataReader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class DataReader {
    private final String ratesPath = "ratings.csv";
    private final String moviePath = "movies.csv";
    private Map<Integer, Map<Integer, Double>> rates;
    public static Map<Integer, Movie> movies;// i changed to static and public kill me
    public Map<Integer, Integer> links;// changed to public

    public DataReader(String dataPath) {
        loadRates(dataPath);
        loadMovies(dataPath);

        links = new HashMap<>();
        CSVParser parser;

        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(dataPath + "links.csv"));
            parser = CSVParser.parse(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase()
                    .withTrim());
            for (CSVRecord csvRecord : parser) {
                int movieId = Integer.parseInt(csvRecord.get("movieId"));
                try {

                    int tmbdId = Integer.parseInt(csvRecord.get("tmdbId").trim());
                    links.put(movieId, tmbdId);
                }
                catch (Exception e){

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadMovies(String dataPath) {
        movies = new HashMap<>();
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(dataPath + moviePath));
            CSVParser parser = CSVParser.parse(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase()
                    .withTrim());
            for (CSVRecord csvRecord : parser) {
                int movieId = Integer.parseInt(csvRecord.get("movieId"));
                String title = csvRecord.get("title");
                String[] genres = csvRecord.get("genres").replace('|', ',').split(",");
                Movie m = new Movie(title, genres);
                movies.put(movieId, new Movie(title, genres));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadRates(String dataPath) {
        rates = new HashMap<>();
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get(dataPath + ratesPath));
            CSVParser parser = CSVParser.parse(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase()
                    .withTrim());
            for (CSVRecord csvRecord : parser) {
                int userId = Integer.parseInt(csvRecord.get("userId"));
                int movieId = Integer.parseInt(csvRecord.get("movieId"));
                double rating = Double.parseDouble(csvRecord.get("rating"));
                if(!rates.containsKey(userId))
                    rates.put(userId, new HashMap<>());
                rates.get(userId).put(movieId, rating);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public Map<Integer, Map<Integer, Double>> getRates() {
        return rates;
    }

    public Map<Integer, Movie> getMovies() {
        return movies;
    }

    public Map<Integer, Integer> getLinks() {
        return links;
    }
}
