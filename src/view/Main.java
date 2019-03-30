package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader mainViewLoader = new FXMLLoader(getClass().getResource("MainView.fxml"));
        Parent root = mainViewLoader.load();
        MainViewHandler controller = mainViewLoader.getController();
        controller.setUp();
//        controller.clearMovies();
        primaryStage.setTitle("Find your movie");
        primaryStage.setScene(new Scene(root, 600, 475));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
