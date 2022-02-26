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
import java.time.LocalDate;

public class MatchesController implements EventHandler<ActionEvent> {
    private Service service;
    private TeamDto team;

    private final SceneUtils sceneUtils = new SceneUtils();

    ObservableList<MatchDto> modelGrade = FXCollections.observableArrayList();

    @FXML
    private TableColumn<MatchDto, String> colAwayTeam;

    @FXML
    private TableColumn<MatchDto, String> colHomeTeam;

    @FXML
    private TableColumn<MatchDto, String> colStage;

    @FXML
    private TableColumn<MatchDto, LocalDate> colMatchDate;

    @FXML
    private TableView<MatchDto> tableView;

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
        modelGrade.setAll(getMatches());
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
        modelGrade.setAll(getMatches());
        initialize();
    }

    public void initialize() {
        colStage.setCellValueFactory(new PropertyValueFactory<>("stage"));
        colHomeTeam.setCellValueFactory(new PropertyValueFactory<>("homeTeam"));
        colAwayTeam.setCellValueFactory(new PropertyValueFactory<>("awayTeam"));
        colMatchDate.setCellValueFactory(new PropertyValueFactory<>("matchDate"));
        tableView.setItems(modelGrade);
    }

    public ObservableList<MatchDto> getMatches() {
        ObservableList<MatchDto> results = FXCollections.observableArrayList();
        for (Match match : service.printAllMatches()) {
            if (service.findOneTeam(match.getHomeTeam()).getName().equals(team.getName())) {
                results.add(new MatchDto("Stage " + match.getStage(), team.getName() + "*", service.findOneTeam(match.getAwayTeam()).getName(), match.getMatchDate()));
            } else if (service.findOneTeam(match.getAwayTeam()).getName().equals(team.getName())) {
                results.add(new MatchDto("Stage " + match.getStage(), service.findOneTeam(match.getHomeTeam()).getName(), team.getName() + "*", match.getMatchDate()));
            }
        }
        return results;
    }

    public void setLabelText(TeamDto team) {
        teamNameLabel.setText("~" + team.getName() + "~");
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
