package com.example.rankingmaster.domain;

import java.util.Objects;

public class Tournament extends Entity<Long> {
    private String name;
    private String password;
    private Integer teams;

    public Tournament(String name, String password, Integer teams) {
        this.name = name;
        this.password = password;
        this.teams = teams;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getTeams() {
        return teams;
    }

    public void setTeams(Integer teams) {
        this.teams = teams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tournament that = (Tournament) o;
        return Objects.equals(password, that.password) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password, teams);
    }
}
