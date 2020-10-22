package edu.chalmers.model.building.blocks;
import org.junit.jupiter.api.Test;

import static org.testng.AssertJUnit.assertFalse;

/**
 * @author Malte Åkvist
 * <p>
 * Test class for PermanentBlock.
 */
public class TestPermanentBlock {
    private PermanentBlock permanentBlock = new PermanentBlock();

    @Test
    public void testCanBeDestroyed() {
        assertFalse(permanentBlock.canBeDestroyed());
    }

    @Test
    public void testRemove() {
        permanentBlock.remove();
    }
}
