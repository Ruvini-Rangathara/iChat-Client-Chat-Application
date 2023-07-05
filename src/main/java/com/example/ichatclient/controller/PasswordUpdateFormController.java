package com.example.ichatclient.controller;

import com.example.ichatclient.model.ClientModel;
import com.example.ichatclient.to.ClientTo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PasswordUpdateFormController implements Initializable {

    public Label lblResetSuccess;
    public Label lblResetWrong;
    @FXML
    private AnchorPane anchorpane;

    @FXML
    private Button btnReset;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private PasswordField txtNewPassword;

    private ForgotPasswordFormController controller;

    private String email;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblResetSuccess.setVisible(false);
        lblResetWrong.setVisible(false);
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        anchorpane.getChildren().clear();
        Stage stage = (Stage) anchorpane.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/forgot-password-form.fxml"))));
        stage.setTitle("iChat - Password Recovery");
        stage.show();
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws InterruptedException, IOException {
        updatePassword();
    }

    @FXML
    void txtConfirmPasswordOnAction(ActionEvent event) throws IOException, InterruptedException {
        updatePassword();
    }

    private void updatePassword() throws IOException, InterruptedException {
        if(updateDB()){
            lblResetSuccess.setVisible(true);
            Thread.sleep(3000);

            anchorpane.getChildren().clear();
            Stage stage = (Stage) anchorpane.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/sign-in-form.fxml"))));
            stage.setTitle("Sign In to iChat");
            stage.show();
        }else{
            lblResetWrong.setVisible(true);
        }
    }

    private boolean updateDB() {
        String username = null;
        if(txtNewPassword.getText().equals(txtConfirmPassword.getText())){
            ClientModel clientModel = new ClientModel();
            for (ClientTo client : clientModel.getAll()) {
                if(client.getEmail().equals(this.email)){
                    username = client.getUsername();
                }
            }
            ClientTo newClient = clientModel.search(username);
            newClient.setPassword(txtNewPassword.getText());
            if(clientModel.update(newClient)){
                return true;
            }
        }
        txtConfirmPassword.setStyle("-fx-border-color: red; -fx-border-width: 1px");
        return false;
    }



    @FXML
    void txtNewPasswordOnAction(ActionEvent event) {
        txtConfirmPassword.requestFocus();
        lblResetWrong.setVisible(false);
        lblResetSuccess.setVisible(false);

    }


    public void init(String email, ForgotPasswordFormController forgotPasswordFormController) {
        this.controller = forgotPasswordFormController;
        this.email = email;
    }
}
