package edu.chalmers.model.enemy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestStatMultiplier {

    @Test
    public void testStatMultiplierDefault() {
        StatMultiplier statMultiplier = new StatMultiplier();
        assertEquals(1, statMultiplier.getHealthMultiplier());
        assertEquals(1, statMultiplier.getDmgMultiplier());
        assertEquals(1, statMultiplier.getSpeedMultiplier());
        assertEquals(1, statMultiplier.getJmpHeightMultiplier());
    }

    @Test
    public void testStatMultiplierBelowOne() {
        StatMultiplier statMultiplier = new StatMultiplier(-1, 0.4, 0.7, -2);
        assertEquals(1, statMultiplier.getHealthMultiplier());
        assertEquals(1, statMultiplier.getDmgMultiplier());
        assertEquals(1, statMultiplier.getSpeedMultiplier());
        assertEquals(1, statMultiplier.getJmpHeightMultiplier());
    }

    @Test
    public void testStatMultiplierOne() {
        StatMultiplier statMultiplier = new StatMultiplier(3);
        assertEquals(3, statMultiplier.getHealthMultiplier());
        assertEquals(1, statMultiplier.getDmgMultiplier());
        assertEquals(1, statMultiplier.getSpeedMultiplier());
        assertEquals(1, statMultiplier.getJmpHeightMultiplier());
    }

    @Test
    public void testStatMultiplierTwo() {
        StatMultiplier statMultiplier = new StatMultiplier(3, 5);
        assertEquals(3, statMultiplier.getHealthMultiplier());
        assertEquals(5, statMultiplier.getDmgMultiplier());
        assertEquals(1, statMultiplier.getSpeedMultiplier());
        assertEquals(1, statMultiplier.getJmpHeightMultiplier());
    }

    @Test
    public void testStatMultiplierThree() {
        StatMultiplier statMultiplier = new StatMultiplier(3, 5, 7);
        assertEquals(3, statMultiplier.getHealthMultiplier());
        assertEquals(5, statMultiplier.getDmgMultiplier());
        assertEquals(7, statMultiplier.getSpeedMultiplier());
        assertEquals(1, statMultiplier.getJmpHeightMultiplier());
    }

    @Test
    public void testStatMultiplierFour() {
        StatMultiplier statMultiplier = new StatMultiplier(3, 5, 7, 9);
        assertEquals(3, statMultiplier.getHealthMultiplier());
        assertEquals(5, statMultiplier.getDmgMultiplier());
        assertEquals(7, statMultiplier.getSpeedMultiplier());
        assertEquals(9, statMultiplier.getJmpHeightMultiplier());
    }
}
