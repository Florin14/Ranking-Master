package com.example.rankingmaster.controller;

import com.example.rankingmaster.service.Service;
import com.example.rankingmaster.utils.SceneUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class ConfirmationController implements EventHandler<ActionEvent> {
    private Service service;
    private final SceneUtils sceneUtils = new SceneUtils();

    @FXML
    private Button noButton;

    @FXML
    private Button yesButton;

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public void handle(ActionEvent event) {
        if(event.getSource() == yesButton){
            service.deleteTournament();
            try {
                sceneUtils.switchToLoginScene(event, service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(event.getSource() == noButton){
            try {
                sceneUtils.switchToRankingScene(event, service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
