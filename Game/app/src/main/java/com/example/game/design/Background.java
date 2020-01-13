package com.example.game.design;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.game.gamecomponents.Constants;
import com.example.game.gamecomponents.characters.GameObject;
import com.example.game.R;


public class Background implements GameObject {
    private final Rect rectangle;
    private Animation idle;
    private AnimationManager animationManager;

    public Background(Context context, String type) {
        loadImg(context, type);
        this.rectangle = new Rect(0, 0, Constants.displaySize.x, Constants.displaySize.y);

    }

    @Override
    public void draw(Canvas canvas) {
        animationManager.draw(canvas, rectangle);

    }

    @Override
    public void update() {
        idle.play();
        animationManager.update();

    }

    private void loadImg(Context context, String type) {
        Bitmap idleImg = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.grass);
        switch (type) {
            case "grass":
                idleImg = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.grass);
                break;
            case "stone":
                idleImg = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.stone);

                break;
            case "login":
                idleImg = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.loginbackground);

                break;
            case "wood":
                idleImg = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.wood);
                break;
            case "store":
                idleImg = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.storebackground);
                break;
        }
        idle = new Animation(new Bitmap[]{idleImg}, 2);
        animationManager = new AnimationManager((new Animation[]{idle}));
    }

}
