package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        ScrollPane root = FXMLLoader.load(getClass().getResource("../gui/MainView.fxml"));

        root.setFitToHeight(true);
        root.setFitToWidth(true);

        primaryStage.setTitle("Management");
        primaryStage.setScene(new Scene(root, 720, 576));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
