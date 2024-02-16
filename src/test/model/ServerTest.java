package model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

// Test suits for the Server Class
public class ServerTest {
    private Server sv;
    private Player p1;
    private Player p2;
    private Player p3;
    private Player p4;

    @BeforeEach
    void setup() {
        sv = new Server();
        p1 = new Player("MiesterZ", "Defuse");
        p2 = new Player("Cuddling", "1v1");
        p3 = new Player("Woozy", "GunGame");
        p4 = new Player("Yousof", "Defuse");
        sv.addPlayer(p1);
        sv.addPlayer(p2);
        sv.addPlayer(p3);
        sv.addPlayer(p4);
    }

    @Test
    void testConstructor() {
        sv = new Server();
        assertTrue(sv.getPlayers().isEmpty());
    }

    @Test
    void testAddPlayer() {
        sv = new Server();
        assertEquals(0, sv.getPlayers().size());
        sv.addPlayer(p1);
        assertEquals(1, sv.getPlayers().size());
        sv.addPlayer(p1);
        assertEquals(1, sv.getPlayers().size());
        sv.addPlayer(p2);
        assertEquals(2, sv.getPlayers().size());
        sv.addPlayer(p3);
        assertEquals(3, sv.getPlayers().size());
        sv.addPlayer(p3);
        assertEquals(3, sv.getPlayers().size());
    }

    @Test
    void testFindPlayer() {
        assertNull(sv.findPlayer("Nefin"));
        assertEquals(p1, sv.findPlayer("MiesterZ"));
        assertEquals(p2, sv.findPlayer("Cuddling"));
        assertEquals(p3, sv.findPlayer("Woozy"));
        assertEquals(p4, sv.findPlayer("Yousof"));
    }

    @Test
    void testMatchmakingNotWorkingCases() {
        sv = new Server();
        assertEquals("No players found for matchmaking. Try again later.", sv.matchmaking(p1, true));
        assertEquals("No players found for matchmaking. Try again later.", sv.matchmaking(p1, false));
        p1.setBanned(true);
        assertEquals("Banned players cannot play. Matchmaking failed.", sv.matchmaking(p1, true));
        assertEquals("Banned players cannot play. Matchmaking failed.", sv.matchmaking(p1, false));
        sv.addPlayer(p1);
        sv.addPlayer(p2);
        sv.addPlayer(p3);
        sv.addPlayer(p4);
        assertEquals("Banned players cannot play. Matchmaking failed.", sv.matchmaking(p1, true));
        assertEquals("Banned players cannot play. Matchmaking failed.", sv.matchmaking(p1, false));
        p1.setBanned(false);
        p1.setRank(5);
        assertEquals("No players found for matchmaking. Try again later.", sv.matchmaking(p1, true));
        assertEquals("No players found for matchmaking. Try again later.", sv.matchmaking(p1, false));
    }

    @Test
    void testMatchmakingWinWorkingCase() {
        assertEquals("Congratulations on winning! Matchmaking successful.", sv.matchmaking(p1, true));
        assertEquals(2, p1.getRank());
        assertEquals(1, p4.getRank());
        assertEquals("Congratulations on winning! Matchmaking successful.", sv.matchmaking(p1, true));
        assertEquals(3, p1.getRank());
        assertEquals(1, p4.getRank());
        assertEquals("No players found for matchmaking. Try again later.", sv.matchmaking(p1, true));
        assertEquals("won", p1.getHistory().get(0));
        assertEquals("lost", p4.getHistory().get(0));
        assertEquals("won", p1.getHistory().get(1));
        assertEquals("lost", p4.getHistory().get(1));

    }

    @Test
    void testMatchmakingLoseWorkingCase() {
        assertEquals("Better luck next time... Matchmaking successful.", sv.matchmaking(p1, false));
        assertEquals(1, p1.getRank());
        assertEquals(2, p4.getRank());
        assertEquals("Better luck next time... Matchmaking successful.", sv.matchmaking(p1, false));
        assertEquals(1, p1.getRank());
        assertEquals(3, p4.getRank());
        assertEquals("No players found for matchmaking. Try again later.", sv.matchmaking(p1, false));
        assertEquals("lost", p1.getHistory().get(0));
        assertEquals("won", p4.getHistory().get(0));
        assertEquals("lost", p1.getHistory().get(1));
        assertEquals("won", p4.getHistory().get(1));
    }

    @Test
    void testMatchmakingMixedWorkingCase() {
        assertEquals("Congratulations on winning! Matchmaking successful.", sv.matchmaking(p1, true));
        assertEquals(2, p1.getRank());
        assertEquals(1, p4.getRank());
        assertEquals("Better luck next time... Matchmaking successful.", sv.matchmaking(p1, false));
        assertEquals(1, p1.getRank());
        assertEquals(2, p4.getRank());
        assertEquals("Congratulations on winning! Matchmaking successful.", sv.matchmaking(p1, true));
        assertEquals(2, p1.getRank());
        assertEquals(1, p4.getRank());
        assertEquals("Better luck next time... Matchmaking successful.", sv.matchmaking(p1, false));
        assertEquals(1, p1.getRank());
        assertEquals(2, p4.getRank());
        assertEquals("won", p1.getHistory().get(0));
        assertEquals("lost", p4.getHistory().get(0));
        assertEquals("lost", p1.getHistory().get(1));
        assertEquals("won", p4.getHistory().get(1));
        assertEquals("won", p1.getHistory().get(2));
        assertEquals("lost", p4.getHistory().get(2));
        assertEquals("lost", p1.getHistory().get(3));
        assertEquals("won", p4.getHistory().get(3));
    }

    @Test
    void testMatchCriteria() {
        assertFalse(sv.matchCriteria(p1, p2));
        assertFalse(sv.matchCriteria(p2, p3));
        assertTrue(sv.matchCriteria(p1, p4));
        p4.setBanned(true);
        assertFalse(sv.matchCriteria(p1, p4));
        p4.setBanned(false);
        assertTrue(sv.matchCriteria(p1, p4));
        p1.setRank(2);
        assertTrue(sv.matchCriteria(p1, p4));
        p1.setRank(3);
        assertFalse(sv.matchCriteria(p1, p4));
        p4.setRank(2);
        assertTrue(sv.matchCriteria(p1, p4));
        p4.setRank(3);
        assertTrue(sv.matchCriteria(p1, p4));
        p4.setRank(4);
        assertTrue(sv.matchCriteria(p1, p4));
        p4.setRank(5);
        assertFalse(sv.matchCriteria(p1, p4));
        p1.setRank(1);
        assertFalse(sv.matchCriteria(p1, p4));
        p1.setRank(5);
        assertTrue(sv.matchCriteria(p1, p4));
    }

}
