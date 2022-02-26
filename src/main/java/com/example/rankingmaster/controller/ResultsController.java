package com.example.rankingmaster.controller;

import com.example.rankingmaster.domain.*;
import com.example.rankingmaster.service.Service;
import com.example.rankingmaster.utils.Encryptor;
import com.example.rankingmaster.utils.SceneUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class ResultsController implements EventHandler<ActionEvent> {
    private Service service;
    private TeamDto team;

    private final SceneUtils sceneUtils = new SceneUtils();

    ObservableList<ResultDto> modelGrade = FXCollections.observableArrayList();

    @FXML
    private TableColumn<ResultDto, String> colAwayTeam;

    @FXML
    private TableColumn<ResultDto, String> colHomeTeam;

    @FXML
    private TableColumn<ResultDto, Integer> colResultAwayTeam;

    @FXML
    private TableColumn<ResultDto, Integer> colResultHomeTeam;

    @FXML
    private TableColumn<ResultDto, String> colStage;

    @FXML
    private TableView<ResultDto> tableView;

    @FXML
    private Button clasamentButton;

    @FXML
    private Button meciuriButton;

    @FXML
    private Button rezultateButton;

    @FXML
    private Label positionLabel;

    @FXML
    private Label teamNameLabel;

    @FXML
    private Button cancelButton;

    Encryptor encryptor = new Encryptor();

    public void setService(Service service, TeamDto team) {
        this.service = service;
        this.team = team;
        setLabelText(team);
        modelGrade.setAll(getResults());
        initialize();
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == cancelButton) {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
            service.logoutTournament();
        } else if (event.getSource() == clasamentButton) {
            try {
                sceneUtils.switchToRankingScene(event, service);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == meciuriButton) {
            try {
                sceneUtils.switchToShowTeamMatchesScene(event, service, team);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == rezultateButton) {
            try {
                sceneUtils.switchToShowTeamResultsScene(event, service, team);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        modelGrade.setAll(getResults());
        initialize();
    }

    public void initialize() {
        colStage.setCellValueFactory(new PropertyValueFactory<>("stage"));
        colHomeTeam.setCellValueFactory(new PropertyValueFactory<>("homeTeam"));
        colAwayTeam.setCellValueFactory(new PropertyValueFactory<>("awayTeam"));
        colResultHomeTeam.setCellValueFactory(new PropertyValueFactory<>("resultHomeTeam"));
        colResultAwayTeam.setCellValueFactory(new PropertyValueFactory<>("resultAwayTeam"));

        tableView.setItems(modelGrade);
    }

    public ObservableList<ResultDto> getResults() {
        ObservableList<ResultDto> results = FXCollections.observableArrayList();
        for (Result result : service.printAllResults()) {
            Team team1 = service.findOneTeam(result.getHomeTeam());
            Team team2 = service.findOneTeam(result.getAwayTeam());
            if (team1.getName().equals(team.getName())) {
                results.add(new ResultDto("Stage " + result.getStage(), team1.getName() + "*", team2.getName(), result.getResultHomeTeam(), result.getResultAwayTeam()));
            } else if (team2.getName().equals(team.getName())) {
                results.add(new ResultDto("Stage " + result.getStage(), team1.getName(), team2.getName() + "*", result.getResultHomeTeam(), result.getResultAwayTeam()));
            }
        }
        return results;
    }

    public void setLabelText(TeamDto team) {
        String position;
        teamNameLabel.setText("~" + team.getName() + "~");
        if (team.getPosition() == 1) {
            position = "st";
        } else if (team.getPosition() == 2) {
            position = "nd";
        } else if (team.getPosition() == 3) {
            position = "rd";
        } else {
            position = "th";
        }
        positionLabel.setText(team.getPosition() + position);
    }
}
