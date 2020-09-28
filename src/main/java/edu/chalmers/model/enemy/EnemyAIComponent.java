package edu.chalmers.model.enemy;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.RaycastResult;
import javafx.geometry.Point2D;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getPhysicsWorld;

/**
 * EnemyAIComponent class. Contains and gives basic Enemy AI to an Entity.
 */
public class EnemyAIComponent extends Component {

    enum Direction {LEFT, RIGHT}

    private EnemyComponent thisEnemy;
    private Entity player;
    private Direction moveDirection;
    private RaycastResult horizontalRaycast;
    private RaycastResult bottomRaycast;

    private int horizontalRaycastLength = 30;
    private int bottomRaycastLength = 50;
    private boolean canJump = true;
    private boolean playerReached = false;

    public EnemyAIComponent(EnemyComponent thisEnemy, Entity player) {
        this.player = player;
        this.thisEnemy = thisEnemy;
    }

    @Override
    public void onUpdate(double tpf) {
        setMoveDirection();
        setHorizontalRaycastDirection();
        setBottomRaycastPosition();
        setPlayerReached();

        moveTowardsPlayer();
        doJump();

        checkOtherEnemy();

        // TIMER TEST
        /*
        getGameTimer().runOnceAfter(() -> {
            // ...
        }, Duration.seconds(2));
         */
    }

    /**
     * Method sets moveDirection based on Player position.
     */
    private void setMoveDirection() {
        if(isPlayerToLeft()) {
            moveDirection = Direction.LEFT;
        }
        else if(isPlayerToRight()) {
            moveDirection = Direction.RIGHT;
        }
    }

    /**
     * Method sets correct direction of horizontalRaycast based on moveDirection.
     */
    private void setHorizontalRaycastDirection() {
        // Point raycast to the left
        if(moveDirection == Direction.LEFT) {
            Point2D raycastStart = new Point2D(thisEnemy.getX(), thisEnemy.getY());
            Point2D raycastEnd = new Point2D(thisEnemy.getX() - horizontalRaycastLength, thisEnemy.getY());

            horizontalRaycast = getPhysicsWorld().raycast(raycastStart, raycastEnd);
        }

        // Point raycast to the right
        else if(moveDirection == Direction.RIGHT) {
            Point2D raycastStart = new Point2D(thisEnemy.getRightX(), thisEnemy.getY());
            Point2D raycastEnd = new Point2D(thisEnemy.getRightX() + horizontalRaycastLength, thisEnemy.getY());

            horizontalRaycast = getPhysicsWorld().raycast(raycastStart, raycastEnd);
        }
    }

    /**
     * Method sets correct position of bottomRaycast based on moveDirection.
     */
    private void setBottomRaycastPosition() {
        // Point raycast to the bottom-left
        if(moveDirection == Direction.LEFT) {
            Point2D raycastStart = new Point2D(thisEnemy.getX(), thisEnemy.getBottomY());
            Point2D raycastEnd = new Point2D(thisEnemy.getX(), thisEnemy.getBottomY() + bottomRaycastLength);

            bottomRaycast = getPhysicsWorld().raycast(raycastStart, raycastEnd);
        }

        // Point raycast to the bottom-right
        else if(moveDirection == Direction.RIGHT) {
            Point2D raycastStart = new Point2D(thisEnemy.getRightX(), thisEnemy.getBottomY());
            Point2D raycastEnd = new Point2D(thisEnemy.getRightX(), thisEnemy.getBottomY() + bottomRaycastLength);

            bottomRaycast = getPhysicsWorld().raycast(raycastStart, raycastEnd);
        }
    }

    /**
     * Method sets playerReached variable based on horizontalRaycast.
     */
    private void setPlayerReached() {
        if(horizontalRaycast == null) {
            return;
        }

        if(horizontalRaycastHitPlayer()) {
            playerReached = true;
        } else {
            playerReached = false;
        }
    }

    /**
     * Method moves Enemy towards the player.
     */
    private void moveTowardsPlayer() {
        // Is Enemy to the right of Player AND Player is not reached?
        if(isPlayerToLeft() && !playerReached) {
            thisEnemy.moveLeft();
        }
        // Is Enemy to the left of Player AND Player is not reached?
        else if(isPlayerToRight() && !playerReached) {
            thisEnemy.moveRight();
        }
    }

    /**
     * Jump method. Makes Enemy jump when needed.
     */
    private void doJump() {
        if(horizontalRaycast == null) {
            return;
        }

        // if *not* nothing is hit OR
        if(!horizontalRaycast.getEntity().equals(Optional.empty()) || bottomRaycast.getEntity().equals(Optional.empty())) {

            // If ray hit side OR ray *not* hit ground
            if((horizontalRaycastHitPlatformSide() && canJump) || (!bottomRaycastHitPlatform() && canJump)) {
                thisEnemy.jump();
                canJump = false;

                // Reset canJump after 2 seconds
                final Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        canJump = true;
                        timer.cancel();
                    }
                }, 2000);
            }
        }
    }

    private void checkOtherEnemy() {
        if(horizontalRaycast == null) {
            return;
        }

        if(horizontalRaycastHitEnemy()) {
            // Get Enemy entity
            Optional<Entity> optionalEntity = horizontalRaycast.getEntity();
            Entity entity = optionalEntity.get();
            boolean otherEnemyReachedPlayer = entity.getComponent(EnemyAIComponent.class).isPlayerReached();
            System.out.println(otherEnemyReachedPlayer);
        }
    }

    /**
     * Method checks if the Player is to the left of the Enemy entity.
     * @return True or false.
     */
    private boolean isPlayerToLeft() {
        return player.getRightX() - thisEnemy.getX() < 0;
    }

    /**
     * Method checks if the Player is to the right of the Enemy entity.
     * @return True or false.
     */
    private boolean isPlayerToRight() {
        return player.getX() - thisEnemy.getRightX() > 0;
    }

    /**
     * Method checks if horizontalRaycast hit the Player.
     * @return True or false.
     */
    private boolean horizontalRaycastHitPlayer() {
        return checkRaycastHit(horizontalRaycast, "PLAYER");
    }

    /**
     * Method checks if horizontalRaycast hit an Enemy.
     * @return True or false.
     */
    private boolean horizontalRaycastHitEnemy() {
        return checkRaycastHit(horizontalRaycast, "ENEMY");
    }

    /**
     * Method checks if horizontalRaycast hit a Platform side.
     * @return True or false.
     */
    private boolean horizontalRaycastHitPlatformSide() {
        return checkRaycastHit(horizontalRaycast, "PLATFORMSIDE");
    }

    /**
     * Method checks if bottomRaycast hit a Platform.
     * @return True or false.
     */
    private boolean bottomRaycastHitPlatform() {
        return checkRaycastHit(bottomRaycast, "PLATFORM");
    }

    /**
     * Method checks if a raycast hit something.
     * @param raycast Raycast to check.
     * @param hit The object to check if hit.
     * @return True or false.
     */
    private boolean checkRaycastHit(RaycastResult raycast, String hit) {
        String raycastHitString = raycast.getEntity().toString();
        return raycastHitString.contains(hit);
    }

    /**
     * Getter for playerReached variable.
     * @return playerReached variable.
     */
    public boolean isPlayerReached() {
        return playerReached;
    }
}
