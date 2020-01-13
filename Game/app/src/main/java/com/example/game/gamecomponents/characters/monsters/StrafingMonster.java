package com.example.game.gamecomponents.characters.monsters;

import com.example.game.gamecomponents.strategies.StrafeStrategy;
import com.example.game.gamecomponents.strategies.StrategyContext;
import com.example.game.gamecomponents.characters.Character;
import com.example.game.gamecomponents.characters.player.Player;

import java.util.ArrayList;

class StrafingMonster extends Character {
    private final StrategyContext context;

    StrafingMonster() {
        this.context = new StrategyContext(new StrafeStrategy());
    }

    public void update(Player player, ArrayList<SlimeMeleeMonster> collidableCharacters) {
        context.executeStrategy(player, this, collidableCharacters);

    }

}

