package com.example.rankingmaster.domain.validators;

import com.example.rankingmaster.controller.MessageAlert;
import com.example.rankingmaster.domain.Tournament;
import javafx.scene.control.Alert;

public class TournamentValidator implements Validator<Tournament> {

    public TournamentValidator() {
    }

    @Override
    public void validate(Tournament entity) throws ValidationException {
        String message = "";
        if (entity.getName().length() == 0) {
            message += "Name can't be an empty string!\n";
        }
        if (entity.getTeams() < 0 || entity.getTeams() % 2 != 0) {
            message += "The number of teams should be a positive number divided by 2!\n";
        }
        if (entity.getPassword().length() == 0) {
            message += "Password can't be an empty string!\n";
        }

        if (message.length() > 0) {
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Create Tournament", message);
            throw new ValidationException(message);

        }
    }
}
