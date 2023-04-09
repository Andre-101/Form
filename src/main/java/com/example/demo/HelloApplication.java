package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {

    private static double xOffset;
    private static double yOffset;

    @Override
    public void start(Stage stage) throws IOException {
        showWindow("first-view.fxml");
    }

    public void showWindow(String fxml){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml));
            Parent node = fxmlLoader.load();
            Scene scene = new Scene(node);
            Stage window = new Stage();

            node.setOnMousePressed(mouseEvent -> {
                xOffset = mouseEvent.getSceneX();
                yOffset = mouseEvent.getSceneY();
            });
            node.setOnMouseDragged(mouseEvent -> {
                window.setX(mouseEvent.getScreenX()-xOffset);
                window.setY(mouseEvent.getScreenY()-yOffset);
            });

            window.initStyle(StageStyle.TRANSPARENT);
            window.setTitle("Formulario ");
            window.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/icon.png"))));
            scene.setFill(Color.TRANSPARENT);
            window.setScene(scene);
            window.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}