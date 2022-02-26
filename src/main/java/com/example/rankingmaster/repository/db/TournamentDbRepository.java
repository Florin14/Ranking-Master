package com.example.rankingmaster.repository.db;

import com.example.rankingmaster.domain.Tournament;
import com.example.rankingmaster.domain.validators.Validator;
import com.example.rankingmaster.repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TournamentDbRepository implements Repository<Long, Tournament> {
    private final Connection connection;
    private final Validator<Tournament> validator;

    public TournamentDbRepository(Connection connection, Validator<Tournament> validator) {
        this.connection = connection;
        this.validator = validator;
    }

    @Override
    public Tournament save(Tournament entity) {
        if (entity == null)
            throw new IllegalArgumentException("entity must be not null");
            validator.validate(entity);
        String sql = "insert into tournaments ( name, password_hash, teams) values (?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getPassword());
            ps.setInt(3, entity.getTeams());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Tournament delete(Long aLong) {
        if (aLong == null)
            throw new IllegalArgumentException("id must be not null");
        String sql = "delete from tournaments where id = ?";
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
    public void update(Tournament entity) {
    }

    @Override
    public Tournament findOne(Long aLong) {
        if (aLong == null)
            throw new IllegalArgumentException("ID must be not null");
        String sql = "select * from tournaments where id = ? ";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, aLong);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String password = resultSet.getString("password_hash");
                Integer teams = resultSet.getInt("teams");

                return new Tournament(name, password, teams);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Tournament> findAll() {
        List<Tournament> events = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * from tournaments");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String password = resultSet.getString("password_hash");
                Integer teams = resultSet.getInt("teams");

                Tournament tournament = new Tournament(name, password, teams);
                tournament.setId(id);
                events.add(tournament);
            }
            return events;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }
}

