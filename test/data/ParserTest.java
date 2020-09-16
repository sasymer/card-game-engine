package data;

import org.junit.jupiter.api.Test;
import xml.Actions;
import xml.Configuration;
import xml.Rules;
import xml.XMLParser;

import java.io.File;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {
    private XMLParser parser;
    private File file;
    private Configuration config;
    private Actions actions;

    private void init() {
        parser = new XMLParser();
        file = new File("data/slapjack/slapjack.xml");
        Rules rules = parser.getRules(file);
        config = rules.getConfiguration();
        actions = rules.getActions();
    }

    @Test
    public void testDecks() {
        init();
        assertEquals(1, config.getDecks());
    }

    @Test
    public void testNumUserCards() {
        init();
        assertEquals(0, config.getNumUserCards());
    }

    @Test
    public void testSingleCenterPile() {
        init();
        assertTrue(config.hasSingleCenterPile());
    }

    @Test
    public void testDisplayUserPairs() {
        init();
        assertFalse(config.displayUserPairs());
    }

    @Test
    public void testCenterShowing() {
        init();
        assertTrue(config.centerShowing());
    }

    @Test
    public void testCheckCurrent() {
        init();
        assertFalse(config.checkCurrent());
    }

    @Test
    public void testFlippable() {
        init();
        assertFalse(config.isFlippable());
    }

    @Test
    public void testShowPairings() {
        init();
        assertFalse(config.hasPairings());
    }

    @Test
    public void testFannable() {
        init();
        assertFalse(config.isFannable());
    }

    @Test
    public void testUserSelectable() {
        init();
        assertFalse(config.userSelectable());
    }

    @Test
    public void testHasKeys() {
        init();
        assertTrue(config.hasKeys());
    }

    @Test
    public void testNeedCheckFinish() {
        init();
        assertFalse(config.needCheckFinish());
    }

    @Test
    public void testEndCondition() {
        init();
        assertTrue(config.getEndCondition());
    }

    @Test
    public void testDeckStyle() {
        init();
        assertEquals("normaldeck", config.getDeckStyle());
    }

    @Test
    public void testSetupMethods() {
        init();
        assertEquals(Arrays.asList("dealDeckToUsers"), actions.getSetupMethods());
    }

    @Test
    public void testTurnActions() {
        init();
        assertEquals(Arrays.asList("CheckAllCardsOneUser","ChangeTurn", "UserCardToCenter", "CheckAllCardsOneUser"), actions.getTurnActions());
    }

    @Test
    public void testSpecialActions() {
        init();
        assertEquals(Arrays.asList("CheckForJack"), actions.getSpecialActions());
    }
}
