package com.example.rankingmaster.repository.db;

import com.example.rankingmaster.domain.Match;
import com.example.rankingmaster.domain.validators.Validator;
import com.example.rankingmaster.repository.Repository;
import com.example.rankingmaster.utils.Constants;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MatchDbRepository implements Repository<Long, Match> {
    private final Connection connection;
    private final Validator<Match> validator;

    public MatchDbRepository(Connection connection, Validator<Match> matchValidator) {
        this.connection = connection;
        this.validator = matchValidator;
    }

    @Override
    public Match save(Match entity) {
        if (entity == null)
            throw new IllegalArgumentException("entity must be not null");
        validator.validate(entity);
        String sql = "insert into matches ( tournament, home_team, away_team, stage, match_date) values (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, entity.getTournament());
            ps.setLong(2, entity.getHomeTeam());
            ps.setLong(3, entity.getAwayTeam());
            ps.setInt(4, entity.getStage());
            String date1 = String.valueOf(entity.getMatchDate());
            LocalDate date = LocalDate.parse(date1, Constants.STANDARD_DATE_FORMAT);
            ps.setDate(5, Date.valueOf(date));

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Match delete(Long aLong) {
        if (aLong == null)
            throw new IllegalArgumentException("id must be not null");
        String sql = "delete from matches where id = ?";
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
    public void update(Match entity) {
    }

    @Override
    public Match findOne(Long aLong) {
        if (aLong == null)
            throw new IllegalArgumentException("ID must be not null");
        String sql = "select * from matches where id = ? ";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, aLong);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                Long tournament = resultSet.getLong("tournament");
                Long homeTeam = resultSet.getLong("home_team");
                Long awayTeam = resultSet.getLong("away_team");
                Integer stage = resultSet.getInt("stage");
                String date1 = resultSet.getString("match_date");
                LocalDate date = LocalDate.parse(date1, Constants.STANDARD_DATE_FORMAT);
                return new Match(tournament, homeTeam, awayTeam, stage, date);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Match> findAll() {
        List<Match> matches = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * from matches");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long tournament = resultSet.getLong("tournament");
                Long homeTeam = resultSet.getLong("home_team");
                Long awayTeam = resultSet.getLong("away_team");
                Integer stage = resultSet.getInt("stage");
                String date1 = resultSet.getString("match_date");
                LocalDate date = LocalDate.parse(date1, Constants.STANDARD_DATE_FORMAT);
                Match match = new Match(tournament, homeTeam, awayTeam, stage, date);
                match.setId(id);
                matches.add(match);
            }
            return matches;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return matches;
    }
}
