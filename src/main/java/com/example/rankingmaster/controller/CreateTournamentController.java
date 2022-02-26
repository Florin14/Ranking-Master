package com.example.rankingmaster.controller;


import com.example.rankingmaster.domain.validators.ValidationException;
import com.example.rankingmaster.service.Service;
import com.example.rankingmaster.utils.Encryptor;
//import com.example.livescore.utils.SceneUtils;
import com.example.rankingmaster.utils.SceneUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateTournamentController implements EventHandler<ActionEvent> {
    private Service service;
    private final SceneUtils sceneUtils = new SceneUtils();

    @FXML
    private Button createButton;

    @FXML
    private Button loginButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField teamsTextField;

    @FXML
    private Button cancelButton;

    @FXML
    private PasswordField passwordPasswordField;

    Encryptor encryptor = new Encryptor();

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == cancelButton) {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
            service.logoutTournament();
        }else if(event.getSource() == loginButton){
            try {
                sceneUtils.switchToLoginScene(event,service);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(event.getSource() == createButton){
            try {
                handleSave(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleSave(ActionEvent event) throws IOException {
        String name = nameTextField.getText();
        String password = passwordPasswordField.getText();
        Integer teams = Integer.valueOf(teamsTextField.getText());

        try {
            if (service.createTournament(name, password, teams)) {
                try {
                    sceneUtils.switchToAddTeamsScene(event, service, teams);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            } else {
                nameTextField.clear();
                teamsTextField.clear();
                passwordPasswordField.clear();
            }
        }catch(ValidationException | NullPointerException e){
            e.printStackTrace();
        }
    }
}
