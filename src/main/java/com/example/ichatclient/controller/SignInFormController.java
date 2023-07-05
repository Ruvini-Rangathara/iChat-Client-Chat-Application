package com.example.ichatclient.controller;

import com.example.ichatclient.model.ClientModel;
import com.example.ichatclient.to.ClientTo;
import com.example.ichatclient.util.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignInFormController implements Initializable {

    public AnchorPane anchorpane;
    @FXML
    private Button btnForgotPassword;

    @FXML
    private Button btnSignIn;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    ClientModel clientModel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientModel = new ClientModel();
    }

    @FXML
    void btnForgotPasswordOnAction(ActionEvent event) throws IOException {
        anchorpane.getChildren().clear();
        Stage stage = (Stage) anchorpane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/forgot-password-form.fxml"))));
        stage.setTitle("iChat - Password Recovery");
        stage.show();
    }

    @FXML
    void btnSignInOnAction(ActionEvent event) throws IOException {
        checkValidity();
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) throws IOException {
        checkValidity();
    }

    void checkValidity() throws IOException {
        if(txtUsername.getText()==null){
            txtUsername.setStyle("-fx-border-color: red; -fx-border-width: 1px");
        }
        if(txtPassword.getText()==null){
            txtPassword.setStyle("-fx-border-color: red; -fx-border-width: 1px");
        }
        if(txtUsername.getText()!=null && txtPassword.getText()!=null){
            ClientTo client = clientModel.search(txtUsername.getText());
            if(client==null){
                txtUsername.setStyle("-fx-border-color: red; -fx-border-width: 1px");
            }else{
                if(client.getPassword().equals(txtPassword.getText())){


//                    anchorpane.getChildren().clear();
//                    Stage stage = (Stage) anchorpane.getScene().getWindow();
//                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/message-form.fxml"))));
//                    stage.setTitle("iChat : "+txtUsername.getText());
//                    stage.show();


                    Client client2 = new Client(txtUsername.getText());
                    Thread thread = new Thread(client2);
                    thread.start();
                    Stage stage = (Stage) anchorpane.getScene().getWindow();
                    stage.close();

                }else{
                    txtPassword.setStyle("-fx-border-color: red; -fx-border-width: 1px");
                }
            }
        }
    }


    @FXML
    void txtUsernameOnAction(ActionEvent event) {
        txtPassword.requestFocus();
    }

    public void btnBackOnAction(ActionEvent event) throws IOException {
        anchorpane.getChildren().clear();
        Stage stage = (Stage) anchorpane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/welcome-form.fxml"))));
        stage.setTitle("iChat");
        stage.show();
    }


}
