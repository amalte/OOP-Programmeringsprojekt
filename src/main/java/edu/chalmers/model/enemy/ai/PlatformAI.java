package edu.chalmers.model.enemy.ai;

import com.almasb.fxgl.entity.Entity;
import edu.chalmers.model.EntityType;
import edu.chalmers.utilities.EntityPos;
import edu.chalmers.utilities.RaycastCalculations;

import java.util.*;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameWorld;

class PlatformAI {

    EnemyAIComponent AI;

    List<Entity> platforms = new ArrayList<>();                         // List witt all platforms.
    List<Double> platformYDeltaList = new ArrayList<>();                // List with all Y-pos delta between thisEnemy and platforms.
    HashMap<Double, Entity> platformAndYDeltaMap = new HashMap<>();     // HashMap with Y-Pos deltas and their corresponding platform.

    public PlatformAI(EnemyAIComponent enemyAIComponent) {
        this.AI = enemyAIComponent;
    }

    /**
     * Method finds all platforms in the level and adds them to the "platforms" list.
     */
    public void setPlatforms() {
        platforms = getGameWorld().getEntitiesByType(EntityType.PLATFORM);
        int worldPlatformIndex = 0;     // Index of the world platform in the platforms list.

        // Find list index of the world platform.
        for (Entity p : platforms) {
            // If platform is att X=0 (the world 'platform').
            if(p.getX() == 0) {
                worldPlatformIndex = platforms.indexOf(p);
                break;
            }
        }

        // Remove world from platform list.
        platforms.remove(platforms.get(worldPlatformIndex));
    }

    /**
     * Method finds the closest platform to the Enemy.
     * @return The closest platform.
     */
    public Entity getClosestPlatform() {
        // Resets lists and variables for new closest platform search.
        platformYDeltaList.clear();
        platformYDeltaList.clear();
        boolean twoPlatformsFound = false;

        for (Entity p : platforms) {

            // Skip platform if Enemy is standing on it
            if(getPlatformBelowEntity() != null) {
                if(getPlatformBelowEntity().equals(p)) {
                    continue;
                }
            }

            // Y-Position delta between current platform and Enemy. Add delta to list and to map along with the platform.
            Double yDelta = Double.valueOf(Math.abs(p.getY() - AI.getThisEnemy().getY()));
            platformYDeltaList.add(yDelta);
            platformAndYDeltaMap.put(yDelta, p);
        }

        //Collections.sort(platformYDeltaList);       // Sorts list from smallest to largest.
        double smallestYDelta = Collections.min(platformYDeltaList);
        Entity closestPlatform = platformAndYDeltaMap.get(smallestYDelta);

        if(Math.abs(smallestYDelta - platformYDeltaList.get(1)) < 5) {
            twoPlatformsFound = true;
        }

        if(twoPlatformsFound) {
            closestPlatform = getClosestPlatformOfTwo();
        }

        return closestPlatform;
    }

    /**
     * Method gets the closest platform if two platforms exist on roughly the same Y-Position.
     * @return The closest platform.
     */
    private Entity getClosestPlatformOfTwo() {
        for (int i = 0; i < platformYDeltaList.size(); i++) {
            for (int j = i+1; j < platformYDeltaList.size(); j++) {
                Double y1 = platformYDeltaList.get(i);
                Double y2 = platformYDeltaList.get(j);

                // If difference between 2 platforms Y-pos is less than 5
                if(Math.abs(y1 - y2) < 5) {
                    Entity platform1 = platformAndYDeltaMap.get(y1);
                    Entity platform2 = platformAndYDeltaMap.get(y2);

                    // Middle X-Positions for platforms and player
                    double platform1MiddleX = EntityPos.getMiddleX(platform1);
                    double platform2MiddleX = EntityPos.getMiddleX(platform2);
                    double playerMiddleX = EntityPos.getMiddleX(AI.getPlayer());

                    // X-Position delta between platforms and player.
                    Double xDelta_playerPlatform1 = Double.valueOf(Math.abs(platform1MiddleX - playerMiddleX));
                    Double xDelta_playerPlatform2 = Double.valueOf(Math.abs(platform2MiddleX - playerMiddleX));

                    // Return the platform with smallest X-Position delta (closest platform).
                    if(xDelta_playerPlatform1 < xDelta_playerPlatform2) {
                        return platformAndYDeltaMap.get(y1);
                    }
                    else {
                        return platformAndYDeltaMap.get(y2);
                    }
                }
            }
        }

        // None of the platforms were the closest (shouldn't reach this).
        return null;
    }

    /**
     * Method returns the Platform the Enemy is standing on.
     * @return Platform or null (if not standing on platform).
     */
    public Entity getPlatformBelowEntity() {
        if(AI.getRaycastAI().getLeftDownwardRaycast() == null ||
                AI.getRaycastAI().getRightDownwardRaycast() == null) {
            return null;
        }

        // Platform hit detection
        boolean leftHit = RaycastCalculations.checkRaycastHit(AI.getRaycastAI().getLeftDownwardRaycast(), EntityType.PLATFORM);
        boolean rightHit = RaycastCalculations.checkRaycastHit(AI.getRaycastAI().getRightDownwardRaycast(), EntityType.PLATFORM);

        // leftDownwardRaycast check.
        if(leftHit) {
            // Get platform entity
            Optional<Entity> optionalEntity = AI.getRaycastAI().getLeftDownwardRaycast().getEntity();
            Entity platform = optionalEntity.get();

            if(platforms.contains(platform)) {
                return platform;
            }
        }

        // rightDownwardRaycast check.
        else if(rightHit) {
            // Get platform entity
            Optional<Entity> optionalEntity = AI.getRaycastAI().getRightDownwardRaycast().getEntity();
            Entity platform = optionalEntity.get();

            if(platforms.contains(platform)) {
                return platform;
            }
        }

        // No platform (should not reach this point).
        return null;
    }

    /**
     * Checks if the platform below Enemy is the given targetEntity.
     * @param targetPlatform targetEntity.
     * @return True or False.
     */
    public boolean checkPlatformBelow(Entity targetPlatform) {
        // If raycasts are null or no platform below Enemy.
        if (AI.getRaycastAI().getLeftDownwardRaycast() == null ||
                AI.getRaycastAI().getRightDownwardRaycast() == null ||
                getPlatformBelowEntity() == null) {
            return false;
        }

        // Checks if the platform below Enemy is the given targetEntity.
        if(getPlatformBelowEntity().equals(targetPlatform)) {
            return true;
        } else {
            return false;
        }
    }
}
