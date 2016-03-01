package hr.tunjevinac.application;

import hr.tunjevinac.controller.ZajednickiController;

import java.time.format.DateTimeFormatter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
    
    public static DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("HH:mm dd.MM.yyyy.");
    private static BorderPane root;
    private Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setMaximized(true);
        try {
            BorderPane rootPane = (BorderPane) FXMLLoader.load(Main.class
                    .getResource("../layout/Main.fxml"));
            root = rootPane;
            GridPane rootGridPane = (GridPane) FXMLLoader.load(Main.class
                    .getResource("../layout/Login.fxml"));
            root.setCenter(rootGridPane);
            Scene scene = new Scene(rootPane, primaryStage.getWidth(), primaryStage.getHeight());
            
            scene.getStylesheets().add(
                    getClass().getResource("../application/application.css")
                            .toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Božićni Praznici");
            primaryStage.setResizable(false);

            primaryStage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void stop() throws Exception {
        ZajednickiController.exeSer.shutdown();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static <T extends Node> void setCenterPane(T centerPane) {
        root.setCenter(centerPane);
    }
}
