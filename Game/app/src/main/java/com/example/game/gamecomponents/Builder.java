package com.example.game.gamecomponents;

import android.content.Context;

import com.example.game.gamecomponents.characters.monsters.BeeStrafingMonster;
import com.example.game.gamecomponents.characters.monsters.SlimeMeleeMonster;

import java.util.ArrayList;
import java.util.Random;
import com.example.game.design.Button;

public class Builder {

////  Builds and returns an array of SlimeMeleeMonsters
//    public static ArrayList<SlimeMeleeMonster> buildSlime(Context context) {
//        ArrayList<SlimeMeleeMonster> monsters = new ArrayList<>();
//        Random rand = new Random();
//        int numMonsters = rand.nextInt(20);
//
//        for (int i = 0; i <= numMonsters; i++) {
//            SlimeMeleeMonster m = new SlimeMeleeMonster(context, rand.nextInt(1000), rand.nextInt(1000));
//            monsters.add(m);
//        }
//        return monsters;
//    }

//    Builds and returns an array of BeeStrafingMonsters
    public static ArrayList<BeeStrafingMonster> buildBee(Context context) {
        ArrayList<BeeStrafingMonster> monsters = new ArrayList<>();
        Random rand = new Random();
        int numMonsters = rand.nextInt(30);

        for (int i = 0; i <= numMonsters; i++) {
            int x = rand.nextInt(Constants.displaySize.x);
            int y = rand.nextInt(1300);
            if (y < 200){
                y += 200;
            }
            BeeStrafingMonster m = new BeeStrafingMonster(context, x, y);
            monsters.add(m);
        }
        return monsters;
    }

//  Builds and returns an array of buttons needed for guessing game keyboard
    public static Button[] buildNumericKB() {
        Button[] buttons = new Button[10];
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Button(100*i + 37, 1500, 100, 100, i+"");
        }
        return buttons;
    }

//  Builds and returns an array of buttons needed for login screen keyboard
    public static ArrayList<Button> buildEmailKB() {
        ArrayList<Button> buttons = new ArrayList<>();
        String s = "1234567890qwertyuiopasdfghjklzxcvbnm.@,";
        for (int i = 0; i < 10; i++) {
            buttons.add(new Button(105 * i +18, 1180, 100, 100, String.valueOf(s.charAt(i))));
        }
//        Making buttons for first 10 qwerty characters
        for (int i = 0; i < 10; i++) {
            buttons.add(new Button(105 * i + 18, 1300, 100, 100, String.valueOf(s.charAt(10 + i))));
        }
//        Making buttons for next 9 qwerty values
        for (int i = 0; i < 9; i++) {
            buttons.add(new Button(105 * i +70, 1420, 100, 100, String.valueOf(s.charAt(20 + i))));
        }
//        Making buttons for last 9 qwerty values
        for (int i = 0; i < 9; i++) {
            buttons.add(new Button(105 * i +70, 1540, 100, 100, String.valueOf(s.charAt(29 + i))));
        }
        return buttons;
    }

}
