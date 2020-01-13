package com.example.game.gamecomponents.characters.player;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;

import com.example.game.R;
import com.example.game.gamecomponents.characters.Character;
import com.example.game.gamecomponents.characters.HealthBar;
import com.example.game.gamecomponents.Constants;
import com.example.game.design.Animation;
import com.example.game.design.AnimationManager;

public class Player extends Character {
    private Bitmap idleImg, walk1, walk2;
    private String col;
    private final Context context;

    public Player(Context context, String col) {
        this.context = context;
        //Set all attributes
        speed = 15;
        max_health = 100;
        this.col = col;
        setAnimations(context);

        setRectangle(new Rect(Constants.displaySize.x / 2 - 50,
                Constants.displaySize.y - 50, Constants.displaySize.x / 2 + 50,
                Constants.displaySize.y + 50));
        int aboveDistance = getRectangle().width() / 2 + 5;
        setHealthBar(new HealthBar(max_health, this, aboveDistance, Color.GREEN, 150));
    }

    /* used for mouse motion in the maze game. Using this Rect means that the player will only
        move if the mouse is withing 250 pixels of the player. This fixes a bug where the
        player starts moving immediately after the next maze level, which is due to the
        MOUSE_DOWN event triggering at the finish line.
     */
    public Rect getMotionEventRect() {
        Rect playerRect = getRectangle();
        return new Rect(playerRect.left - 250, playerRect.top - 250,
                playerRect.right + 250, playerRect.bottom + 250);
    }

    public void moveHealthBar() {
        healthBar.move();
    }

    public void update(Point point) {
        //This is how a player moves
        double oldLeft = getRectangle().left;
        float[] normal = new float[2];
        //Finds the vector between the player and the point that was pressed
        normal[0] = point.x - getRectangle().centerX();
        normal[1] = point.y - getRectangle().centerY();
        //Determines the size of the vector.
        float magnitude = (float) Math.sqrt(normal[0] * normal[0] + normal[1] * normal[1]);
        //Check if the player does not move
        if (magnitude < speed) {
            getAnimationManager().playAnimation(0);
            return;
        }
        //Scale the movement vector to represent the speed
        float[] un = new float[2];
        un[0] = normal[0] / magnitude;
        un[1] = normal[1] / magnitude;
        int move_x = (int) (un[0] * speed);
        int move_y = (int) (un[1] * speed);
        //Move the player
        changeRectangle((getRectangle().centerX() + move_x) - getRectangle().width() / 2,
                (getRectangle().centerY() + move_y) - getRectangle().height() / 2,
                (getRectangle().centerX() + move_x) + getRectangle().width() / 2,
                (getRectangle().centerY() + move_y) + getRectangle().height() / 2);
        healthBar.move();
        //Play the appropriate movement animation.
        int state = 0; // 0 blueidle, 1 walking , 2 walking left
        if (getRectangle().left - oldLeft > 0) {
            state = 1;
        } else if (getRectangle().left - oldLeft < 0) {
            state = 2;
        }

        getAnimationManager().playAnimation(state);
        getAnimationManager().update();

    }

    public void setCostume(String color) {
        this.col = color;
        getAnimationManager().update();
        setAnimations(context);
    }

    public int getHealth() {
        return healthBar.getCurrHealth();
    }


    private void setAnimations(Context context) {
        switch (this.col) {
            case "blue":
                idleImg = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.blueidle);
                walk1 = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.bluewalk1);
                walk2 = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.bluewalk2);
                break;

            case "green":
                idleImg = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.greenidle);
                walk1 = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.greenwalk1);
                walk2 = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.greenwalk2);
                break;

            case "pink":
                idleImg = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.pinkidle);
                walk1 = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.pinkwalk1);
                walk2 = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.pinkwalk2);
                break;
        }


        Animation idle = new Animation(new Bitmap[]{idleImg}, 2);
        Animation walkRight = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);
        Matrix m = new Matrix();
        m.preScale(-1, 1);
        walk1 = Bitmap.createBitmap(walk1, 0, 0, walk1.getWidth(), walk1.getHeight(), m,
                false);
        walk2 = Bitmap.createBitmap(walk2, 0, 0, walk2.getWidth(), walk2.getHeight(), m,
                false);
        Animation walkLeft = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);

        // All animations in Player
        setAnimationManager(new AnimationManager((new Animation[]{idle, walkRight, walkLeft})));
    }
}
