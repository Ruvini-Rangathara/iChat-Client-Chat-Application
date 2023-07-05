package com.example.ichatclient.controller;

import com.example.ichatclient.model.ClientModel;
import com.example.ichatclient.to.ClientTo;
import com.example.ichatclient.util.Mail;
import com.jfoenix.controls.JFXCheckBox;
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

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpFormController implements Initializable {

    public AnchorPane anchorpane;
    @FXML
    private Button btnSignUp;

    @FXML
    private JFXCheckBox checkBoxAgree;

    @FXML
    private TextField txtEmail;

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
    void btnSignUpOnAction(ActionEvent event) throws IOException {
        signUp();
    }

    @FXML
    void txtEmailOnAction(ActionEvent event) throws IOException {
        signUp();
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) {
        txtEmail.requestFocus();
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

    private void signUp() throws IOException {
        ClientTo client = new ClientTo(txtUsername.getText(), txtPassword.getText(), txtEmail.getText());
        if(checkBoxAgree.isSelected()){
            if(!clientModel.save(client)){
                invalidSignUp();
            }else{
                sendClientRegistrationMail();

                anchorpane.getChildren().clear();
                Stage stage = (Stage) anchorpane.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/sign-in-form.fxml"))));
                stage.setTitle("Sign In to iChat");
                stage.show();
            }
        }else{
            checkBoxAgree.setStyle("-fx-background-color: #77dd77;");
        }
    }

    private void invalidSignUp() {
        for (ClientTo client : clientModel.getAll()) {
            if(client.getUsername().equals(txtUsername.getText())){
                txtUsername.setStyle("-fx-border-color: red; -fx-border-width: 1px");
                break;
            }
            if(client.getEmail().equals(txtEmail.getText())){
                txtEmail.setStyle("-fx-border-color: red; -fx-border-width: 1px");
                break;
            }
        }
    }

    private void sendClientRegistrationMail() {
        new Thread(()->{
            String subject="Welcome to iChat - Your Signup Was Successful!";
            String text="Dear User,\n" +
                    "\n" +
                    "Congratulations and welcome to iChat! We're excited to have you as a new member of our chat community. Your signup process was successful, and we are thrilled to provide you with a platform to connect with people from around the world.\n" +
                    "\n" +
                    "As a member of iChat, you'll enjoy a range of features designed to enhance your chatting experience. Whether you want to connect with friends, meet new people, or engage in group discussions, our application offers a seamless and user-friendly interface for all your communication needs.\n" +
                    "\n" +
                    "We encourage you to take advantage of the following features:\n" +
                    "\n" +
                    "1. Group Chats: Engage in group discussions on various topics and find like-minded individuals.\n" +
                    "\n" +
                    "2. Emojis: Express yourself with a wide array of emojis to add fun to your conversations.\n" +
                    "\n" +
                    "5. Privacy and Security: Rest assured, your data and conversations are encrypted and protected.\n" +
                    "\n" +
                    "\n" +
                    "Once again, welcome to the iChat community! We hope you have a wonderful experience connecting with people, making new friends, and engaging in exciting conversations.\n" +
                    "\n" +
                    "Happy chatting!\n" +
                    "\n" +
                    "Best regards,\n" +
                    "iChat Team";

            String to = txtEmail.getText();
            try {
                Mail.sendMail(to, subject, text);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

}
