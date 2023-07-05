package com.example.ichatclient.controller;

import com.example.ichatclient.util.Mail;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class ForgotPasswordFormController {

    public AnchorPane anchorpane;
    @FXML
    private Button btnResendCode;

    @FXML
    private Button btnVerify;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtOtp1;

    @FXML
    private TextField txtOtp2;

    @FXML
    private TextField txtOtp3;

    @FXML
    private TextField txtOtp4;

    Random random = new Random();

    int otp =0;

    String email;

    @FXML
    void btnResendCodeOnAction(ActionEvent event) {
        sendOtpMail();
    }

    @FXML
    void btnVerifyOnAction(ActionEvent event) throws IOException {
        verifyOtp();
    }

    @FXML
    void txtEmailOnAction(ActionEvent event) {
        sendOtpMail();
    }

    private void sendOtpMail() {
        otp = random.nextInt(8999)+1000;

        new Thread(()->{
            String subject="Reset Your iChat Password";
            String text="Dear User,\n" +
                    "\n" +
                    "You have requested to reset your password for the iChat. Please use the following One-Time Password (OTP) to proceed with the password reset process:\n" +
                    "\n" +
                    "OTP: "+otp+" \n" +
                    "\n" +
                    "If you did not initiate this password reset request, please ignore this email.\n" +
                    "\n" +
                    "Thank you,\n" +
                    "iChat Team";

            String to = "subhasinghe.rr2000@gmail.com";
            try {
                Mail.sendMail(to, subject, text);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }).start();

    }

    @FXML
    void txtOtp1OnAction(ActionEvent event) {
        txtOtp2.requestFocus();
    }

    @FXML
    void txtOtp2OnAction(ActionEvent event) {
        txtOtp3.requestFocus();
    }

    @FXML
    void txtOtp3OnAction(ActionEvent event) {
        txtOtp4.requestFocus();
    }

    @FXML
    void txtOtp4OnAction(ActionEvent event) throws IOException {
        verifyOtp();
    }

    private void verifyOtp() throws IOException {
        String temp=null;
        if( txtOtp1!=null && txtOtp2!=null && txtOtp3!=null && txtOtp4!=null ){
            temp=txtOtp1.getText()+txtOtp2.getText()+txtOtp3.getText()+txtOtp4.getText();
        }
        int typedOtp = Integer.parseInt(temp);
        if(typedOtp==otp){

            email = txtEmail.getText();

            anchorpane.getChildren().clear();
            Stage stage = (Stage) anchorpane.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/password-update-form.fxml"))));

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/password-update-form.fxml"));
            Parent load = fxmlLoader.load();
            PasswordUpdateFormController controller = fxmlLoader.getController();
            controller.init(email,this);
            stage.setTitle("iChat - Password Recovery");
            stage.setScene(new Scene(load));
//            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();




        }else{
            txtOtp1.setStyle("-fx-border-color: red; -fx-border-width: 1px");
            txtOtp2.setStyle("-fx-border-color: red; -fx-border-width: 1px");
            txtOtp3.setStyle("-fx-border-color: red; -fx-border-width: 1px");
            txtOtp4.setStyle("-fx-border-color: red; -fx-border-width: 1px");
        }
    }


    public void btnBackOnAction(ActionEvent event) throws IOException {
        anchorpane.getChildren().clear();
        Stage stage = (Stage) anchorpane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/sign-in-form.fxml"))));
        stage.setTitle("iChat - Password Recovery");
        stage.show();
    }
}
