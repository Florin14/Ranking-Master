package com.example.rankingmaster.controller;

import com.example.rankingmaster.database.DatabaseConnection;
import com.example.rankingmaster.service.Service;
import com.example.rankingmaster.utils.SceneUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController implements EventHandler<ActionEvent> {
    private Service service;
    private final SceneUtils sceneUtils = new SceneUtils();


    //
//    public void switchToSignupScene(ActionEvent event) throws IOException {
//        sceneUtils.switchToSignUpScene(event, this.service);
//    }
//
//    public void loginButtonOnAction(ActionEvent event) throws IOException, NoSuchAlgorithmException {
//        if (!emailTextField.getText().isBlank() && !passwordPasswordField.getText().isBlank()) {
//            if (validateLogin()) {
//                service.loginUser(emailTextField.getText(),encryptor.encryptString(passwordPasswordField.getText()));
//                sceneUtils.switchToUserProfileScene(event, this.service);
//            } else {
//                passwordPasswordField.clear();
//                loginMessageLabel.setText("Invalid login.Please try again!");
//            }
//        } else {
//            loginMessageLabel.setText("Please enter the email and password!");
//        }
//
//    }
    @FXML
    private Button loginButton;

    @FXML
    private Button createButton;

    @FXML
    private Button cancelButton;

    @FXML
    private PasswordField passwordPasswordField;

    //Encryptor encryptor = new Encryptor();

    public void setService(Service service) {
        this.service = service;
    }

    public boolean validateLogin() throws NoSuchAlgorithmException {
        boolean validated = false;
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectDB = connection.getConnection();

        String verifyLogin = "SELECT count(1) FROM tournaments WHERE password_hash = '" + passwordPasswordField.getText() + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet rs = statement.executeQuery(verifyLogin);

            while (rs.next()) {
                if (rs.getInt(1) == 1) {
                    validated = true;
                    //service.login(emailTextField.getText(), encryptor.encryptString(passwordPasswordField.getText()));
                    service.login(passwordPasswordField.getText());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return validated;
    }

    public void loginButtonOnAction(ActionEvent event) throws IOException, NoSuchAlgorithmException {
        if (!passwordPasswordField.getText().isBlank()) {
            if (validateLogin()) {
                service.login(passwordPasswordField.getText());
                sceneUtils.switchToRankingScene(event, this.service);
            } else {
                passwordPasswordField.clear();
                //loginMessageLabel.setText("Invalid login.Please try again!");
            }
        }
//        } else {
//            loginMessageLabel.setText("Please enter the email and password!");
//        }

    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == cancelButton) {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
            service.logoutTournament();
        } else if (event.getSource() == loginButton) {
            try {
                loginButtonOnAction(event);
            } catch (IOException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == createButton) {
            try {
                sceneUtils.switchToCreateScene(event, service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}