package model;

import org.junit.jupiter.api.*;

import java.util.ArrayList;

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
    }

    @Test
    void testConstructor() {
        assertTrue(sv.getPlayers().isEmpty());
    }

    @Test
    void testAddPlayer() {
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
    void testMatchCriteriaRankDifferenceCase() {
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

    @Test
    void testReport() {
        sv.addPlayer(p1);
        sv.addPlayer(p2);
        sv.addPlayer(p3);
        sv.addPlayer(p4);
        assertEquals("Player with given name is not found. Please try again.", sv.report("Surge"));
        assertEquals(p1.handleReport(), sv.report("MiesterZ"));
        assertEquals(p3.handleReport(), sv.report("Woozy"));
        assertEquals(p3.handleReport(), sv.report("Woozy"));
        assertEquals(p4.handleReport(), sv.report("Yousof"));
    }

}
