package com.example.game.design;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.game.gamecomponents.characters.GameObject;

public class Button implements GameObject {

    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final Paint paint;
    private final Paint backPaint;
    private final String name;

    public Button(int xCo, int yCo, int w, int h, String n) {
        x = xCo;
        y = yCo;
        width = w;
        height = h;
        name = n;
        paint = new Paint();
        backPaint = new Paint();
        backPaint.setColor(Color.rgb(0, 0, 0));
        paint.setColor(Color.rgb(0, 100, 0));
    }


    public void draw(Canvas canvas) {
        paint.setStrokeWidth(3);
        backPaint.setColor(Color.rgb(0, 0, 0));
        canvas.drawRect(x - 6, y - 6, x + width + 6, y + height + 6, backPaint);
        canvas.drawRect(x, y, x + width, y + height, paint);
        backPaint.setTextSize(100);
        canvas.drawText(name, x + (width - 50 * name.length()) / 2, y +
                ((height + 80) / 2), backPaint);
    }

    @Override
    public void update() {
    }

    public boolean isClicked(int xCo, int yCo) {
        backPaint.setColor(Color.rgb(255, 255, 255));
        return (x < xCo && x + width > xCo && y < yCo && y + height > yCo);
    }

    public String getName() {
        return name;
    }
}
