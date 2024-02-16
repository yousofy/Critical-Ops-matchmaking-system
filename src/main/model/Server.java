package model;

import java.util.*;

// Description: Represents the game's server which holds all active
//              players in the server and can perform operations on them.
public class Server {
    private ArrayList<Player> players;       // all the players that exist in the server

    // EFFECTS: creates a new server with no existing players
    public Server() {
        players = new ArrayList<>();
    }

    // REQUIRES: the player's name is not already contained in the server
    // MODIFIES: this
    // EFFECTS: adds given player to the server, does nothing if player already exists
    public void addPlayer(Player player) {
        if (!players.contains(player)) {
            players.add(player);
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    // MODIFIES: player, opponent
    // EFFECTS: If the player is banned, does nothing and returns banned players cannot play.
    //          Otherwise, given a player and match result (true=win, false=lose), finds a different
    //          player to match against. The two players must meet the matchmaking criteria according to
    //          matchCriteria. After the match, update both players' rank and history according to
    //          whether each player lost or won. Finally, returns matchmaking was successful.
    //          If no matching players were found at all, does nothing and returns no available players.
    public String matchmaking(Player player, boolean result) {
        if (player.isBanned()) {
            return "Banned players cannot play. Matchmaking failed.";
        }
        for (Player opponent : players) {
            if (matchCriteria(player, opponent) && player != opponent) {
                player.updateRank(result);
                player.updateHistory(result);
                opponent.updateRank(!result);
                opponent.updateHistory(!result);
                if (result) {
                    return "Congratulations on winning! Matchmaking successful.";
                } else {
                    return "Better luck next time... Matchmaking successful.";
                }
            }
        }
        return "No players found for matchmaking. Try again later.";
    }

    // REQUIRES: !player.isBanned()
    // EFFECTS: Returns true if both given players have the same game mode and are at maximum
    //          one rank apart and if opponent is unbanned. Otherwise, returns false.
    public boolean matchCriteria(Player player, Player opponent) {
        return !opponent.isBanned() && player.getGameMode() == opponent.getGameMode()
                && player.getRank() + 1 >= opponent.getRank() && player.getRank() - 1 <= opponent.getRank();
    }

    // REQUIRES: name is a non-empty string
    // MODIFIES: player
    // EFFECTS: reports the player corresponding to the given name and returns the relevant message depending on
    //          the report, does nothing if no such player is found and returns the player was not found.
    public String report(String name) {
        for (Player player : players) {
            if (name == player.getName()) {
                return player.handleReport();
            }
        }
        return "Player with given name is not found. Please try again.";
    }

}
