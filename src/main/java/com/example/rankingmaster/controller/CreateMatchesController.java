package com.example.rankingmaster.controller;

import com.example.rankingmaster.domain.Match;
import com.example.rankingmaster.domain.Team;
import com.example.rankingmaster.domain.TeamName;
import com.example.rankingmaster.service.Service;
import com.example.rankingmaster.utils.SceneUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateMatchesController implements EventHandler<ActionEvent> {
    private Service service;
    private final SceneUtils sceneUtils = new SceneUtils();
    ObservableList<TeamName> modelGrade = FXCollections.observableArrayList();
    private Integer teams;
    private Integer stage = 1;
    private Integer count = 0;


    @FXML
    private ComboBox<String> awayTeam;

    @FXML
    private ComboBox<String> homeTeam;

    @FXML
    private TableColumn<TeamName, String> colAwayTeam;

    @FXML
    private TableColumn<TeamName, String> colHomeTeam;

    @FXML
    private Button matchButton;

    @FXML
    private DatePicker matchDate;

    @FXML
    private Label stageLabel;

    @FXML
    private TableView<TeamName> tableView;

    public void setService(Service service, Integer teams) {
        this.service = service;
        this.teams = teams;
        saveComboBox(new ArrayList<>());
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == matchButton) {
            String errorMessage = "";
            Long homeId = 0L;
            Long awayId = 0L;
            String homeTeam1 = homeTeam.getSelectionModel().getSelectedItem();
            String awayTeam1 = awayTeam.getSelectionModel().getSelectedItem();
            for (Team team : service.findAllTeams()) {
                if (team.getName().equals(homeTeam1)) {
                    homeId = team.getId();
                } else if (team.getName().equals(awayTeam1)) {
                    awayId = team.getId();
                }
            }
            if (homeTeam1.isBlank() || awayTeam1.isBlank()) {
                errorMessage += "Please fill in all the blanks!\n";
            }
            if (homeTeam1.equals(awayTeam1)) {
                errorMessage += "Please select 2 different teams!\n";
            }
            boolean flag = true;
            for (Match match : service.printAllMatches()) {
                Team home = service.findOneTeam(match.getHomeTeam());
                Team away = service.findOneTeam(match.getAwayTeam());
                if (match.getTournament().equals(service.getCurrentTournamentId()) && home.getName().equals(homeTeam1) && away.getName().equals(awayTeam1)) {
                    flag = false;
                }
            }
            if (!flag) {
                errorMessage += "This match already exist!\n";
            }

            if (errorMessage.length() > 0) {
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Add match", errorMessage);
            } else if (verifyMatch(homeId, awayId)) {
                if (matchDate.getValue() != null) {
                    service.saveMatch(homeId, awayId, stage, matchDate.getValue());
                    count++;
                    if (teams / 2 == count) {
                        count = 0;
                        stage++;
                        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Add match", "Stage added!");
                        setLabelText();
                    }
                    if (teams.equals(stage)) {
                        try {
                            setRetur();
                            sceneUtils.switchToRankingScene(event, service);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    modelGrade.setAll(getMatches());
                    initialize();
                } else {
                    MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Add match", "Please select a date for your match!");
                }

            } else {
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Add match", "This match already exists!");
            }
            saveComboBox(getStrings());
        }
    }

    public void initialize() {
        colHomeTeam.setCellValueFactory(new PropertyValueFactory<>("homeTeam"));
        colAwayTeam.setCellValueFactory(new PropertyValueFactory<>("awayTeam"));

        tableView.setItems(modelGrade);
    }

    public void saveComboBox(List<String> strings) {
        ObservableList<String> teams = FXCollections.observableArrayList();
        for (Team team : service.showTeams()) {
            if (team.getTournament().equals(service.getCurrentTournamentId()) && (strings.size() == 0) || !strings.contains(team.getName())) {
                teams.add(team.getName());
            }
        }
        homeTeam.setItems(teams);
        awayTeam.setItems(teams);
    }

    public List<String> getStrings() {
        List<String> strings = new ArrayList<>();
        for (Match match : service.printAllMatches()) {
            if (match.getTournament().equals(service.getCurrentTournamentId()) && match.getStage().equals(stage)) {
                Team team1 = service.findOneTeam(match.getHomeTeam());
                Team team2 = service.findOneTeam(match.getAwayTeam());
                strings.add(team1.getName());
                strings.add(team2.getName());
            }
        }
        return strings;
    }

    public ObservableList<TeamName> getMatches() {
        ObservableList<TeamName> matches = FXCollections.observableArrayList();
        for (Match match : service.printAllMatches()) {
            if (match.getStage().equals(stage)) {
                Team team1 = service.findOneTeam(match.getHomeTeam());
                Team team2 = service.findOneTeam(match.getAwayTeam());
                matches.add(new TeamName(team1.getName(), team2.getName()));
            }
        }
        return matches;
    }

    public void setRetur() {
        for (Match match : service.printAllMatches()) {
            service.saveMatch(match.getAwayTeam(), match.getHomeTeam(), match.getStage() + teams - 1, match.getMatchDate().plusMonths(6).plusDays(15));
        }
    }

    public boolean verifyMatch(Long homeId, Long awayId) {
        for (Match match : service.printAllMatches()) {
            if ((match.getHomeTeam().equals(homeId) && match.getAwayTeam().equals(awayId)) || (match.getHomeTeam().equals(awayId) && match.getAwayTeam().equals(homeId))) {
                return false;
            }
        }
        return true;
    }

    public void setLabelText() {
        stageLabel.setText("Stage " + stage);
    }
}
