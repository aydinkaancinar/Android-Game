package com.example.game.design;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Paint;

public class Animation {
    private final Bitmap[] frames;
    private int frameIndex;
    private boolean isPlaying = false;
    private final float frameTime;
    private long lastFrame;

    public Animation(Bitmap[] frames, float animationTime) {
        this.frames = frames;
        frameIndex = 0;
        frameTime = animationTime / frames.length;
        lastFrame = System.currentTimeMillis();
    }


    boolean isPlaying() {
        return isPlaying;
    }

    void play() {
        frameIndex = 0;
        isPlaying = true;
        lastFrame = System.currentTimeMillis();
    }

    void stop() {
        isPlaying = false;
    }

    void update() {
        if (!isPlaying) {
            return;
        }
        if (System.currentTimeMillis() - lastFrame > frameTime * 1000) {
            frameIndex++;
            frameIndex = frameIndex >= frames.length ? 0 : frameIndex;
            lastFrame = System.currentTimeMillis();
        }
    }

    void draw(Canvas canvas, Rect destination) {
        if (!isPlaying) {
            return;
        }
        canvas.drawBitmap(frames[frameIndex], null, destination, new Paint());
    }

}

