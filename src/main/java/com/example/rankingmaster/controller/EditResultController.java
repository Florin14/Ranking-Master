package com.example.rankingmaster.controller;

import com.example.rankingmaster.domain.Match;
import com.example.rankingmaster.domain.MatchDto;
import com.example.rankingmaster.service.Service;
import com.example.rankingmaster.utils.SceneUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;


public class EditResultController implements EventHandler<ActionEvent> {
    private Service service;
    private final SceneUtils sceneUtils = new SceneUtils();

    private MatchDto match;

    @FXML
    private Label awayTeamLabel;

    @FXML
    private TextField awayTeamResultTextField;

    @FXML
    private Button editButton;

    @FXML
    private Label homeTeamLabel;

    @FXML
    private TextField homeTeamResultTextField;

    public void setService(Service service, MatchDto match) {
        this.service = service;
        this.match = match;
        setLabelText(match);
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == editButton) {
            Long matchId = 0L;
            Long homeId = 0L;
            Long awayId = 0L;
            int stage = 0;
            for (Match match1 : service.printAllMatches()) {
                if (service.findOneTeam(match1.getHomeTeam()).getName().equals(match.getHomeTeam()) && service.findOneTeam(match1.getAwayTeam()).getName().equals(match.getAwayTeam())) {
                    homeId = match1.getHomeTeam();
                    awayId = match1.getAwayTeam();
                    stage = match1.getStage();
                    matchId = match1.getId();
                }

            }
            if (!homeTeamResultTextField.getText().isBlank() && !awayTeamResultTextField.getText().isBlank()) {
                service.saveResult(homeId, stage, awayId, Integer.valueOf(homeTeamResultTextField.getText()), Integer.valueOf(awayTeamResultTextField.getText()));
                try {
                    sceneUtils.switchToAllMatchesScene(event, service);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                service.deleteMatch(matchId);
            }

        }
    }

    public void setLabelText(MatchDto match) {
        homeTeamLabel.setText(match.getHomeTeam());
        awayTeamLabel.setText(match.getAwayTeam());
    }

}
