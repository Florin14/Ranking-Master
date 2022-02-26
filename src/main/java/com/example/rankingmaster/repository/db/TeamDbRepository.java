package com.example.rankingmaster.repository.db;

import com.example.rankingmaster.domain.Team;
import com.example.rankingmaster.domain.validators.Validator;
import com.example.rankingmaster.repository.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeamDbRepository implements Repository<Long, Team> {
    private final Connection connection;
    private final Validator<Team> validator;

    public TeamDbRepository(Connection connection, Validator<Team> validator) {
        this.connection = connection;
        this.validator = validator;
    }

    @Override
    public Team save(Team entity) {
        if (entity == null)
            throw new IllegalArgumentException("entity must be not null");
        validator.validate(entity);
        String sql = "insert into teams ( name, played, wins, draws, loses, scored, conceded, points, tournament) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, entity.getName());
            ps.setInt(2, entity.getGamesPlayed());
            ps.setInt(3, entity.getWins());
            ps.setInt(4, entity.getDraws());
            ps.setInt(5, entity.getLoses());
            ps.setInt(6, entity.getGoalsScored());
            ps.setInt(7, entity.getGoalsConceded());
            ps.setInt(8, entity.getPoints());
            ps.setLong(9, entity.getTournament());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Team delete(Long aLong) {
        if (aLong == null)
            throw new IllegalArgumentException("id must be not null");
        String sql = "delete from teams where id = ?";
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
    public void update(Team entity) {
        if (entity == null)
            throw new IllegalArgumentException("entity must be not null");
        validator.validate(entity);
        //String sql = "update teams set played = ? , set wins = ? , set draws = ? , set loses = ? , set scored = ? , set conceded = ? , set points = ?  where id = ?";
        String sql = "update teams set played = ?  where id = ? ";
        String sql1 = " update teams set wins = ?  where id = ? ";
        String sql2 = " update teams set draws = ?  where id = ? ";
        String sql3 = " update teams set loses = ?  where id = ? ";
        String sql4 = " update teams set scored = ?  where id = ? ";
        String sql5 = " update teams set conceded = ?  where id = ? ";
        String sql6 = " update teams set points = ?  where id = ? ";


        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
            PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
            PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
            PreparedStatement preparedStatement4 = connection.prepareStatement(sql4);
            PreparedStatement preparedStatement5 = connection.prepareStatement(sql5);
            PreparedStatement preparedStatement6 = connection.prepareStatement(sql6);

            preparedStatement.setInt(1, entity.getGamesPlayed());
            preparedStatement.setLong(2, entity.getId());
            preparedStatement1.setInt(1, entity.getWins());
            preparedStatement1.setLong(2, entity.getId());
            preparedStatement2.setInt(1, entity.getDraws());
            preparedStatement2.setLong(2, entity.getId());
            preparedStatement3.setInt(1, entity.getLoses());
            preparedStatement3.setLong(2, entity.getId());
            preparedStatement4.setInt(1, entity.getGoalsScored());
            preparedStatement4.setLong(2, entity.getId());
            preparedStatement5.setInt(1, entity.getGoalsConceded());
            preparedStatement5.setLong(2, entity.getId());
            preparedStatement6.setInt(1, entity.getPoints());
            preparedStatement6.setLong(2, entity.getId());

//            preparedStatement.setInt(3, entity.getDraws());
//            preparedStatement.setInt(4, entity.getLoses());
//            preparedStatement.setInt(5, entity.getGoalsScored());
//            preparedStatement.setInt(6, entity.getGoalsConceded());
//            preparedStatement.setInt(7, entity.getPoints());

            preparedStatement.executeUpdate();
            preparedStatement1.executeUpdate();
            preparedStatement2.executeUpdate();
            preparedStatement3.executeUpdate();
            preparedStatement4.executeUpdate();
            preparedStatement5.executeUpdate();
            preparedStatement6.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Team findOne(Long aLong) {
        if (aLong == null)
            throw new IllegalArgumentException("ID must be not null");
        String sql = "select * from teams where id = ? ";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, aLong);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                Integer gamesPlayed = resultSet.getInt("played");
                Integer wins = resultSet.getInt("wins");
                Integer draws = resultSet.getInt("draws");
                Integer loses = resultSet.getInt("loses");
                Integer goalsScored = resultSet.getInt("scored");
                Integer goalsConceded = resultSet.getInt("conceded");
                Integer points = resultSet.getInt("points");
                Long tournament = resultSet.getLong("tournament");

                return new Team(name, gamesPlayed, wins, draws, loses, goalsScored, goalsConceded, points, tournament);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Team> findAll() {
        List<Team> teams = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * from teams");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Integer gamesPlayed = resultSet.getInt("played");
                Integer wins = resultSet.getInt("wins");
                Integer draws = resultSet.getInt("draws");
                Integer loses = resultSet.getInt("loses");
                Integer goalsScored = resultSet.getInt("scored");
                Integer goalsConceded = resultSet.getInt("conceded");
                Integer points = resultSet.getInt("points");
                Long tournament = resultSet.getLong("tournament");

                Team team = new Team(name, gamesPlayed, wins, draws, loses, goalsScored, goalsConceded, points, tournament);
                team.setId(id);
                teams.add(team);
            }
            return teams;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teams;
    }
}
