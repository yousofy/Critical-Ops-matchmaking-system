package model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

// Test suits for the Player Class
class PlayerTest {
    private Player p1;
    private Player p2;

    @BeforeEach
    void setup() {
        p1 = new Player("Nefin", "1v1");
        p2 = new Player("Cuddling", "Defuse");
    }

    @Test
    void testConstructor() {
        assertEquals("Nefin", p1.getName());
        assertEquals("1v1", p1.getGameMode());
        assertEquals(1, p1.getId());
        assertEquals(1, p1.getRank());
        assertTrue(p1.getHistory().isEmpty());
        assertTrue(p1.getSkins().isEmpty());
        assertEquals(0, p1.getReportCount());
        assertFalse(p1.isBanned());

        assertEquals("Cuddling", p2.getName());
        assertEquals("Defuse", p2.getGameMode());
        assertEquals(2, p2.getId());
        assertEquals(1, p2.getRank());
        assertTrue(p2.getHistory().isEmpty());
        assertTrue(p2.getSkins().isEmpty());
        assertEquals(0, p2.getReportCount());
        assertFalse(p2.isBanned());
    }
}