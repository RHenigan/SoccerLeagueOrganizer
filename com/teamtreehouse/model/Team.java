package com.teamtreehouse.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Team implements Comparable{
    private String teamName;
    private String coachName;
    private Set<Player> players = new TreeSet<>();
    private int expPerc = 0;

    public Team(String teamName, String coachName) {
        this.teamName = teamName;
        this.coachName = coachName;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getCoachName() {
        return coachName;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public List<Player> getPlayersAsList() {
        List<Player> playerList = new ArrayList<>();
        playerList.addAll(players);
        return playerList;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setExpPerc() {
        int expCount = 0;
        float expPercFloat = 0;
        for (Player player : players) {
            if (player.isPreviousExperience()) {
                expCount++;
            }
            expPercFloat = 100 * expCount/players.size();
        }
        expPerc = (int) expPercFloat;
    }

    public int getExpPerc() {
        return expPerc;
    }

    @Override
    public int compareTo(Object o) {
        Team other = (Team) o;
        return this.getTeamName().compareTo(other.getTeamName());
    }
}