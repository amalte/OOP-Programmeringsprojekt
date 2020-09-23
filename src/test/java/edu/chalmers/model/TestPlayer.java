package edu.chalmers.model;

import com.almasb.fxgl.physics.PhysicsComponent;
import org.junit.Assert;
import org.junit.Test;


public class TestPlayer{

    PlayerComponent p = new PlayerComponent(new PhysicsComponent());

    @Test
    public void testInflictDamage(){
        int temp = p.getHealth();
        p.inflictDamage(10);
        Assert.assertEquals(temp-10, p.getHealth());
    }
}
