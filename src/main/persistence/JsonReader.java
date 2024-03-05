package persistence;

import model.Player;
import model.Server;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.json.*;

// Description: Represents a reader that reads server from JSON data stored in file
//              with the code being inspired by the JsonSerializationDemo application
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads server from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Server read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseServer(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses server from JSON object and returns it
    private Server parseServer(JSONObject jsonObject) {
        Server sv = new Server();
        addThingies(sv, jsonObject);
        return sv;
    }

    // MODIFIES: sv
    // EFFECTS: parses players from JSON object and adds them to server
    private void addThingies(Server sv, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("players");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addPlayer(sv, nextThingy);
        }
    }

    // MODIFIES: sv
    // EFFECTS: parses player from JSON object and adds it to server
    private void addPlayer(Server sv, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String gameMode = jsonObject.getString("gameMode");
        int rank = jsonObject.getInt("rank");
        ArrayList<String> history = jsonArrayToArrayList(jsonObject.getJSONArray("history"));
        ArrayList<String> skins = jsonArrayToArrayList(jsonObject.getJSONArray("skins"));
        int reportCount = jsonObject.getInt("reportCount");
        boolean banned = jsonObject.getBoolean("banned");
        Player player = new Player(name, gameMode);
        player.setRank(rank);
        player.setHistory(history);
        player.setSkins(skins);
        player.setReportCount(reportCount);
        player.setBanned(banned);
        sv.addPlayer(player);
    }

    // Source: stackoverflow Converting JSONArray to ArrayList
    // REQUIRES: jsonArray must not be null and contain only strings
    // EFFECTS: converts a jsonArray to ArrayList
    public ArrayList<String> jsonArrayToArrayList(JSONArray jsonArray) {
        ArrayList<String> list = new ArrayList<String>();
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(jsonArray.getString(i));
            }
        }
        return list;
    }
}

