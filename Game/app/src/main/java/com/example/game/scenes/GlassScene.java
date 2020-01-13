package com.example.game.scenes;

// importing the required packages

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

import com.example.game.gamecomponents.Builder;
import com.example.game.gamecomponents.characters.monsters.BeeStrafingMonster;
import com.example.game.design.Background;
import com.example.game.design.Button;
import com.example.game.gamecomponents.Constants;
import com.example.game.gamecomponents.characters.player.Player;
import com.example.game.gamecomponents.characters.monsters.SlimeMeleeMonster;

import java.util.ArrayList;

// implementing the game3 class: a random number of monsters move-about/show-up on the screen and
// the player has to guess the number of monsters.
public class GlassScene implements Scene {

    private final Background background;
    private final Player player;
    private final Point playerPoint;
    private final Button quitButton;
    static private int score;
    private final Button[] buttons;
    private final Button enter;
    private final Button quitButton2;
    private final Button saveButton;
    private final Button erase;
    private boolean gameOver;
    private final SceneManager manager;
    private String userInput;
    private int guess;
    private int xp;
    private int counter;
    private final ArrayList<BeeStrafingMonster> monsters;

    GlassScene(Context context, SceneManager manager, Background background) {
        gameOver = false;
        saveButton = new Button(300, 500, 500, 100, "Save Score?");
        quitButton2 = new Button(300, 700, 500, 100, "Quit");
        guess = 0;
        counter = 0;
        player = new Player(context, SceneManager.getCostume());
        this.manager = manager;
        this.background = background;
        xp = 0;
        userInput = "";

        //      Creating the required buttons
        enter = new Button(400, 1700, 300, 100, "Enter");
        erase = new Button(400, 1900, 300, 100, "Erase");
        quitButton = new Button(850, 50, 100, 100, "X");

        //      creating the input keyboard using a Builder method
        this.buttons = Builder.buildNumericKB();

        // maybe not needed ?
        playerPoint = new Point(Constants.displaySize.x / 2, Constants.displaySize.y);

        //      Generating a random number of monsters using Builder
        monsters = Builder.buildBee(context);
        //      Creating an empty list of slime monsters because it is required to update the Bee
        // monsters'
        //      movement.
    }

    @Override
    // update the background the player input
    public void update() {
        ArrayList<SlimeMeleeMonster> emptyList = new ArrayList<>();
        background.update();
        player.update(playerPoint);
        for (BeeStrafingMonster m : monsters) {
            m.update(player, emptyList);
        }
    }

    @Override
    // draw the various entities on the 'Canvas'
    public void draw(Canvas canvas) {
        for (BeeStrafingMonster m : monsters) {
            m.draw(canvas);
        }
        background.draw(canvas);
        for (int i = 0; i < 10; i++) {
            buttons[i].draw(canvas);
        }
        enter.draw(canvas);
        for (BeeStrafingMonster m : monsters) {
            m.draw(canvas);
        }
        quitButton.draw(canvas);
        erase.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        canvas.drawText("SCORE: " + score, 30, 100, paint);
        canvas.drawText("input: " + userInput, 30, 1400, paint);
        if(gameOver){
            saveButton.draw(canvas);
            quitButton2.draw(canvas);
        }
    }

    @Override
    public void terminate() {
        SceneManager.activeScene = 1;
    }

    @Override
    public void receiveTouch(MotionEvent event) {
        if (quitButton.isClicked((int) event.getX(), (int) event.getY())) {
            gameOver = true;
        }
        for (Button button : buttons) {
            if (button.isClicked((int) event.getX(), (int) event.getY())) {
                if (counter % 2 == 0) {
                    userInput += button.getName();
                }
                counter += 1;
            }
        }
        if (enter.isClicked((int) event.getX(), (int) event.getY())) {
            try {
                guess = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
            }
            if (guess == monsters.size()) {
                xp = 200;
                score += guess;
            } else {
                xp = 0;
            }

            SceneManager.nextScene = 4;
            SceneManager.activeScene = 9;
            //            this.terminate();
            manager.resetScenes();
        }
        if (erase.isClicked((int) event.getX(), (int) event.getY())) {
            userInput = "";
        }
        if(gameOver){
            if (quitButton2.isClicked((int) event.getX(), (int) event.getY())) {
                xp = 0;
                SceneManager.nextScene = 1;
                SceneManager.activeScene = 9;
            }
            if (saveButton.isClicked((int) event.getX(), (int) event.getY())) {
                SceneManager.nextScene = 1;
                SceneManager.activeScene = 9;
                manager.resetScenes();
            }
        }
    }

    void setCostume(String color) {
        player.setCostume(color);
    }

    int getXp() {
        return xp;
    }

    int getScore(){return score;}
}
