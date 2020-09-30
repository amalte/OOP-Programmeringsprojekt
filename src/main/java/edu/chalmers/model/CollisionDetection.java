package edu.chalmers.model;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import edu.chalmers.model.enemy.EnemyComponent;

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

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.BLOCK) {
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

        // ------ENEMY------ //

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.ENEMY, EntityType.PLATFORM) {
            @Override
            protected void onCollisionBegin(Entity a, Entity b) {
                a.getComponent(EnemyComponent.class).resetJumpAmounts();
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.ENEMY, EntityType.BLOCK) {
            @Override
            protected void onCollisionBegin(Entity a, Entity b) {
                a.getComponent(EnemyComponent.class).resetJumpAmounts();
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.ENEMY, EntityType.PROJECTILE) {
            @Override
            protected void onCollisionBegin(Entity a, Entity b) {
                //ToDo implement direct contact with projectile to receive proper damage and not from player.
                a.getComponent(EnemyComponent.class).inflictDamage(player.getWeapon().getDamage());
                b.removeFromWorld();
            }
        });
    }
}
