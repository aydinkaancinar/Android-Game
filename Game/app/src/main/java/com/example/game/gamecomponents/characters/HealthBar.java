package com.example.game.gamecomponents.characters;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class HealthBar implements GameObject {
    private final int maxHealth;
    private int currHealth;
    private final Rect rectangle;
    private final Paint paint;
    private final int length;
    private final Character character;
    private int oldCharacterX;
    private int oldCharacterY;

    public HealthBar(int maxHealth, Character character, int aboveDistance, int color, int length) {
        this.rectangle = new Rect(character.getRectangle().centerX() - length / 2,
                character.getRectangle().centerY() - aboveDistance,
                character.getRectangle().centerX() + length / 2,
                character.getRectangle().centerY() - aboveDistance - 5);
        paint = new Paint();
        paint.setColor(color);
        currHealth = maxHealth;
        this.maxHealth = maxHealth;
        this.length = length;
        this.character = character;
        oldCharacterX = character.getRectangle().centerX();
        oldCharacterY = character.getRectangle().centerY();

    }

    void resetHealth() {
        this.currHealth = maxHealth;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(rectangle, paint);

    }

    @Override
    public void update() {
    }


    public void move() {
        int moveX = character.getRectangle().centerX() - oldCharacterX;
        int moveY = character.getRectangle().centerY() - oldCharacterY;
        oldCharacterX = character.getRectangle().centerX();
        oldCharacterY = character.getRectangle().centerY();
        rectangle.set((rectangle.centerX() + moveX) - rectangle.width() / 2,
                (rectangle.centerY() + moveY) - rectangle.height() / 2,
                (rectangle.centerX() + moveX) + rectangle.width() / 2,
                (rectangle.centerY() + moveY) + rectangle.height() / 2);
    }

    public void takeDamage(int dmg) {
        currHealth -= dmg;
        currHealth = Math.max(0, currHealth);
        float scale = ((float) currHealth / (float) maxHealth) * length;
        int change = (rectangle.right - (rectangle.left + (int) (scale))) / 2;
        rectangle.right = rectangle.right - change;
        rectangle.left = rectangle.left + change;


    }

    public int getCurrHealth() {
        return currHealth;
    }
}

