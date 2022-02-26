package com.example.rankingmaster;

import com.example.rankingmaster.database.DatabaseConnection;
import com.example.rankingmaster.domain.Match;
import com.example.rankingmaster.domain.Result;
import com.example.rankingmaster.domain.Team;
import com.example.rankingmaster.domain.Tournament;
import com.example.rankingmaster.domain.validators.MatchValidator;
import com.example.rankingmaster.domain.validators.ResultValidator;
import com.example.rankingmaster.domain.validators.TeamValidator;
import com.example.rankingmaster.domain.validators.TournamentValidator;
import com.example.rankingmaster.repository.Repository;
import com.example.rankingmaster.repository.db.MatchDbRepository;
import com.example.rankingmaster.repository.db.ResultDbRepository;
import com.example.rankingmaster.repository.db.TeamDbRepository;
import com.example.rankingmaster.repository.db.TournamentDbRepository;
import com.example.rankingmaster.service.*;
import com.example.rankingmaster.utils.SceneUtils;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class Main extends Application {
    Service service;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        Repository<Long, Tournament> tournamentDbRepository = new TournamentDbRepository(connection, new TournamentValidator());
        Repository<Long, Team> teamDbRepository = new TeamDbRepository(connection, new TeamValidator());
        Repository<Long, Match> matchDbRepository = new MatchDbRepository(connection, new MatchValidator());
        Repository<Long, Result> resultDbRepository = new ResultDbRepository(connection, new ResultValidator());

        TournamentService tournamentService = new TournamentService(tournamentDbRepository);
        TeamService teamService = new TeamService(teamDbRepository);
        MatchService matchService = new MatchService(matchDbRepository);
        ResultService resultService = new ResultService(resultDbRepository);

        service = new Service(tournamentService, teamService, matchService, resultService);

        SceneUtils sceneUtils = new SceneUtils();
        sceneUtils.switchToLoginScene1(stage, service);
    }

}