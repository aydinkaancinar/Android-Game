package com.example.game.gamecomponents.strategies;

import com.example.game.gamecomponents.characters.Character;
import com.example.game.gamecomponents.characters.monsters.SlimeMeleeMonster;
import com.example.game.gamecomponents.characters.player.Player;

import java.util.ArrayList;

public class ChaseStrategy implements Strategy {
    private final float[] normal = new float[2];
    private float magnitude;
    private int speed;

    @Override
    public void move(Player player, Character character,
                     ArrayList<SlimeMeleeMonster> collidableCharacters) {
        double oldLeft = character.getRectangle().left;
        speed = character.speed;
        //makes the character handle the current force on it
        character.handleForce();
        // Find the vector from the character to the player
        normal[0] = player.getRectangle().centerX() - character.getRectangle().centerX();
        normal[1] = player.getRectangle().centerY() - character.getRectangle().centerY();
        magnitude = (float) Math.sqrt(normal[0] * normal[0] + normal[1] * normal[1]);
        //Deals with monster collisions
        collide(character, collidableCharacters);

        //Checks if the monster is within attacking distance
        if (magnitude <= (float) (character.getRectangle().width() / 2
                + player.getRectangle().width() / 2)) {
            player.healthBar.takeDamage(character.damage);
        }
        if (magnitude < character.speed) {
            character.getAnimationManager().playAnimation(0);
            return;
        }
        //Moves the character towards the player
        chasePlayer(character);

        //Plays the appropriate animation
        playMovementAnimation(oldLeft, character);

    }


    private void chasePlayer(Character character) {
        //moves the character towards the player
        int[] direction = findDirection();
        int moveX = direction[0];
        int moveY = direction[1];
        character.changeRectangle(character.getRectangle().centerX() +
                        moveX - character.getRectangle().width() / 2,
                (character.getRectangle().centerY() + moveY)
                        - character.getRectangle().height() / 2,
                (character.getRectangle().centerX() + moveX) +
                        character.getRectangle().width() / 2,
                (character.getRectangle().centerY() + moveY) +
                        character.getRectangle().height() / 2);
        character.healthBar.move();
    }

    private void playMovementAnimation(double oldLeft, Character character) {
        int state = 0; // 0 blueidle, 1 walking right , 2 walking left, 3 death right
        if (character.getRectangle().left - oldLeft > 0) {
            state = 1;
            character.deathDirection = 3;

        } else if (character.getRectangle().left - oldLeft < 0) {
            character.deathDirection = 4;
            state = 2;

        }

        character.getAnimationManager().playAnimation(state);
        character.getAnimationManager().update();
    }

    private int[] findDirection() {
        //Scales the normal vector by the speed to find the direction vector of the right magnitude
        float[] un = new float[2];
        un[0] = normal[0] / magnitude;
        un[1] = normal[1] / magnitude;
        int moveX = (int) (un[0] * speed);
        int moveY = (int) (un[1] * speed);
        return new int[]{moveX, moveY};
    }

    private void collide(Character character, ArrayList<SlimeMeleeMonster> collidables) {
        //Deals with all the character collisions
        for (SlimeMeleeMonster m : collidables) {
            float[] normal = new float[2];
            normal[0] = m.getRectangle().centerX() - character.getRectangle().centerX();
            normal[1] = m.getRectangle().centerY() - character.getRectangle().centerY();
            float magnitude = (float) Math.sqrt(normal[0] * normal[0] + normal[1] * normal[1]);

            if (magnitude <= (float) (character.getRectangle().width() / 2
                    + m.getRectangle().width() / 2)) {
                float[] un = new float[2];
                un[0] = normal[0] / magnitude;
                un[1] = normal[1] / magnitude;
                int moveX = (int) (un[0] * speed);
                int moveY = (int) (un[1] * speed);
                int[] direction = new int[]{moveX * 5, moveY * 5};
                m.applyForce(direction);
                m.healthBar.takeDamage(character.damage * 20);
            }
        }
    }
}
