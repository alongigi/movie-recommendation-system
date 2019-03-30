package view;

import DataReader.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.JSONObject;

import java.net.URL;
import java.util.*;

public class RecommendationsHandler implements Initializable{
    public VBox searchResultVBox;
    private DataReader dataReader;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void showMovies(List<Integer> movieIds){
        Map<Integer, Movie> movies = dataReader.getMovies();
        Map<Integer, Integer> links = dataReader.getLinks();
        Random random = new Random();
        for (int i = 0; i < movieIds.size(); i++) {
            Integer randomKey = movieIds.get(random.nextInt(movieIds.size()));
            Movie movie = movies.get(movieIds.get(i));
            int movieId = movieIds.get(i);
            String imagePath = "30oXQKwibh0uANGMs0Sytw3uN22.jpg";
            try {
                int tdbmId = links.get(movieId);
                JSONObject jsonObject = JsonReader.readJsonFromUrl("https://api.themoviedb.org/3/movie/" + tdbmId + "?api_key=6323cf8bd5ee99e29a95530e11aff7af&language=en-US");

                imagePath = (String) jsonObject.get("backdrop_path");
            } catch (Exception e) {
                imagePath = "https://png.pngtree.com/element_origin_min_pic/16/09/08/2057d15a050b0d1.jpg";
                addMovieEntry(imagePath, movie.getTitle(), movieId);
                continue;
            }
            String imageUrl = "https://image.tmdb.org/t/p/w185_and_h278_bestv2/" + imagePath;
            // String movieDescription = "the best movie";
            addMovieEntry(imageUrl, movie.getTitle(), movieId);
        }
    }

    private void addMovieEntry(String imageUrl, String movieDescription, int movieId) {
        HBox movieEntry = new HBox();

        movieEntry.setPadding(new Insets(15, 0, 15, 0));
        Image image = new Image(imageUrl);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setSmooth(true);
        imageView.setPreserveRatio(true);
        imageView.setCache(true);
        TextArea movieDescriptionTextArea = new TextArea();
        movieDescriptionTextArea.setText(movieDescription);
        movieDescriptionTextArea.setMinWidth(300);
        movieDescriptionTextArea.setMaxHeight(100);
        movieDescriptionTextArea.setEditable(false);

        movieEntry.getChildren().add(imageView);
        movieEntry.getChildren().add(movieDescriptionTextArea);
       // movieEntry.getChildren().add(rankChoiceBox);


        searchResultVBox.getChildren().add(movieEntry);
    }

    public void setDataReader(DataReader dataReader) {
        this.dataReader = dataReader;
    }
}
