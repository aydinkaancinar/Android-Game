package com.example.game.scenes;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.graphics.Canvas;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.content.SharedPreferences;
import com.example.game.gamecomponents.mazecreator.CollisionChecker;
import com.example.game.gamecomponents.mazecreator.MazeGenerator;
import com.example.game.design.Background;
import com.example.game.design.Button;

import java.util.ArrayList;

public class SceneManager {
    private final ArrayList<Scene> scenes; //list of scenes in the game
    static int activeScene; //this is set to change the scene on the display
    static int nextScene; //this is used to indicate next scene to display after loading scene
    private final Context context;
    static private SharedPreferences pref; //this is a built in class that saves information
    static private Editor editor; //is used to edit the info in SharedPreferences
    static private SurvivalScene game1;
    static private MenuScene menu;
    static private MazeScene maze;
    static private GlassScene game3;
    static private Loading loading;
    private ScoreBoardScene scoreScene;
    private CustomizationScene store;
    private WelcomeScene welcome;
    static private SignIn signIn;
    static private Login login;
    static private int xpScore;
    static private int xp; //total xp of all games
    static private int xp1; //xp for first game
    static private int xp2; //xp for second game
    static private int xp3; //xp for third game
    static private String userInfo; //this string codes the user info as username+password
    static private String userName; //username of the user
    static private String color; //color of the character that the user uses
    static String[][] highscore; //this array has the names of the users that achieved an highscore
    static int[][] highscoreScores; //this is the highscores
    private final MazeGenerator mazeGenerator;
    private final CollisionChecker collisionChecker;
    private Background background;
    private final Button quitButton;

    public SceneManager(Context context) {
        this.context = context;
        highscoreScores = new int[3][3];
        highscore = new String[3][3];
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        activeScene = 6;
        scenes = new ArrayList<>();
        addAllScenes();
        editor = pref.edit();
        userInfo = "";
        xp = 0;
        xp1 = 0;
        xp2 = 0;
        xp3 = 0;
        color = "blue";
        userName = "";
        xpScore = 0;
        mazeGenerator = new MazeGenerator();
        collisionChecker = new CollisionChecker();
        background = new Background(context, pref.getString(userInfo + "background", "grass"));
        quitButton = new Button(850, 300, 100, 100, "X");
    }

    public void receiveTouch(MotionEvent event) {
        if (scenes.size() > 0) {
            scenes.get(activeScene).receiveTouch(event);
        }
    }

    public void update() {
        if (scenes.size() > 0) {
            scenes.get(activeScene).update();
        }
    }

    public void draw(Canvas canvas) {
        if (scenes.size() > 0) {
            scenes.get(activeScene).draw(canvas);
        }

    }

    void resetScenes() {
        // collects the xp gained in the games and adds them up to the total xp
        xp += (game1.getXp() + maze.getXp() + game3.getXp());
        xpScore += game3.getScore();
        xp2 += maze.getXp();
        xp1 += game1.getXp();
        xp3 += game3.getXp();
        //sets the color of the character
        color = store.getCostume();
        //sets background
        this.background = new Background(context, pref.getString(userInfo + "background", "grass"));
        //gets the highscores from SharedPreferences
        getHighScores();
        //checks if the user scored an highscore
        checkHighScore();
        //sets the xp to display in the menu
        menu.setXp(xp);
        //saves the highscores
        editor.putInt(userInfo + "xpscore", xpScore);
        editor.putInt(userInfo + "xp", xp);
        editor.putInt(userInfo + "xp1", xp1);
        editor.putInt(userInfo + "xp2", xp2);
        editor.putInt(userInfo + "xp3", xp3);
        editor.apply();
        scenes.clear();
        addAllScenes();
    }

    //this method gets the highscores from the SharedPreferences
    private void getHighScores() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                highscore[i][j] = pref.getString("highname" + i + "" + j, "");
                highscoreScores[i][j] = pref.getInt("highscore" + i + "" + j, 0);
            }
        }
    }

    //checks if the user scored an highscore
    private void checkHighScore() {
        if (game1.getXp() > 0) {
            swapScores(game1.getXp(), 0);
            setHighScores();
        } else if (maze.getXp() > 0) {
            swapScores(maze.getXp(), 1);
            setHighScores();
        } else if (game3.getXp() > 0) {
            swapScores(game3.getXp(), 2);
            setHighScores();
        }
    }

    //enters back the highscores to the SharedPreferences
    private void setHighScores() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.println("Scores: " + highscore[i][j] + highscoreScores[i][j]);
                editor.putString("highname" + i + "" + j, highscore[i][j]);
                editor.apply();
                editor.putInt("highscore" + i + "" + j, highscoreScores[i][j]);
                editor.apply();
            }
        }
    }

    //swaps the placement of the scores
    private void swapScores(int score, int i) {
        if (score >= highscoreScores[i][0]) {
            highscoreScores[i][2] = highscoreScores[i][1];
            highscore[i][2] = highscore[i][1];
            highscoreScores[i][1] = highscoreScores[i][0];
            highscore[i][1] = highscore[i][0];
            highscoreScores[i][0] = score;
            highscore[i][0] = userName;
        } else if (score >= highscoreScores[i][1]) {
            highscoreScores[i][2] = highscoreScores[i][1];
            highscore[i][2] = highscore[i][1];
            highscoreScores[i][1] = score;
            highscore[i][1] = userName;
        } else if (score >= highscoreScores[i][2]) {
            highscoreScores[i][2] = score;
            highscore[i][2] = userName;
        }
    }

    private void addAllScenes() {
        login = new Login(this, new Background(context, "grass"));
        signIn = new SignIn(context, this);
        game1 = new SurvivalScene(context, this, background);
        menu = new MenuScene(this, background);
        maze = new MazeScene(context, this, mazeGenerator, collisionChecker, background,
                quitButton);
        game3 = new GlassScene(context, this, background);
        loading = new Loading(background);
        store = new CustomizationScene(this, new Background(context, "store"));
        welcome = new WelcomeScene(this, new Background(context, "login"));
        scoreScene = new ScoreBoardScene(this, new Background(context, "grass"));
        scenes.add(login);
        scenes.add(menu);
        scenes.add(game1);
        scenes.add(maze);
        scenes.add(game3);
        scenes.add(store);
        scenes.add(welcome);
        scenes.add(signIn);
        scenes.add(scoreScene);
        scenes.add(loading);
    }

    //gets the xp depending on the type of xp required
    int getXp(String xpType) {
        return pref.getInt(userInfo + "xp" + xpType, 0);
    }

    //gets username and password from login and sets the informations of the user for the game
    //by taking the corresponding userinfo from SharedPreferences
    static void setUserInfo(String name, String password) {
        userInfo = name + password;
        userName = name;
        color = pref.getString(userInfo + "color", "blue");
        xp = pref.getInt(userInfo + "xp", 0);
        xp1 = pref.getInt(userInfo + "xp1", 0);
        xp2 = pref.getInt(userInfo + "xp2", 0);
        xp3 = pref.getInt(userInfo + "xp3", 0);
        xpScore = pref.getInt(userInfo + "xpscore", 0);
        menu.setXp(xp);
        game1.setCostume(color);
        maze.setCostume(color);
        game3.setCostume(color);
    }

    //is used to register a new user into the game
    static void registerUser(String user, String pass) {
        editor.putString(user + "password", pass);
        editor.apply();
        editor.putBoolean(user, true);
        editor.apply();
    }

    //checks if user exist
    static boolean userExists(String user) {
        return pref.getBoolean(user, false);
    }

    //checks if the password is valid for the user
    static boolean validPassword(String user, String pass) {
        return pass.equals(pref.getString(user + "password", ""));
    }

    //sets tyhe costume of the user
    static void setCostume(String col) {
        editor.putString(userInfo + "color", col);
        color = col;
        game1.setCostume(color);
        maze.setCostume(color);
        game3.setCostume(color);
        editor.apply();
    }

    //returns the color of the costume of the user
    static String getCostume() {
        return pref.getString(userInfo + "color", "blue");
    }

    static String getUserName() {
        return userName;
    }

    static void changeUser() {
        login.resetUser();
    }

    public void setBackground(String type) {
        this.background = new Background(context, type);
        editor.putString(userInfo + "background", type);
        editor.apply();
    }
}
