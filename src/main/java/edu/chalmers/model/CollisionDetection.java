package edu.chalmers.model;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import edu.chalmers.model.building.blocks.Block;
import edu.chalmers.model.enemy.EnemyComponent;
import edu.chalmers.utilities.EntityPos;

/**
 * @author Oscar Arvidson
 * <p>
 * CollisionDetection class, handles all collision in the game.
 */
public class CollisionDetection {

    /**
     * Handle all entity Collision that has a direct effect on either one or both of the Entities.
     */
    public CollisionDetection(PlayerComponent player) {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.PLATFORM) {
            @Override
            protected void onCollisionBegin(Entity a, Entity b) {
                if (aboveMiddleCollision(a, b) && !sideCollision(a, b)) {  // Can only jump if standing above and on platform.
                    if (a.hasComponent(PlayerComponent.class)) {
                        a.getComponent(PlayerComponent.class).landed();
                        // onGround check.
                        // If the platform has X-Position of 0, then the platform is the ground.
                        if (b.getX() == 0) {
                            a.getComponent(PlayerComponent.class).setOnGround(true);
                        } else {
                            a.getComponent(PlayerComponent.class).setOnGround(false);
                        }
                        a.getComponent(PlayerComponent.class).setAirborne(false);
                    }
                }
            }

            @Override
            protected void onCollisionEnd(Entity a, Entity b) {
                if (a.hasComponent(PlayerComponent.class)) {
                    a.getComponent(PlayerComponent.class).setAirborne(true);
                }
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.BLOCK) {
            @Override
            protected void onCollisionBegin(Entity a, Entity b) {

                if (aboveMiddleCollision(a, b) && !sideCollision(a, b)) {  // Can only jump if standing above and on block

                    if (a.hasComponent(PlayerComponent.class)) {
                        a.getComponent(PlayerComponent.class).landed();
                        a.getComponent(PlayerComponent.class).setAirborne(false);
                    }
                }
            }

            @Override
            protected void onCollisionEnd(Entity a, Entity b) {
                if (a.hasComponent(PlayerComponent.class)) {
                    a.getComponent(PlayerComponent.class).setAirborne(true);
                }
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.ENEMY) {
            @Override
            protected void onCollision(Entity a, Entity b) {

                if (a.hasComponent(PlayerComponent.class) && b.hasComponent(EnemyComponent.class)) {
                    a.getComponent(PlayerComponent.class).inflictDamage(b.getComponent(EnemyComponent.class).getDamage());

                    if (aboveMiddleCollision(a, b) && !sideCollision(a, b)) {  // Can only jump if standing above and on block
                        a.getComponent(PlayerComponent.class).landed();
                    }
                }
            }
        });

        // ------ENEMY------ //

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.ENEMY, EntityType.ENEMY) {
            @Override
            protected void onCollisionBegin(Entity a, Entity b) {

                if (aboveMiddleCollision(a, b)) {  // Can only jump if standing above and on block
                    if (a.hasComponent(EnemyComponent.class)) {
                        a.getComponent(EnemyComponent.class).landed();
                    }
                }
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.ENEMY, EntityType.PLATFORM) {
            @Override
            protected void onCollisionBegin(Entity a, Entity b) {
                if (a.hasComponent(EnemyComponent.class))
                    a.getComponent(EnemyComponent.class).landed();

                // onGround check.
                // If the platform has X-Position of 0, then the platform is the ground.
                if (b.getX() == 0) {
                    if (a.hasComponent(EnemyComponent.class))
                        a.getComponent(EnemyComponent.class).setOnGround(true);
                } else {
                    if (a.hasComponent(EnemyComponent.class))
                        a.getComponent(EnemyComponent.class).setOnGround(false);
                }

                if (a.hasComponent(EnemyComponent.class))
                    a.getComponent(EnemyComponent.class).setAirborne(false);
            }

            @Override
            protected void onCollisionEnd(Entity a, Entity b) {
                if (a.hasComponent(EnemyComponent.class))
                    a.getComponent(EnemyComponent.class).setAirborne(true);
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.ENEMY, EntityType.BLOCK) {
            @Override
            protected void onCollisionBegin(Entity a, Entity b) {
                if (a.hasComponent(EnemyComponent.class)) {
                    if (aboveMiddleCollision(a, b) && !sideCollision(a, b)) {  // Can only jump if standing above and on block
                        a.getComponent(EnemyComponent.class).landed();
                    }
                    a.getComponent(EnemyComponent.class).setAirborne(false);
                }
            }

            @Override
            protected void onCollision(Entity a, Entity b) {
                Block block = b.getObject("this");

                if (a.hasComponent(EnemyComponent.class)) {
                    block.inflictDamage(a.getComponent(EnemyComponent.class).getBlockDamage());
                }
            }

            @Override
            protected void onCollisionEnd(Entity a, Entity b) {
                if (a.hasComponent(EnemyComponent.class)) {
                    a.getComponent(EnemyComponent.class).setAirborne(true);
                }
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.ENEMY, EntityType.PROJECTILE) {
            @Override
            protected void onCollisionBegin(Entity a, Entity b) {
                // Remove projectile's velocity so Enemies don't get pushed.
                if (b.hasComponent(PhysicsComponent.class)) {
                    b.getComponent(PhysicsComponent.class).setVelocityY(0);
                    b.getComponent(PhysicsComponent.class).setVelocityX(0);
                }

                if (a.hasComponent(EnemyComponent.class)) {
                    a.getComponent(EnemyComponent.class).inflictDamage(player.getActiveWeapon().getDamage());
                }
                b.removeFromWorld();
            }
        });
    }

    private boolean sideCollision(Entity a, Entity b) {
        return !(EntityPos.getRightSideX(a) > EntityPos.getLeftSideX(b) && EntityPos.getLeftSideX(a) < EntityPos.getRightSideX(b));
    }

    private boolean aboveMiddleCollision(Entity a, Entity b) {
        return EntityPos.getBottomY(a) < EntityPos.getMiddleY(b);
    }
}
