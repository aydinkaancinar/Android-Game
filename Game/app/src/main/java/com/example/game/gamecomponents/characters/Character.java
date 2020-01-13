package com.example.game.gamecomponents.characters;


import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.game.design.AnimationManager;


public class Character implements GameObject {
    private Rect rectangle;
    private AnimationManager animationManager;
    public int speed;
    protected int max_health = 100;
    public HealthBar healthBar;
    public int damage;
    protected int[] directionOfForce;
    public int counter;
    public int deathDirection = 3; //3 right, 4 left

    @Override
    public void draw(Canvas canvas) {
        animationManager.draw(canvas, rectangle);
        healthBar.draw(canvas);
    }

    public void resetHealth() {
        healthBar.resetHealth();
    }

    @Override
    public void update() {
        animationManager.update();
    }

    public Rect getRectangle() { //Not sure why it is marked as not used. This is used.
        return rectangle;
    }

    protected void setRectangle(Rect rect) {
        this.rectangle = rect;
    }

    protected void setAnimationManager(AnimationManager manager) {
        this.animationManager = manager;
    }

    public AnimationManager getAnimationManager() {
        return animationManager;
    }

    protected void setHealthBar(HealthBar healthBar) {
        this.healthBar = healthBar;
    }

    public void changeRectangle(int left, int top, int right, int bottom) {
        rectangle.set(left, top, right, bottom);

    }

    public void applyForce(int[] direction) {
        this.directionOfForce = direction;
    }

    public void handleForce() {
    }

}
