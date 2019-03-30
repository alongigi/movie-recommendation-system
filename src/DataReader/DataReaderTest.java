package DataReader;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class DataReaderTest {

    private DataReader dataReader;

    @Before
    public void setUp() throws Exception {
        dataReader = new DataReader("resources/");
    }

    @Test
    public void readRates(){
        Map<Integer, Map<Integer, Double>> usersMovieRates =  dataReader.getRates();
        assertEquals(usersMovieRates.get(1).size(), 27);
        assertEquals(usersMovieRates.get(4).size(), 62);
        assertEquals(usersMovieRates.get(12).get(1354), 5.0, 0.0001);

    }

    @Test
    public void readMovies(){
        Map<Integer, Movie> movies = dataReader.getMovies();
        assertEquals(movies.size(), 45843);
        assertEquals(movies.get(41).getTitle(), "Richard III (1995)");
        String[] genres = {"Action", "Crime", "Thriller"};
        assertArrayEquals(movies.get(6).getGenres(), genres);
    }

    @Test
    public void readLinks(){
        Map<Integer, Integer> links = dataReader.getLinks();
        assertEquals(links.get(10).intValue(), 710);
    }
}