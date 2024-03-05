package persistence;

import model.Player;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkPlayer(String name, String gameMode, int rank, ArrayList<String> history,
                               ArrayList<String> skins, int reportCount, boolean banned, Player player) {
        assertEquals(name, player.getName());
        assertEquals(gameMode, player.getGameMode());
        assertEquals(rank, player.getRank());
        assertEquals(history, player.getHistory());
        assertEquals(skins, player.getSkins());
        assertEquals(reportCount, player.getReportCount());
        assertEquals(banned, player.isBanned());
    }
}
