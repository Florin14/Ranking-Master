package com.example.rankingmaster.utils;

import com.example.rankingmaster.Main;
import com.example.rankingmaster.controller.*;
import com.example.rankingmaster.domain.MatchDto;
import com.example.rankingmaster.domain.TeamDto;
import com.example.rankingmaster.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneUtils {
    private Parent root;
    private Stage stage;
    private Service service;

    public SceneUtils() {

    }

    public void switchToLoginScene1(Stage stage, Service service) throws IOException {
        this.service = service;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/login.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();
        stage.setTitle("Ranking Master");
        Scene scene = new Scene(anchorPane, 600, 400);
        stage.setScene(scene);
        LoginController welcomeController = fxmlLoader.getController();
        welcomeController.setService(service);
        stage.show();
    }

    public void switchToLoginScene(ActionEvent event, Service service) throws IOException {
        this.service = service;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/login.fxml"));
        Scene scene = null;
        root = fxmlLoader.getRoot();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load(), 600, 400);
        LoginController loginController = fxmlLoader.getController();
        loginController.setService(this.service);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToCreateScene(ActionEvent event, Service service) throws IOException {
        this.service = service;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/createTournament.fxml"));
        Scene scene = null;
        root = fxmlLoader.getRoot();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load(), 520, 400);
        CreateTournamentController createController = fxmlLoader.getController();
        createController.setService(this.service);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToAddTeamsScene(ActionEvent event, Service service, Integer teams) throws IOException {
        this.service = service;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/teamAdding.fxml"));
        Scene scene = null;
        root = fxmlLoader.getRoot();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load(), 520, 400);
        TeamAddingController teamAddingController = fxmlLoader.getController();
        teamAddingController.setService(this.service, teams);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToCreateMatchesScene(ActionEvent event, Service service, Integer teams) throws IOException {
        this.service = service;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/createMatches.fxml"));
        Scene scene = null;
        root = fxmlLoader.getRoot();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load(), 800, 600);
        CreateMatchesController createMatchesController = fxmlLoader.getController();
        createMatchesController.setService(this.service, teams);
        createMatchesController.setLabelText();
        stage.setScene(scene);
        stage.show();
    }

    public void switchToRankingScene(ActionEvent event, Service service) throws IOException {
        this.service = service;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/ranking.fxml"));
        Scene scene = null;
        root = fxmlLoader.getRoot();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load(), 800, 600);
        RankingController rankingController = fxmlLoader.getController();
        rankingController.setService(this.service);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToAllMatchesScene(ActionEvent event, Service service) throws IOException {
        this.service = service;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/allMatches.fxml"));
        Scene scene = null;
        root = fxmlLoader.getRoot();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load(), 800, 600);
        AllMatchesController allMatchesController = fxmlLoader.getController();
        allMatchesController.setService(this.service);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToAllResultsScene(ActionEvent event, Service service) throws IOException {
        this.service = service;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/allResults.fxml"));
        Scene scene = null;
        root = fxmlLoader.getRoot();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        scene = new Scene(fxmlLoader.load(), 800, 600);
        AllResultsController allResultsController = fxmlLoader.getController();
        allResultsController.setService(this.service);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToEditResultScene(ActionEvent event, Service service, MatchDto match) throws IOException {
        this.service = service;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/editResult.fxml"));
        Scene scene = null;
        root = fxmlLoader.getRoot();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load(), 600, 400);
        EditResultController editResultController = fxmlLoader.getController();
        editResultController.setService(this.service, match);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToShowTeamResultsScene(ActionEvent event, Service service, TeamDto team) throws IOException {
        this.service = service;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/results.fxml"));
        Scene scene = null;
        root = fxmlLoader.getRoot();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load(), 800, 600);
        ResultsController resultsController = fxmlLoader.getController();
        resultsController.setService(this.service, team);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToShowTeamMatchesScene(ActionEvent event, Service service, TeamDto team) throws IOException {
        this.service = service;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/matches.fxml"));
        Scene scene = null;
        root = fxmlLoader.getRoot();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load(), 800, 600);
        MatchesController matchesController = fxmlLoader.getController();
        matchesController.setService(this.service, team);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToDeleteConfirmationScene(ActionEvent event, Service service) throws IOException {
        this.service = service;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/deleteConfirmation.fxml"));
        Scene scene = null;
        root = fxmlLoader.getRoot();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load(), 350, 220);

        ConfirmationController deleteConfirmationController = fxmlLoader.getController();
        deleteConfirmationController.setService(this.service);
        stage.setScene(scene);
        stage.show();
    }
}
