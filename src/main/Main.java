package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Main extends Application {

    private static Scene mainScene;

    @Override
    public void start(Stage primaryStage) throws Exception{
        ScrollPane root = FXMLLoader.load(getClass().getResource("../gui/MainView.fxml"));

        root.setFitToHeight(true);
        root.setFitToWidth(true);

        mainScene = new Scene(root, 720, 576);
        primaryStage.setTitle("Management");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static Scene getMainScene () {
        return mainScene;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
