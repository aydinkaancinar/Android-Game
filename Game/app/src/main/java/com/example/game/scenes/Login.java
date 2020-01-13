package com.example.game.scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import com.example.game.gamecomponents.Builder;
import com.example.game.design.Button;
import com.example.game.design.Background;
import java.util.ArrayList;


public class Login implements Scene {
    private String email;
    private String password;
    private String passwordDisplay; //the hidden password that only shows ****... as user types
    Button signIn;
    private final ArrayList<Button> buttons;
    private final Background background;
    private int counter; //used to store click counts
    private int counter2; //used to store click counts
    private final Button erase;
    private boolean pass; //checks if user is typing password or username
    private final Button passwordButton; //to switch to typing password
    private final Button emailButton; //to switch to typing username
    private String cursorEmail = "|"; //shows user is typing email
    private String cursorPassword = ""; //shows user is typing password
    private final SceneManager sManager;
    String message; //has the message for user for incorrect password/email
    private boolean noUserName = false; //is true when invalid username is typed
    private int count = 0; //used to make "|" blip when user is typing
    String buttonText; //used to modify login text
    private boolean noUser = false; //is true when type user doesn't exist
    boolean newUser = false; //false when login is chosen true when new user is registered
    private final Button quitButton;

    Login(SceneManager manager, Background background) {
        sManager = manager;
        email = "";
        password = "";
        passwordDisplay = "";
        pass = false;
        buttonText = "Login";
        this.background = background;
        quitButton = new Button(750, 50, 300, 100, "BACK");
        buttons = Builder.buildEmailKB();
        message = "User doesn't exist";
        signIn = new Button(30, 1900, 600, 100, buttonText);
        erase = new Button(800, 1700, 200, 100, "<--");
        emailButton = new Button(30, 700, 1000, 100, "e-mail:");
        passwordButton = new Button(30, 940, 1000, 100, "password:");
    }

    public void update() {
        background.update();
        //this is used to show the user where they are typing
        if (count % 20 < 10) {
            cursorEmail = "";
        } else {
            if (!pass) cursorEmail = "|"; //this "|" blips if user is entering username
        }
        if (count % 20 < 10) {
            cursorPassword = "";
        } else {
            if (pass) cursorPassword = "|"; //this "|" blips if user is entering password
        }
        count++;
    }

    public void draw(Canvas canvas) {
        //draws th components of the login menu
        background.draw(canvas);
        for (Button b : buttons) {
            b.draw(canvas);
        }
        signIn.draw(canvas);
        erase.draw(canvas);
        quitButton.draw(canvas);
        passwordButton.draw(canvas);
        emailButton.draw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(70);
        Paint p1 = new Paint();
        Paint p2 = new Paint();
        p1.setColor(Color.rgb(0, 0, 0));
        p2.setColor(Color.rgb(255, 255, 255));
        canvas.drawRect(28, 823, 1032, 927, p1);
        canvas.drawRect(30, 825, 1030, 925, p2);
        canvas.drawRect(28, 1053, 1032, 1157, p1);
        canvas.drawRect(30, 1055, 1030, 1155, p2);
        canvas.drawText("" + email + cursorEmail, 30, 890, paint);
        canvas.drawText("" + passwordDisplay + cursorPassword, 30, 1120, paint);
        if (noUser) canvas.drawText(message, 30, 240, paint);
        if (noUserName) {
            canvas.drawText("You need to enter a valid", 30, 300, paint);
            canvas.drawText("e-mail and password", 30, 360, paint);
        }
    }

    public void receiveTouch(MotionEvent event) {
        //this checks if the keyboard buttons are clicked
        for (Button b : buttons) {
            if (b.isClicked((int) event.getX(), (int) event.getY())) {
                if (counter % 2 == 0) {
                    if (pass) {
                        password += b.getName();
                        passwordDisplay += "*";
                    } else {
                        email += b.getName();
                    }
                }
                counter += 1;
            }
        }
        //this check if the quit button is clicked
        if (quitButton.isClicked((int) event.getX(), (int) event.getY())) {
            SceneManager.activeScene = 6;
        }
        //this lets user to switch entering the email
        if (emailButton.isClicked((int) event.getX(), (int) event.getY() - 100)) {
            pass = false;
        }
        //this lets user to switch entering the password
        if (passwordButton.isClicked((int) event.getX(), (int) event.getY() - 100)) {
            pass = true;
        }
        //this button erases the last character of the string(username or password) depending which
        //one the user is entering
        if (erase.isClicked((int) event.getX(), (int) event.getY())) {
            if (counter2 % 2 == 0) {
                if (pass) {
                    if (password.length() != 0) {
                        password = password.substring(0, password.length() - 1);
                        passwordDisplay = passwordDisplay.substring(0, passwordDisplay.length() - 1);
                    }
                } else {
                    if (email.length() != 0) email = email.substring(0, email.length() - 1);
                }
            }
            counter2 += 1;
        }
        //after this button is clicked the user is registered or an already existing
        //user enters the game
        if (signIn.isClicked((int) event.getX(), (int) event.getY())) {
            if (!email.equals("") && !password.equals("")) {
                if (email.contains("@")) {
                    if (!newUser == checkValidUserName(email)) {
                        if (newUser) {
                            SceneManager.registerUser(email, password);
                            SceneManager.setUserInfo(email, password);
                            sManager.resetScenes();
                            SceneManager.nextScene = 1;
                            SceneManager.activeScene = 9;
                        } else {
                            if (SceneManager.validPassword(email, password)) {
                                SceneManager.setUserInfo(email, password);
                                sManager.resetScenes();
                                SceneManager.nextScene = 1;
                                SceneManager.activeScene = 9;
                            } else {
                                noUserName = true;
                            }
                        }
                    } else {
                        noUser = true;
                    }
                } else {
                    noUserName = true;
                }
            } else {
                noUserName = true;
            }
        }
    }
    //method to check if a user exists
    private boolean checkValidUserName(String name) {
        return SceneManager.userExists(name);
    }
    //deletes user info
    void resetUser() {
        password = "";
        email = "";
        passwordDisplay = "";
        noUserName = false;
    }
    @Override
    public void terminate() {
        SceneManager.activeScene = 0;
    }
}
