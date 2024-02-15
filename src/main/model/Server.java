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

    // MODIFIES: this
    // EFFECTS: adds given player to the server, does nothing if player already exists
    public void addPlayer(Player player) {
        if (!players.contains(player)) {
            players.add(player);
        }
    }

    // EFFECTS: returns all existing players in the server
    public ArrayList<Player> getPlayers() {
        return players;
    }

    // MODIFIES: this, player, opponent
    // EFFECTS: given a player, finds a player in the server to match against. The two
    //          players must have the same gamemode and at maximum be one rank apart.
    //          After the match, update both players' rank and history according to whether
    //          each player lost or won. Finally, return matchmaking was successful.
    //          If no matching players found, do nothing and return no available players.
    public String matchMaking(Player player) {
        for (Player opponent : players) {
            if (rankCriteria(player, opponent)) {
                return "do more work";
            }
        }
        return "do even more work";
    }

    public boolean rankCriteria(Player p1, Player p2) {
        return true;
    }
}
