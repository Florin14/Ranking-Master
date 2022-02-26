package com.example.rankingmaster.domain.validators;

import com.example.rankingmaster.controller.MessageAlert;
import com.example.rankingmaster.domain.Match;
import com.example.rankingmaster.domain.Team;
import javafx.scene.control.Alert;

import java.time.LocalDate;

public class MatchValidator implements Validator<Match> {

    public MatchValidator() {
    }

    @Override
    public void validate(Match entity) throws ValidationException {
        String message = "";
//        if (entity.getMatchDate().isBefore(LocalDate.now())) {
//            message += "Please select a future date for your match\n";
//        }

        if (message.length() > 0) {
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Create Team", message);
            throw new ValidationException(message);

        }
    }
}
