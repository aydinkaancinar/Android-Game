package com.example.game.gamecomponents.characters.monsters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;

import com.example.game.gamecomponents.characters.HealthBar;
import com.example.game.R;
import com.example.game.design.Animation;
import com.example.game.design.AnimationManager;

public class BeeStrafingMonster extends StrafingMonster {
    public BeeStrafingMonster(Context context, int x, int y) {
        setRectangle(new Rect(x - 50, y - 50,
                x + 50, y + 50));
        speed = 20;
        damage = 5;
        int aboveDistance = (getRectangle().width() / 2 + 5);
        healthBar = new HealthBar(max_health, this, aboveDistance, Color.RED, 100);
        setAnimations(context);
    }

    private void setAnimations(Context context) {
        Bitmap idleImg = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.bee);
        Bitmap walk1 = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.bee_fly);
        Bitmap deathImg = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.bee_dead);

        Animation idle = new Animation(new Bitmap[]{idleImg}, 2);
        Animation walkLeft = new Animation(new Bitmap[]{idleImg, walk1}, 0.1f);
        Animation deathLeft = new Animation(new Bitmap[]{deathImg}, 1);
        Matrix m = new Matrix();
        m.preScale(-1, 1);
        idleImg = Bitmap.createBitmap(idleImg, 0, 0, idleImg.getWidth(), idleImg.getHeight(), m,
                false);
        walk1 = Bitmap.createBitmap(walk1, 0, 0, walk1.getWidth(), walk1.getHeight(), m,
                false);
        deathImg = Bitmap.createBitmap(deathImg, 0, 0, deathImg.getWidth(), deathImg.getHeight(), m,
                false);
        Animation walkRight = new Animation(new Bitmap[]{idleImg, walk1}, 0.1f);
        Animation deathRight = new Animation(new Bitmap[]{deathImg}, 1);

        setAnimationManager(new AnimationManager((new Animation[]{idle, walkRight, walkLeft,
                deathRight, deathLeft})));
    }
}
