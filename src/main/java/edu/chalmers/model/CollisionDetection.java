package edu.chalmers.model;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.CollisionHandler;
import edu.chalmers.model.enemy.EnemyComponent;
import edu.chalmers.model.enemy.enemytypes.BlobComponent;
import edu.chalmers.model.enemy.enemytypes.RexComponent;
import edu.chalmers.model.enemy.enemytypes.ZombieComponent;

import java.util.ArrayList;
import java.util.List;

public class CollisionDetection {

    /**
     * Handle all entity Collision that has a direct effect on either one or both of the Entities.
     */
    public void initCollisionHandler(PlayerComponent player){
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.PLATFORM) {
            @Override
            protected void onCollisionBegin(Entity a, Entity b) {
                a.getComponent(PlayerComponent.class).resetJumpAmounts();
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.ENEMY) {
            @Override
            protected void onCollision(Entity a, Entity b) {
                a.getComponent(PlayerComponent.class).inflictDamage(b.getComponent(EnemyComponent.class).getDamage());
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.ENEMY, EntityType.PROJECTILE) {
            @Override
            protected void onCollisionBegin(Entity a, Entity b) {
                //ToDo implement direct contact with projectile to receive proper damage and not from player.

                // Find the correct Enemy component.
                if(a.getComponents().contains(ZombieComponent.class)) {
                    a.getComponent(ZombieComponent.class).inflictDamage(player.getWeapon().getDamage());
                }
                else if(a.getComponents().contains(RexComponent.class)) {
                    a.getComponent(RexComponent.class).inflictDamage(player.getWeapon().getDamage());
                }
                else if(a.getComponents().contains(BlobComponent.class)) {
                    a.getComponent(BlobComponent.class).inflictDamage(player.getWeapon().getDamage());
                }
                else {
                    return;
                }

                b.removeFromWorld();
            }
        });
    }
}
