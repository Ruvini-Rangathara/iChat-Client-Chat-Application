package com.example.ichatclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class AppInitializer extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Image icon = new Image("assert/iChat-logo.png");
        stage.getIcons().add(icon);
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/welcome-form.fxml"))));
        stage.setTitle("iChat");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}