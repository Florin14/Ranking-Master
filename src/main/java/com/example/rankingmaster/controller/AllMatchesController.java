package com.example.rankingmaster.controller;

import com.example.rankingmaster.domain.Match;
import com.example.rankingmaster.domain.MatchDto;
import com.example.rankingmaster.service.Service;
import com.example.rankingmaster.utils.Encryptor;
import com.example.rankingmaster.utils.SceneUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class AllMatchesController implements EventHandler<ActionEvent> {
    private Service service;
    private final SceneUtils sceneUtils = new SceneUtils();
    ObservableList<MatchDto> modelGrade = FXCollections.observableArrayList();

    @FXML
    private TableView<MatchDto> tableView;

    @FXML
    private TableColumn<MatchDto, String> colStage;

    @FXML
    private TableColumn<MatchDto, String> colHomeTeam;

    @FXML
    private TableColumn<MatchDto, String> colAwayTeam;

    @FXML
    private TableColumn<MatchDto, LocalDate> colMatchDate;

    @FXML
    private Button clasamentButton;

    @FXML
    private Button meciuriButton;

    @FXML
    private Button rezultateButton;

    @FXML
    private Button addResultButton;

    @FXML
    private Button cancelButton;

    Encryptor encryptor = new Encryptor();

    public void setService(Service service) {
        this.service = service;
        modelGrade.setAll(getMatches());
        initialize();
    }

    @Override
    public void handle(ActionEvent event) {
        MatchDto match = tableView.getSelectionModel().getSelectedItem();
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
        } else if (event.getSource() == addResultButton && match != null) {
            if (match.getMatchDate().isAfter(LocalDate.now())) {
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Add result", "You can't select a future match!");
            } else {
                try {
                    sceneUtils.switchToEditResultScene(event, service, match);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
        int nrMatches = 0;
        ObservableList<MatchDto> matches = FXCollections.observableArrayList();
        for (Match match : service.printAllMatches()) {
            if (nrMatches == 0 || nrMatches != match.getStage()) {
                nrMatches = match.getStage();
                matches.add(new MatchDto("Stage " + match.getStage(), "", "", null));
            }
            matches.add(new MatchDto("", service.findOneTeam(match.getHomeTeam()).getName(), service.findOneTeam(match.getAwayTeam()).getName(), match.getMatchDate()));
        }
        return matches;
    }
}
