package persistence;

import model.Player;
import model.Server;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Server sv = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyServer() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyServer.json");
        try {
            Server sv = reader.read();
            assertEquals(0, sv.getPlayers().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralServer() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralServer.json");
        try {
            Server sv = reader.read();
            ArrayList<Player> players = sv.getPlayers();
            assertEquals(2, players.size());

            ArrayList<String> woozyHistory = new ArrayList<>();
            woozyHistory.add("won");
            woozyHistory.add("lost");
            ArrayList<String> woozySkins = new ArrayList<>();
            woozySkins.add("XD.45 Inked");
            woozySkins.add("FP6 Catacomb");
            checkPlayer("Woozy", "1v1", 2, woozyHistory, woozySkins, 3, false, players.get(0));

            ArrayList<String> MiesterZHistory = new ArrayList<>();
            MiesterZHistory.add("lost");
            MiesterZHistory.add("lost");
            ArrayList<String> MiesterZSkins = new ArrayList<>();
            MiesterZSkins.add("AK-47 Koi");
            MiesterZSkins.add("AK-47 Koi");
            checkPlayer("MiesterZ", "Defuse", 3, MiesterZHistory, MiesterZSkins, 4, true, players.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}

