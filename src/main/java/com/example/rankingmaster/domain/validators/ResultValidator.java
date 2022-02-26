package com.example.rankingmaster.domain.validators;

import com.example.rankingmaster.controller.MessageAlert;
import com.example.rankingmaster.domain.Match;
import com.example.rankingmaster.domain.Result;
import javafx.scene.control.Alert;

import java.time.LocalDate;

public class ResultValidator implements Validator<Result> {

    public ResultValidator() {
    }

    @Override
    public void validate(Result entity) throws ValidationException {
        String message = "";
        if (entity.getResultAwayTeam() < 0 || entity.getResultHomeTeam() < 0)
            message += "The result has to be a positive number!";

        if (message.length() > 0) {
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Create Team", message);
            throw new ValidationException(message);

        }
    }
}
