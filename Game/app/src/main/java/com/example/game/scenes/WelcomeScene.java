package com.example.game.scenes;


import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.game.design.Background;
import com.example.game.design.Button;

public class WelcomeScene implements Scene {
    private final Background background;
    private final Button loginButton, signButton;
    private final SceneManager manager;

    WelcomeScene(SceneManager manager, Background background) {
        this.manager = manager;
        this.background = background;
        signButton = new Button(100, 1200, 880, 150, "New User");
        loginButton = new Button(100, 1400, 880, 150, "Login");

    }

    @Override
    public void update() {
        background.update();
    }

    @Override
    public void draw(Canvas canvas) {
        background.draw(canvas);
        loginButton.draw(canvas);
        signButton.draw(canvas);
    }

    @Override
    public void terminate() {
        SceneManager.activeScene = 1;
    }

    @Override
    public void receiveTouch(MotionEvent event) {

        if (loginButton.isClicked((int) event.getX(), (int) event.getY())) {
            SceneManager.nextScene = 0;
            SceneManager.activeScene = 9;
            manager.resetScenes();

        }
        if (signButton.isClicked((int) event.getX(), (int) event.getY())) {
            SceneManager.nextScene = 7;
            SceneManager.activeScene = 9;
            manager.resetScenes();
        }
    }
}
