package com.example.game.gamecomponents.mazecreator;

import android.graphics.Rect;

import com.example.game.gamecomponents.characters.player.Player;

import java.util.ArrayList;

public class CollisionChecker {

    private final ArrayList<Rect> walls;
    private Rect finishLine;

    public CollisionChecker() {
        walls = new ArrayList<>();

    }

    public void resetCollisionChecker() {
        this.walls.clear();
    }

    public void setCollisionChecker(ArrayList<Rect> walls, Rect finishLine) {
        this.walls.addAll(walls);
        this.finishLine = finishLine;

    }

    public boolean checkCollisions(Player player) {
        if (walls.size() > 0) {
            return checkPlayerCollision(player);
        }
        return false;
    }

    /**
     * Uses the Rect.intersect method to check if the player collides with a wall. The collisionBox
     * is used for the collision detection of the player, and it represents a slightly smaller Rect
     * than the player. This is so that when the edge of the player Rect hits a wall, it isn't
     * recognized as a collision (since the corners of the playerRect are invisible to the user, and
     * only collisions with the visual body of the character should be checked).
     *
     * @param player the Rect representing the controllable player.
     * @return a boolean value representing whether the player has collided with a wall or not.
     */
    private boolean checkPlayerCollision(Player player) {
        Rect playerRect = player.getRectangle();
        Rect collisionBox = new Rect(playerRect.left + 30, playerRect.top + 30, playerRect.right - 30,
                playerRect.bottom - 30);

        for (int i = 0; i < walls.size(); i++) {
            if (collisionBox.intersect(walls.get(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean checkFinished(Player player) {

        Rect playerRect = player.getRectangle();
        Rect collisionBox = new Rect(playerRect.left + 30, playerRect.top + 30, playerRect.right - 30,
                playerRect.bottom - 30);
        if (walls.size() > 0) {
            return (collisionBox.intersect(finishLine));
        }
        return false;
    }
}
