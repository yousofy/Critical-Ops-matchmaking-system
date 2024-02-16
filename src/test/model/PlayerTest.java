package model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

// Test suits for the Player Class
class PlayerTest {
    private Player p1;
    private Player p2;
    private Player p3;
    private Player p4;

    @BeforeEach
    void setup() {
        p1 = new Player("MiesterZ", "Defuse");
        p2 = new Player("Cuddling", "1v1");
        p3 = new Player("Woozy", "GunGame");
        p4 = new Player("Yousof", "Defuse");
    }

    @Test
    void testConstructor() {
        assertEquals("MiesterZ", p1.getName());
        assertEquals("Defuse", p1.getGameMode());
        assertEquals(1, p1.getRank());
        assertTrue(p1.getHistory().isEmpty());
        assertTrue(p1.getSkins().isEmpty());
        assertEquals(0, p1.getReportCount());
        assertFalse(p1.isBanned());

        assertEquals("Cuddling", p2.getName());
        assertEquals("1v1", p2.getGameMode());
        assertEquals(1, p2.getRank());
        assertTrue(p2.getHistory().isEmpty());
        assertTrue(p2.getSkins().isEmpty());
        assertEquals(0, p2.getReportCount());
        assertFalse(p2.isBanned());
    }

    @Test
    void testGetDisplayRank() {
        assertEquals("Bronze", p1.getDisplayRank());
        p1.setRank(2);
        assertEquals("Silver", p1.getDisplayRank());
        p1.setRank(3);
        assertEquals("Gold", p1.getDisplayRank());
        p1.setRank(4);
        assertEquals("Platinum", p1.getDisplayRank());
        p1.setRank(5);
        assertEquals("Diamond", p1.getDisplayRank());
    }

    @Test
    void testUpdateRankNormalCase() {
        p1.setRank(3);
        p1.updateRank(true);
        assertEquals(4, p1.getRank());

        p2.setRank(3);
        p2.updateRank(false);
        assertEquals(2, p2.getRank());

        p3.updateRank(true);
        assertEquals(2, p3.getRank());

        p4.setRank(5);
        p4.updateRank(false);
        assertEquals(4, p4.getRank());
    }

    @Test
    void testUpdateRankSpecialCase() {
        p1.setRank(5);
        p1.updateRank(true);
        assertEquals(5, p1.getRank());

        p2.updateRank(false);
        assertEquals(1, p2.getRank());
    }

    @Test
    void testUpdateHistoryDifferentCase() {
        assertEquals(0, p1.getHistory().size());
        p1.updateHistory(true);
        assertEquals(1, p1.getHistory().size());
        assertEquals("won", p1.getHistory().get(0));
        p1.updateHistory(false);
        assertEquals(2, p1.getHistory().size());
        assertEquals("lost", p1.getHistory().get(1));

        assertEquals(0, p2.getHistory().size());
        p2.updateHistory(false);
        assertEquals(1, p2.getHistory().size());
        assertEquals("lost", p2.getHistory().get(0));
        p2.updateHistory(true);
        assertEquals(2, p2.getHistory().size());
        assertEquals("won", p2.getHistory().get(1));
    }

    @Test
    void testUpdateHistorySameCase() {
        assertEquals(0, p1.getHistory().size());
        p1.updateHistory(true);
        assertEquals(1, p1.getHistory().size());
        assertEquals("won", p1.getHistory().get(0));
        p1.updateHistory(true);
        assertEquals(2, p1.getHistory().size());
        assertEquals("won", p1.getHistory().get(1));

        assertEquals(0, p2.getHistory().size());
        p2.updateHistory(false);
        assertEquals(1, p2.getHistory().size());
        assertEquals("lost", p2.getHistory().get(0));
        p2.updateHistory(false);
        assertEquals(2, p2.getHistory().size());
        assertEquals("lost", p2.getHistory().get(1));
    }

    @Test
    void testClaimSkinsBronzeToGoldCase() {
        assertEquals(0, p1.getSkins().size());
        assertEquals("Rank too low! Gain a higher rank to receive skins." , p1.claimSkins());
        assertEquals(0, p1.getSkins().size());

        p2.setRank(2);
        assertEquals(0, p2.getSkins().size());
        assertEquals("Congratulations on reaching Silver! You received: XD.45 Inked." , p2.claimSkins());
        assertEquals(1, p2.getSkins().size());
        assertEquals("XD.45 Inked", p2.getSkins().get(0));

        p3.setRank(3);
        assertEquals(0, p3.getSkins().size());
        assertEquals("Congratulations on reaching Gold! You received: FP6 Catacomb." , p3.claimSkins());
        assertEquals(1, p3.getSkins().size());
        assertEquals("FP6 Catacomb", p3.getSkins().get(0));
    }

    @Test
    void testClaimSkinsPlatinumToDiamondCase() {
        p1.setRank(4);
        assertEquals(0, p1.getSkins().size());
        assertEquals("Congratulations on reaching Platinum! You received: AK-47 Koi." , p1.claimSkins());
        assertEquals(1, p1.getSkins().size());
        assertEquals("AK-47 Koi", p1.getSkins().get(0));

        p2.setRank(5);
        assertEquals(0, p2.getSkins().size());
        assertEquals("Congratulations on reaching Diamond! You received: Karambit Elite." , p2.claimSkins());
        assertEquals(1, p2.getSkins().size());
        assertEquals("Karambit Elite", p2.getSkins().get(0));
    }

    @Test
    void testHandleReport() {
        assertFalse(p1.isBanned());
        assertEquals(0, p1.getReportCount());
        assertEquals("Your report has been received. Thank you for your feedback.", p1.handleReport());
        assertFalse(p1.isBanned());
        assertEquals(1, p1.getReportCount());
        assertEquals("Your report has been received. Thank you for your feedback.", p1.handleReport());
        assertFalse(p1.isBanned());
        assertEquals(2, p1.getReportCount());
        assertEquals("Your report has been received. Thank you for your feedback.", p1.handleReport());
        assertFalse(p1.isBanned());
        assertEquals(3, p1.getReportCount());
        assertEquals("Your report has been received. Thank you for your feedback.", p1.handleReport());
        assertFalse(p1.isBanned());
        assertEquals(4, p1.getReportCount());
        assertEquals("Your report has been received. The player has been banned.", p1.handleReport());
        assertTrue(p1.isBanned());
        assertEquals(4, p1.getReportCount());
        assertEquals("The player has already been banned by previous reports.", p1.handleReport());
        assertTrue(p1.isBanned());
        assertEquals("The player has already been banned by previous reports.", p1.handleReport());
        assertTrue(p1.isBanned());
        assertEquals(4, p1.getReportCount());
    }

}