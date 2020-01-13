package com.example.game.scenes;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.game.design.Background;

public class Loading implements Scene {
    private final Background background;
    private String dots;
    private int counter;

    Loading(Background background) {
        this.background = background;
        this.counter = 0;
        dots = "";
    }

    public void update() {
        background.update();
        counter++;
        System.out.println(counter);
        if(counter%7 == 0) dots += ".";
        if (counter > 30) {
            counter = 0;
            dots = "";
            SceneManager.activeScene = SceneManager.nextScene;
        }
    }

    public void draw(Canvas canvas) {
        background.draw(canvas);
        Paint paint = new Paint();
        paint.setTextSize(150);
        canvas.drawText("LOADING" + dots, 100, 1000, paint);
    }

    public void terminate() {
        SceneManager.activeScene = SceneManager.nextScene;
    }

    public void receiveTouch(MotionEvent event) {
    }


}
