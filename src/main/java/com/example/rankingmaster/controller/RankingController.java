package com.example.rankingmaster.controller;

import com.example.rankingmaster.domain.MatchDto;
import com.example.rankingmaster.domain.Result;
import com.example.rankingmaster.domain.Team;
import com.example.rankingmaster.domain.TeamDto;
import com.example.rankingmaster.service.Service;
import com.example.rankingmaster.utils.Encryptor;
import com.example.rankingmaster.utils.SceneUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class RankingController implements EventHandler<ActionEvent> {
    private Service service;
    private final SceneUtils sceneUtils = new SceneUtils();
    ObservableList<TeamDto> modelGrade = FXCollections.observableArrayList();

    @FXML
    private TableView<TeamDto> tableView;

    @FXML
    private TableColumn<TeamDto, Integer> colDiference;

    @FXML
    private TableColumn<TeamDto, Integer> colGamesDraw;

    @FXML
    private TableColumn<TeamDto, Integer> colGamesLost;

    @FXML
    private TableColumn<TeamDto, Integer> colGamesPlayed;

    @FXML
    private TableColumn<TeamDto, Integer> colGamesWon;

    @FXML
    private TableColumn<TeamDto, String> colGoals;

    @FXML
    private TableColumn<TeamDto, Integer> colPoints;

    @FXML
    private TableColumn<TeamDto, Integer> colPosition;

    @FXML
    private TableColumn<TeamDto, String> colTeam;

    @FXML
    private Button clasamentButton;

    @FXML
    private Button detailsButton;

    @FXML
    private Button meciuriButton;

    @FXML
    private Button rezultateButton;

    @FXML
    private Button deleteTournamentButton;

    @FXML
    private Button cancelButton;

    Encryptor encryptor = new Encryptor();

    public void setService(Service service) {
        this.service = service;
        modelGrade.setAll(getTeams());
        initialize();
    }

    @Override
    public void handle(ActionEvent event) {
        TeamDto team = tableView.getSelectionModel().getSelectedItem();

        if (event.getSource() == cancelButton) {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
            service.logoutTournament();
        } else if (event.getSource() == deleteTournamentButton) {
            try {
                sceneUtils.switchToDeleteConfirmationScene(event, service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == clasamentButton) {
            try {
                sceneUtils.switchToRankingScene(event, service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == meciuriButton) {
            try {
                sceneUtils.switchToAllMatchesScene(event, service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == rezultateButton) {
            try {
                sceneUtils.switchToAllResultsScene(event, service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == detailsButton) {
            if (team == null) {
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Show details", "Please select a team!");
            } else {
                try {
                    sceneUtils.switchToShowTeamResultsScene(event, service, team);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        modelGrade.setAll(getTeams());
        initialize();
    }

    public void initialize() {
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        colTeam.setCellValueFactory(new PropertyValueFactory<>("name"));
        colGamesPlayed.setCellValueFactory(new PropertyValueFactory<>("gamesPlayed"));
        colGamesWon.setCellValueFactory(new PropertyValueFactory<>("wins"));
        colGamesDraw.setCellValueFactory(new PropertyValueFactory<>("draws"));
        colGamesLost.setCellValueFactory(new PropertyValueFactory<>("loses"));
        colGoals.setCellValueFactory(new PropertyValueFactory<>("goals"));
        colDiference.setCellValueFactory(new PropertyValueFactory<>("difference"));
        colPoints.setCellValueFactory(new PropertyValueFactory<>("points"));

        tableView.setItems(modelGrade);
    }

    public ObservableList<TeamDto> getTeams() {
        Integer position = 1;
        String goals = "";
        ObservableList<TeamDto> teams = FXCollections.observableArrayList();

        for (Team team : service.showTeams()) {
            goals = team.getGoalsScored() + ":" + team.getGoalsConceded();
            teams.add(new TeamDto(position, team.getName(), team.getGamesPlayed(), team.getWins(), team.getDraws(), team.getLoses(), goals, team.getGoalsScored() - team.getGoalsConceded(), team.getPoints()));
            position++;
        }
        return teams;
    }


}
