package com.example.rankingmaster.controller;

import com.example.rankingmaster.domain.Team;
import com.example.rankingmaster.service.Service;
import com.example.rankingmaster.utils.SceneUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class TeamAddingController implements EventHandler<ActionEvent> {
    private Service service;
    private Integer teams;
    private Integer count;
    private final SceneUtils sceneUtils = new SceneUtils();

    @FXML
    private Button addButton;

    @FXML
    private TextField nameTextField;

    public void setService(Service service, Integer teams) {
        this.service = service;
        this.teams = teams;
        this.count = 0;
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == addButton) {
            if (!nameTextField.getText().isBlank() && verifyTeam(nameTextField.getText())) {
                service.saveTeam(nameTextField.getText());
                count++;
                nameTextField.clear();
                if (count.equals(teams)) {
                    try {
                        sceneUtils.switchToCreateMatchesScene(event, service, teams);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Team", "Please enter a valid team that is not in the tournament!");

            }
        }
    }

    public boolean verifyTeam(String teamName) {
        for (Team team : service.showTeams()) {
            if (team.getName().equals(teamName))
                return false;
        }
        return true;
    }
}
