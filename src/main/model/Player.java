package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

// Description: Represents a player having a unique name, rank, game mode,
//              match history, gun skins, report count, and ban status.
public class Player implements Writable {
    private static final int BRONZE = 1;
    private static final int SILVER = 2;
    private static final int GOLD = 3;
    private static final int PLATINUM = 4;
    private static final int DIAMOND = 5;
    private static final int MAXREPORT = 4;

    private String name;                 // the unique name of the player
    private String gameMode;             // the game mode of the player
    private int rank;                    // the rank of the player
    private ArrayList<String> history;   // the record of all previous games of the player
    private ArrayList<String> skins;     // all the gun skins the player owns
    private int reportCount;             // the number of reports the player has received
    private boolean banned;              // the player's ban status

    // REQUIRES: name and gameMode are non-empty strings, gameMode is a valid type
    // EFFECTS: creates a player profile with given name and game mode.
    //          The player starts with the first rank which is Bronze,
    //          has a history of no games played, has no cosmetic items,
    //          has 0 reports on their name, and is not banned from playing.
    public Player(String name, String gameMode) {
        this.name = name;
        this.gameMode = gameMode;
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

    public int getRank() {
        return rank;
    }

    // EFFECTS: returns the player's rank description
    public String getDisplayRank() {
        if (BRONZE == getRank()) {
            return "Bronze";
        } else if (SILVER == getRank()) {
            return "Silver";
        } else if (GOLD == getRank()) {
            return "Gold";
        } else if (PLATINUM == getRank()) {
            return "Platinum";
        } else {
            return "Diamond";
        }
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public ArrayList<String> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<String> history) {
        this.history = history;
    }

    public ArrayList<String> getSkins() {
        return skins;
    }

    public void setSkins(ArrayList<String> skins) {
        this.skins = skins;
    }

    public int getReportCount() {
        return reportCount;
    }

    public void setReportCount(int reportCount) {
        this.reportCount = reportCount;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    // MODIFIES: this
    // EFFECTS: updates the player's rank according to result. If result is true, advances
    //          the player's rank by one level. Otherwise, de-ranks the player by one level.
    //          If the player is already at the highest rank, does nothing if result is true.
    //          If the player is already at the lowest rank, does nothing if result is false.
    public void updateRank(boolean result) {
        if (result && getRank() != 5) {
            rank += 1;
            EventLog.getInstance().logEvent(new Event("Player rank increased."));
        } else if (!result && getRank() != 1) {
            rank -= 1;
            EventLog.getInstance().logEvent(new Event("Player rank decreased."));
        } else {
            EventLog.getInstance().logEvent(new Event("Player rank did not change."));
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the player's match history according to result. If result is true,
    //          adds "won" to the match history. Otherwise, adds "lost" to the match history.
    public void updateHistory(boolean result) {
        if (result) {
            history.add("won");
            EventLog.getInstance().logEvent(new Event("Added win to history."));
        } else {
            history.add("lost");
            EventLog.getInstance().logEvent(new Event("Added loss to history."));
        }
    }

    // MODIFIES: this
    // EFFECTS: awards the player a specific skin according to their rank. The player will receive no
    //          skins if they are at the lowest rank and the method will return the rank is too low.
    //          The skin is added to the player's skins collection and a confirmation message is returned.
    public String claimSkins() {
        if (BRONZE == getRank()) {
            EventLog.getInstance().logEvent(new Event("Added nothing to inventory."));
            return "Rank too low! Gain a higher rank to receive skins.";
        } else if (SILVER == getRank()) {
            skins.add("XD.45 Inked");
            EventLog.getInstance().logEvent(new Event("Added XD.45 Inked to inventory."));
            return "Congratulations on reaching Silver! You received: XD.45 Inked.";
        } else if (GOLD == getRank()) {
            skins.add("FP6 Catacomb");
            EventLog.getInstance().logEvent(new Event("Added FP6 Catacomb Inked to inventory."));
            return "Congratulations on reaching Gold! You received: FP6 Catacomb.";
        } else if (PLATINUM == getRank()) {
            skins.add("AK-47 Koi");
            EventLog.getInstance().logEvent(new Event("Added AK-47 Koi Inked to inventory."));
            return "Congratulations on reaching Platinum! You received: AK-47 Koi.";
        } else {
            skins.add("Karambit Elite");
            EventLog.getInstance().logEvent(new Event("Added Karambit Elite Inked to inventory."));
            return "Congratulations on reaching Diamond! You received: Karambit Elite.";
        }
    }

    // MODIFIES: this
    // EFFECTS: adds one to the player's report count and returns report successful. If the player
    //          exceeds the maximum report count, bans the player and returns player banned. If the
    //          player is already banned, does nothing and returns the player is already banned.
    public String handleReport() {
        if (isBanned()) {
            EventLog.getInstance().logEvent(new Event("Report count did not change."));
            return "The player has already been banned by previous reports.";
        } else if (getReportCount() < MAXREPORT) {
            reportCount++;
            EventLog.getInstance().logEvent(new Event("Report count increased."));
            return "Your report has been received. Thank you for your feedback.";
        } else {
            banned = true;
            EventLog.getInstance().logEvent(new Event("Report count increased and banned player."));
            return "Your report has been received. The player has been banned.";
        }
    }

    // EFFECTS: returns this as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("gameMode", gameMode);
        json.put("rank", rank);
        json.put("history", history);
        json.put("skins", skins);
        json.put("reportCount", reportCount);
        json.put("banned", banned);
        return json;
    }

}

