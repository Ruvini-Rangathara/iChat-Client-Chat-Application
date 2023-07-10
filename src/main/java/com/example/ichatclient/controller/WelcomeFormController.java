package com.example.ichatclient.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeFormController {
    public AnchorPane anchorpane;

    public void btnSignInOnAction(ActionEvent event) throws IOException {
        anchorpane.getChildren().clear();
        Stage stage = (Stage) anchorpane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/sign-in-form.fxml"))));
        stage.setTitle("Sign In to iChat");
        stage.show();
    }

    public void btnSignUpOnAction(ActionEvent event) throws IOException {
        anchorpane.getChildren().clear();
        Stage stage = (Stage) anchorpane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/sign-up-form.fxml"))));
        stage.setTitle("Sign Up to iChat");
        stage.show();
    }
}
