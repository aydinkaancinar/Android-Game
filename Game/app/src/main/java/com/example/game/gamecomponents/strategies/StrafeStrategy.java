package com.example.game.gamecomponents.strategies;

import com.example.game.gamecomponents.characters.Character;
import com.example.game.gamecomponents.characters.monsters.SlimeMeleeMonster;
import com.example.game.gamecomponents.characters.player.Player;
import com.example.game.gamecomponents.Constants;

import java.util.ArrayList;

public class StrafeStrategy implements Strategy {
    private final float[] normal = new float[2];
    private int direction = 0; //0 left 1 right
    private int speed;

    @Override
    public void move(Player player, Character character,
                     ArrayList<SlimeMeleeMonster> collidableCharacters) {
        double oldLeft = character.getRectangle().left;
        speed = character.speed;
        //Deals with monster collsions
        collide(character, collidableCharacters);
        normal[0] = player.getRectangle().centerX() - character.getRectangle().centerX();
        normal[1] = player.getRectangle().centerY() - character.getRectangle().centerY();
        float magnitude = (float) Math.sqrt(normal[0] * normal[0] + normal[1] * normal[1]);
        //Checks if the monster is within attacking distance
        if (magnitude <= (float) (character.getRectangle().width() / 2
                + player.getRectangle().width() / 2)) {
            player.healthBar.takeDamage(character.damage);
        }
        //Moves the character towards the player
        strafe(character);

        //Plays the appropriate animation
        playMovementAnimation(oldLeft, character);

    }


    private void strafe(Character character) {
        int move_x;
        if (direction == 0) {
            move_x = -speed;
        }
        else {move_x = speed;}
        int move_y = 0;
        character.changeRectangle(character.getRectangle().centerX() +
                        move_x - character.getRectangle().width() / 2,
                (character.getRectangle().centerY() + move_y)
                        - character.getRectangle().height() / 2,
                (character.getRectangle().centerX() + move_x) +
                        character.getRectangle().width() / 2,
                (character.getRectangle().centerY() + move_y) +
                        character.getRectangle().height() / 2);
        character.healthBar.move();
        if (character.getRectangle().centerX() > Constants.displaySize.x) {
            direction = 0;
        }
        else if (character.getRectangle().centerX() < 0) {
            direction = 1;
        }
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


    private void collide(Character character, ArrayList<SlimeMeleeMonster> collidables) {
        //Deals with all collisions
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
                int move_x = (int) (un[0] * speed);
                int move_y = (int) (un[1] * speed);
                System.out.println(move_x);
                int[] direction = new int[]{move_x * 4, move_y * 4};
                m.applyForce(direction);
                m.healthBar.takeDamage(character.damage * 2);
            }
        }
    }
}


