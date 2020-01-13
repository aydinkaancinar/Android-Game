package com.example.game.gamecomponents.strategies;

import com.example.game.gamecomponents.characters.Character;
import com.example.game.gamecomponents.characters.monsters.SlimeMeleeMonster;
import com.example.game.gamecomponents.characters.player.Player;

import java.util.ArrayList;

interface Strategy {
    void move(Player player, Character character, ArrayList<SlimeMeleeMonster> collidableCharacters);
}
