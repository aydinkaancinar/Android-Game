package com.example.game.gamecomponents.characters.monsters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;

import java.lang.*;

import com.example.game.gamecomponents.characters.HealthBar;
import com.example.game.R;
import com.example.game.design.Animation;
import com.example.game.design.AnimationManager;

public class SlimeMeleeMonster extends MeleeMonster {
    public SlimeMeleeMonster(Context context, int x, int y) {
        setRectangle(new Rect(x - 50, y - 50,
                x + 50, y + 50));
        speed = 12;
        damage = 1;
        setAnimations(context);
        int aboveDistance = (getRectangle().width() / 2 + 5);
        healthBar = new HealthBar(max_health, this, aboveDistance, Color.RED, 100);
        counter = 30;
    }

    public void handleForce() {
        if (directionOfForce == null) {
            return;
        }
        changeRectangle(getRectangle().centerX() +
                        directionOfForce[0] / 2 - getRectangle().width() / 2,
                (getRectangle().centerY() + directionOfForce[1] / 2)
                        - getRectangle().height() / 2,
                (getRectangle().centerX() + directionOfForce[0] / 2) +
                        getRectangle().width() / 2,
                (getRectangle().centerY() + directionOfForce[1] / 2) +
                        getRectangle().height() / 2);
        directionOfForce[0] = Math.max(0, Math.abs(directionOfForce[0]) - speed /8);
        directionOfForce[1] = Math.max(0, Math.abs(directionOfForce[1]) - speed /8);

    }

    private void setAnimations(Context context) {
        Bitmap idleImg = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.slime);
        Bitmap walk1 = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.slime_walk);
        Bitmap deathImg = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.slime_dead);

        Animation idle = new Animation(new Bitmap[]{idleImg}, 2);
        Animation walkLeft = new Animation(new Bitmap[]{idleImg, walk1}, 0.5f);
        Animation deathLeft = new Animation(new Bitmap[]{deathImg}, 1);
        Matrix m = new Matrix();
        m.preScale(-1, 1);
        idleImg = Bitmap.createBitmap(idleImg, 0, 0, idleImg.getWidth(), idleImg.getHeight(), m,
                false);
        walk1 = Bitmap.createBitmap(walk1, 0, 0, walk1.getWidth(), walk1.getHeight(), m,
                false);
        deathImg = Bitmap.createBitmap(deathImg, 0, 0, deathImg.getWidth(), deathImg.getHeight(), m,
                false);
        Animation walkRight = new Animation(new Bitmap[]{idleImg, walk1}, 0.5f);
        Animation deathRight = new Animation(new Bitmap[]{deathImg}, 1);

        // All animations in SlimeMeleeMonster
        setAnimationManager(new AnimationManager((new Animation[]{idle, walkRight, walkLeft,
                deathRight, deathLeft})));
    }
}
