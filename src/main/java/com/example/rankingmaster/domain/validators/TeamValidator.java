package com.example.rankingmaster.domain.validators;

import com.example.rankingmaster.controller.MessageAlert;
import com.example.rankingmaster.domain.Team;
import javafx.scene.control.Alert;

public class TeamValidator implements Validator<Team> {

    public TeamValidator() {
    }

    @Override
    public void validate(Team entity) throws ValidationException {
        String message = "";
        if (entity.getName().length() == 0) {
            message += "Name can't be an empty string!\n";
        }
        if (entity.getGamesPlayed() < 0 || entity.getWins() < 0 ||  entity.getLoses() < 0 || entity.getDraws() < 0 || entity.getGoalsScored() < 0 || entity.getGoalsConceded() < 0) {
            message += "The numbers  should be positive!\n";
        }

        if (message.length() > 0) {
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Create Team", message);
            throw new ValidationException(message);

        }
    }
}
