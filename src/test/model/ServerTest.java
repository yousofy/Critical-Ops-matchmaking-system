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

    @BeforeEach
    void setup() {
        sv = new Server();
        p1 = new Player("Nefin", "1v1");
        p2 = new Player("Cuddling", "Defuse");
        p3 = new Player("MiesterZ", "Elimination");
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
    void testGetPlayers() {
        sv.addPlayer(p1);
        sv.addPlayer(p2);
        sv.addPlayer(p3);
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        assertEquals(players, sv.getPlayers());
    }
}
