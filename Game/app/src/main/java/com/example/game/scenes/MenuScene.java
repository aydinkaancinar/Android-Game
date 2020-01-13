package com.example.game.scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;


import com.example.game.design.Background;
import com.example.game.design.Button;


public class MenuScene implements Scene {
    private final Background background;
    private final Button gameButton, game2Button, game3Button, storeButton, changeUser, scoreButton;
    private int xp;
    private final int xp1;
    private final int score;
    private final int xp2;
    private final int xp3;

    MenuScene(SceneManager manager, Background background) {
        this.background = background;
        gameButton = new Button(100, 800, 880, 150, "Survival");
        game2Button = new Button(100, 1000, 880, 150, "Endless Maze");
        game3Button = new Button(100, 1200, 880, 150, "Guessing Game");
        storeButton = new Button(100, 1400, 880, 150, "Customization");
        scoreButton = new Button(100, 1600, 880, 150, "Scoreboard");
        changeUser = new Button(100, 1800, 880, 150, "Change User");
        xp = manager.getXp("");
        score = manager.getXp("score");
        xp1 = manager.getXp("1");
        xp2 = manager.getXp("2");
        xp3 = manager.getXp("3");
    }

    @Override
    public void update() {
        background.update();
    }

    @Override
    public void draw(Canvas canvas) {
        background.draw(canvas);
        gameButton.draw(canvas);
        game2Button.draw(canvas);
        game3Button.draw(canvas);
        storeButton.draw(canvas);
        scoreButton.draw(canvas);
        changeUser.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(60);
        canvas.drawText("Total XP: " + xp, 30, 80, paint);
        canvas.drawText("Score: " + score, 30, 140, paint);
        paint.setTextSize(60);
        canvas.drawText("USER: " + SceneManager.getUserName(), 30, 200, paint);
        paint.setTextSize(200);
        canvas.drawText("BEST", 300, 400, paint);
        canvas.drawText("GAME", 250, 550, paint);
        canvas.drawText("EVER!", 280, 700, paint);
        paint.setTextSize(50);
        canvas.drawText("Game1 XP: " + xp1, 600, 60, paint);
        canvas.drawText("Game2 XP: " + xp2, 600, 100, paint);
        canvas.drawText("Game3 XP: " + xp3, 600, 140, paint);
    }

    @Override
    public void terminate() {
        SceneManager.activeScene = 0;
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        if (gameButton.isClicked((int) event.getX(), (int) event.getY())) {
            SceneManager.nextScene = 2;
            SceneManager.activeScene = 9;
        } else if (game2Button.isClicked((int) event.getX(), (int) event.getY())) {
            SceneManager.nextScene = 3;
            SceneManager.activeScene = 9;
        } else if (game3Button.isClicked((int) event.getX(), (int) event.getY())) {
            SceneManager.nextScene = 4;
            SceneManager.activeScene = 9;
        } else if (storeButton.isClicked((int) event.getX(), (int) event.getY())) {
            SceneManager.nextScene = 5;
            SceneManager.activeScene = 9;
        } else if (changeUser.isClicked((int) event.getX(), (int) event.getY())) {
            SceneManager.nextScene = 0;
            SceneManager.activeScene = 9;
            SceneManager.changeUser();
        } else if (scoreButton.isClicked((int) event.getX(), (int) event.getY())) {
            SceneManager.nextScene = 8;
            SceneManager.activeScene = 9;
        }
    }

    void setXp(int points) {
        xp = points;
    }
}
