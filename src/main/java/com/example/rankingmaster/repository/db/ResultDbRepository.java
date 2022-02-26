package com.example.rankingmaster.repository.db;

import com.example.rankingmaster.domain.Result;
import com.example.rankingmaster.domain.validators.Validator;
import com.example.rankingmaster.repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResultDbRepository implements Repository<Long, Result> {
    private final Connection connection;
    private final Validator<Result> validator;

    public ResultDbRepository(Connection connection, Validator<Result> validator) {
        this.connection = connection;
        this.validator = validator;
    }

    @Override
    public Result save(Result entity) {
        if (entity == null)
            throw new IllegalArgumentException("entity must be not null");
        validator.validate(entity);
        String sql = "insert into results ( tournament, home_team, away_team, result_home_team, result_away_team, stage) values (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, entity.getTournament());
            ps.setLong(2, entity.getHomeTeam());
            ps.setLong(3, entity.getAwayTeam());
            ps.setInt(4, entity.getResultHomeTeam());
            ps.setInt(5, entity.getResultAwayTeam());
            ps.setInt(6, entity.getStage());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Result delete(Long aLong) {
        if (aLong == null)
            throw new IllegalArgumentException("id must be not null");
        String sql = "delete from results where id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, aLong);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Result entity) {
    }

    @Override
    public Result findOne(Long aLong) {
        if (aLong == null)
            throw new IllegalArgumentException("ID must be not null");
        String sql = "select * from results where id = ? ";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, aLong);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                Long tournament = resultSet.getLong("tournament");
                Long homeTeam = resultSet.getLong("home_team");
                Long awayTeam = resultSet.getLong("away_team");
                Integer resultHome = resultSet.getInt("result_home_team");
                Integer resultAway = resultSet.getInt("result_away_team");
                Integer stage = resultSet.getInt("stage");

                return new Result(tournament, stage, homeTeam, awayTeam, resultHome, resultAway);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Result> findAll() {
        List<Result> matches = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * from results");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long tournament = resultSet.getLong("tournament");
                Long homeTeam = resultSet.getLong("home_team");
                Long awayTeam = resultSet.getLong("away_team");
                Integer resultHome = resultSet.getInt("result_home_team");
                Integer resultAway = resultSet.getInt("result_away_team");
                Integer stage = resultSet.getInt("stage");

                Result result = new Result(tournament, stage, homeTeam, awayTeam, resultHome, resultAway);

                result.setId(id);
                matches.add(result);
            }
            return matches;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return matches;
    }
}
