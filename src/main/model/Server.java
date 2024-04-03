package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

// Description: Represents the game's server which holds all active
//              players in the server and can perform operations on them.
public class Server implements Writable {
    private ArrayList<Player> players;       // all the players that exist in the server

    // EFFECTS: creates a new server with no existing players
    public Server() {
        players = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds given player to the server, does nothing if player's name already exists
    public String addPlayer(Player player) {
        for (Player p : players) {
            if (p.getName().equals(player.getName())) {
                EventLog.getInstance().logEvent(new Event("Player not created."));
                return "A player with the same name exists. Please choose a different name.";
            }
        }
        players.add(player);
        EventLog.getInstance().logEvent(new Event("Player created."));
        return "Player created successfully!";
    }

    public ArrayList<Player> getPlayers() {
        EventLog.getInstance().logEvent(new Event("Players shown."));
        return players;
    }

    // EFFECTS: given a player's name, returns the player if they are in the server. Otherwise, returns null
    public Player findPlayer(String name) {
        for (Player player : players) {
            if (name.equals(player.getName())) {
                return player;
            }
        }
        return null;
    }

    // MODIFIES: player, opponent
    // EFFECTS: If the player is banned, does nothing and returns banned players cannot play.
    //          Otherwise, given a player and match result (true = win, false = lose), finds a different
    //          player to match against. The two players must meet the matchmaking criteria according to
    //          matchCriteria. After the match, update both players' rank and history according to
    //          whether each player lost or won. Finally, returns matchmaking was successful.
    //          If no matching players were found at all, does nothing and returns no available players.
    public String matchmaking(Player player, boolean result) {
        if (player.isBanned()) {
            EventLog.getInstance().logEvent(new Event("Matchmaking did not occur (banned)."));
            return "Banned players cannot play. Matchmaking failed.";
        }
        for (Player opponent : players) {
            if (matchCriteria(player, opponent) && player != opponent) {
                player.updateRank(result);
                player.updateHistory(result);
                opponent.updateRank(!result);
                opponent.updateHistory(!result);
                if (result) {
                    EventLog.getInstance().logEvent(new Event("Matchmaking did occur (win)."));
                    return "Congratulations on winning! Matchmaking successful.";
                } else {
                    EventLog.getInstance().logEvent(new Event("Matchmaking did occur (loss)."));
                    return "Better luck next time... Matchmaking successful.";
                }
            }
        }
        EventLog.getInstance().logEvent(new Event("Matchmaking did not occur (no players)."));
        return "No players found for matchmaking. Try again later.";
    }

    // REQUIRES: !player.isBanned()
    // EFFECTS: Returns true if both given players have the same game mode and are at maximum
    //          one rank apart and if opponent is unbanned. Otherwise, returns false.
    public boolean matchCriteria(Player player, Player opponent) {
        return !opponent.isBanned() && player.getGameMode().equals(opponent.getGameMode())
                && player.getRank() + 1 >= opponent.getRank() && player.getRank() - 1 <= opponent.getRank();
    }

    // EFFECTS: returns this as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("players", playersToJson());
        return json;
    }

    // EFFECTS: returns players in this server as a JSON array
    private JSONArray playersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Player p : players) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }

}
