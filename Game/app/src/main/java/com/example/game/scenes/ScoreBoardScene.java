package com.example.game.scenes;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.game.design.Button;
import com.example.game.design.Background;

public class ScoreBoardScene implements Scene {
    private final Background background;
    //to switch the display of scores for each game
    private final Button quitButton, game1, game2, game3;
    private final SceneManager manager;
    //stores the game number
    private int gameName;

    ScoreBoardScene(SceneManager manager, Background background) {
        this.manager = manager;
        this.background = background;
        quitButton = new Button(850, 50, 100, 100, "X");
        gameName = 0;
        game1 = new Button(100, 500, 293, 150, "G 1");
        game2 = new Button(393, 500, 293, 150, "G 2");
        game3 = new Button(686, 500, 293, 150, "G 3");
    }

    @Override
    public void update() {
        background.update();
    }

    @Override
    public void draw(Canvas canvas) {
        //draws the components of the scoreboard
        background.draw(canvas);
        quitButton.draw(canvas);
        game1.draw(canvas);
        game2.draw(canvas);
        game3.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(150);
        canvas.drawText("High Scores", 130, 400, paint);
        paint.setTextSize(80);
        canvas.drawText("GAME " + (1 + gameName) + " High Scores:", 70, 800, paint);
        if (SceneManager.highscoreScores[gameName][0] != 0) {
            canvas.drawText("#1  " + SceneManager.highscore[gameName][0], 70, 1000, paint);
            canvas.drawText("" + SceneManager.highscoreScores[gameName][0], 70, 1100, paint);
        }
        if (SceneManager.highscoreScores[gameName][1] != 0) {
            canvas.drawText("#2  " + SceneManager.highscore[gameName][1], 70, 1300, paint);
            canvas.drawText("" + SceneManager.highscoreScores[gameName][1], 70, 1400, paint);
        }
        if (SceneManager.highscoreScores[gameName][2] != 0) {
            canvas.drawText("#3  " + SceneManager.highscore[gameName][2], 70, 1600, paint);
            canvas.drawText("" + SceneManager.highscoreScores[gameName][2], 70, 1700, paint);
        }
    }

    @Override
    public void terminate() {
        SceneManager.activeScene = 1;
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        if (quitButton.isClicked((int) event.getX(), (int) event.getY())) {
            SceneManager.activeScene = 1;
            manager.resetScenes();
        }
        if (game1.isClicked((int) event.getX(), (int) event.getY())) {
            gameName = 0;
        }
        if (game2.isClicked((int) event.getX(), (int) event.getY())) {
            gameName = 1;
        }
        if (game3.isClicked((int) event.getX(), (int) event.getY())) {
            gameName = 2;
        }
    }
}