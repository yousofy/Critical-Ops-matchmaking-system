package model;

import java.util.*;

// Description: Represents a player having an id, name, rank, gamemode,
//              match history, gun skins, report count, and ban status.
public class Player {
    private static final int BRONZE = 1;
    private static final int SILVER = 2;
    private static final int GOLD = 3;
    private static final int PLATINUM = 4;
    private static final int DIAMOND = 5;

    private String name;                 // the name of the player
    private String gameMode;             // the gamemode of the player
    private int id;                      // the unique id number of the player
    private static int nextId = 1;       // provides the id for the next player
    private int rank;                    // the rank of the player
    private ArrayList<String> history;   // the record of all previous games of the player
    private ArrayList<String> skins;       // all the gun skins the player owns
    private int reportCount;             // the number of reports the player has received
    private boolean banned;              // the player's ban status

    // REQUIRES: name and gameMode are non-empty strings, gameMode is a valid type
    // EFFECTS: creates a player profile with given name and gamemode.
    //          The player is given a unique id, starts with the first rank,
    //          has a history of no games played, has no cosmetic items,
    //          has 0 reports on their name, and is not banned from playing.
    public Player(String name, String gameMode) {
        this.name = name;
        this.gameMode = gameMode;
        id = nextId++;
        rank = BRONZE;
        history = new ArrayList<>();
        skins = new ArrayList<>();
        reportCount = 0;
        banned = false;
    }

    public String getName() {
        return name;
    }

    public String getGameMode() {
        return gameMode;
    }

    public int getId() {
        return id;
    }

    public int getRank() {
        return rank;
    }

    public ArrayList<String> getHistory() {
        return history;
    }

    public ArrayList<String> getSkins() {
        return skins;
    }

    public int getReportCount() {
        return reportCount;
    }

    public boolean isBanned() {
        return banned;
    }

}
// add a method to claim rewards (skins)
// add a method to report another player
