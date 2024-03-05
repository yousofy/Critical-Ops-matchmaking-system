package persistence;

import model.Player;
import model.Server;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Server wr = new Server();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Server sv = new Server();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyServer.json");
            writer.open();
            writer.write(sv);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyServer.json");
            sv = reader.read();
            assertEquals(0, sv.getPlayers().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralServer() {
        try {
            Server sv = new Server();
            sv.addPlayer(setupCuddling());
            sv.addPlayer(setupYousof());
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralServer.json");
            writer.open();
            writer.write(sv);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralServer.json");
            sv = reader.read();
            List<Player> players = sv.getPlayers();
            assertEquals(2, players.size());

            ArrayList<String> cuddlingHistory = new ArrayList<>();
            cuddlingHistory.add("won");
            cuddlingHistory.add("won");
            ArrayList<String> cuddlingSkins = new ArrayList<>();
            cuddlingSkins.add("Karambit Elite");
            cuddlingSkins.add("FP6 Catacomb");
            checkPlayer("Cuddling", "GunGame", 4, cuddlingHistory, cuddlingSkins, 4, true, players.get(0));

            ArrayList<String> yousofHistory = new ArrayList<>();
            yousofHistory.add("lost");
            yousofHistory.add("won");
            ArrayList<String> yousofSkins = new ArrayList<>();
            yousofSkins.add("XD.45 Inked");
            yousofSkins.add("XD.45 Inked");
            checkPlayer("Yousof", "1v1", 2, yousofHistory, yousofSkins, 0, false, players.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    Player setupCuddling() {
        Player cuddling = new Player("Cuddling", "GunGame");
        cuddling.setRank(4);
        ArrayList<String> cuddlingNewHistory = new ArrayList<>();
        cuddlingNewHistory.add("won");
        cuddlingNewHistory.add("won");
        cuddling.setHistory(cuddlingNewHistory);
        ArrayList<String> cuddlingNewSkins = new ArrayList<>();
        cuddlingNewSkins.add("Karambit Elite");
        cuddlingNewSkins.add("FP6 Catacomb");
        cuddling.setSkins(cuddlingNewSkins);
        cuddling.setReportCount(4);
        cuddling.setBanned(true);
        return cuddling;
    }

    Player setupYousof() {
        Player yousof = new Player("Yousof", "1v1");
        yousof.setRank(2);
        ArrayList<String> yousofNewHistory = new ArrayList<>();
        yousofNewHistory.add("lost");
        yousofNewHistory.add("won");
        yousof.setHistory(yousofNewHistory);
        ArrayList<String> yousofNewSkins = new ArrayList<>();
        yousofNewSkins.add("XD.45 Inked");
        yousofNewSkins.add("XD.45 Inked");
        yousof.setSkins(yousofNewSkins);
        yousof.setReportCount(0);
        yousof.setBanned(false);
        return yousof;
    }
}