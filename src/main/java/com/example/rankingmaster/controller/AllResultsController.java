package com.example.rankingmaster.controller;

import com.example.rankingmaster.domain.Result;
import com.example.rankingmaster.domain.ResultDto;
import com.example.rankingmaster.service.Service;
import com.example.rankingmaster.utils.Encryptor;
import com.example.rankingmaster.utils.SceneUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class AllResultsController implements EventHandler<ActionEvent> {
    private Service service;
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
    private Button cancelButton;

    Encryptor encryptor = new Encryptor();

    public void setService(Service service) {
        this.service = service;
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
        int nrResults = 0;
        ObservableList<ResultDto> results = FXCollections.observableArrayList();
        for (Result result : service.printAllResults()) {
            if (nrResults == 0 || nrResults != result.getStage()) {
                nrResults = result.getStage();
                results.add(new ResultDto("Stage " + result.getStage(), "", "", null, null));
            }
            results.add(new ResultDto("", service.findOneTeam(result.getHomeTeam()).getName(), service.findOneTeam(result.getAwayTeam()).getName(), result.getResultHomeTeam(), result.getResultAwayTeam()));
        }
        return results;
    }
}
