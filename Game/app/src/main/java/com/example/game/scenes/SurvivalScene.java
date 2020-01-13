package com.example.game.scenes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

import com.example.game.gamecomponents.characters.monsters.BeeStrafingMonster;
import com.example.game.design.Background;
import com.example.game.design.Button;
import com.example.game.gamecomponents.Constants;
import com.example.game.gamecomponents.characters.player.Player;
import com.example.game.gamecomponents.characters.monsters.SlimeMeleeMonster;
import com.example.game.gamecomponents.characters.Character;


import java.util.ArrayList;

public class SurvivalScene implements Scene {
    private final Background background;
    private final Player player;
    private final Point playerPoint;
    private final Button quitButton;
    private final Button quitButton2;
    private final Button saveButton;
    private final ArrayList<Character> characters = new ArrayList<>();
    private int score = 0;
    private final SceneManager manager;
    private boolean gameOver;
    private int xp;
    private final Context context;
    private final ArrayList<Character> vanishingCharacters = new ArrayList<>();

    SurvivalScene(Context context, SceneManager manager, Background background) {
        gameOver = false;
        this.context = context;
        player = new Player(context, SceneManager.getCostume());
        player.setCostume(SceneManager.getCostume());
        this.manager = manager;
        xp = 0;
        createMonsters();
        this.background = background;
        playerPoint = new Point(Constants.displaySize.x / 2, Constants.displaySize.y);
        quitButton = new Button(850, 50, 100, 100, "X");
        saveButton = new Button(300, 500, 500, 100, "Save Score?");
        quitButton2 = new Button(300, 700, 500, 100, "Quit");
    }

    //Updates the scene.
    @Override
    public void update() {
        if(!gameOver) score++;
        if (player.getHealth() < 1) {
            xp = score;
            gameOver = true;

        }
        background.update();
        player.update(playerPoint);
        ArrayList<SlimeMeleeMonster> slimeMonsters = new ArrayList<>();
        handleMonsterDeaths();
        for (Character m : characters) {
            if (m instanceof SlimeMeleeMonster)
                slimeMonsters.add((SlimeMeleeMonster) m);
        }

        for (SlimeMeleeMonster m : slimeMonsters) {
            ArrayList<SlimeMeleeMonster> collidableCharacter = new ArrayList<>(slimeMonsters);
            collidableCharacter.remove(m);
            m.update(player, collidableCharacter);
        }
        for (Character m : characters) {
            if (m instanceof BeeStrafingMonster) {
                ((BeeStrafingMonster) m).update(player, slimeMonsters);
            }
        }

    }

    //Draws the scene.
    @Override
    public void draw(Canvas canvas) {
        background.draw(canvas);
        player.draw(canvas);
        for (Character m : characters) {
            m.draw(canvas);
        }
        ArrayList<Character> toRemove = new ArrayList<>();
        for (Character m : vanishingCharacters) {
            if (m.counter > 0) {
                m.draw(canvas);
                m.counter -= 1;
            } else {
                toRemove.add(m);
            }
        }
        for (Character m : toRemove) {
            vanishingCharacters.remove(m);
        }
        toRemove.clear();
        quitButton.draw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        canvas.drawText("SCORE: " + score, 30, 100, paint);
        if(gameOver){
            saveButton.draw(canvas);
            quitButton2.draw(canvas);
        }
    }

    //Terminates a scene.
    @Override
    public void terminate() {
        SceneManager.nextScene = 1;
        SceneManager.activeScene = 9;
    }

    //Handles touch events.
    @Override
    public void receiveTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                playerPoint.set((int) event.getX(), (int) event.getY());
        }
        if (quitButton.isClicked((int) event.getX(), (int) event.getY())) {
            SceneManager.nextScene = 1;
            SceneManager.activeScene = 9;
            xp = 0;
            manager.resetScenes();
        }
        if(gameOver){
            if (quitButton2.isClicked((int) event.getX(), (int) event.getY())) {
                xp = 0;
                SceneManager.nextScene = 1;
                SceneManager.activeScene = 9;
                player.resetHealth();
                manager.resetScenes();
            }
            if (saveButton.isClicked((int) event.getX(), (int) event.getY())) {
                SceneManager.nextScene = 1;
                SceneManager.activeScene = 9;
                player.resetHealth();
                manager.resetScenes();
            }
        }
    }

    //Returns the XP collected.
    int getXp() {
        return xp;
    }

    //Creates the monsters in the scene.
    private void createMonsters() {
        characters.add(new SlimeMeleeMonster(context, 100, 100));
        characters.add(new SlimeMeleeMonster(context, 400, 600));
        characters.add(new SlimeMeleeMonster(context, 100, 1000));
        characters.add(new SlimeMeleeMonster(context, 400, 80));
        characters.add(new BeeStrafingMonster(context, -100, 500));
        characters.add(new BeeStrafingMonster(context, Constants.displaySize.x + 100, 1000));
    }

    void setCostume(String color) {
        player.setCostume(color);
    }

    // Removes and creates new monsters upon death
    private void handleMonsterDeaths() {
        ArrayList<Character> toRemove = new ArrayList<>();
        for (Character m : characters) {
            if (m.healthBar.getCurrHealth() == 0) {
                toRemove.add(m);
            }
        }
        for (Character m : toRemove) {
            m.getAnimationManager().playAnimation(m.deathDirection);
            vanishingCharacters.add(m);
            characters.remove(m);
            characters.add(new SlimeMeleeMonster(context,
                    (int) (Math.random() * Constants.displaySize.x),
                    Constants.displaySize.y - 100));
        }
        toRemove.clear();
    }
}
