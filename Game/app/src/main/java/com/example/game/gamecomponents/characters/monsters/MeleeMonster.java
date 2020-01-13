package com.example.game.gamecomponents.characters.monsters;

import com.example.game.gamecomponents.strategies.ChaseStrategy;
import com.example.game.gamecomponents.strategies.StrategyContext;
import com.example.game.gamecomponents.characters.Character;
import com.example.game.gamecomponents.characters.player.Player;

import java.util.ArrayList;

class MeleeMonster extends Character {
    private final StrategyContext context;

    MeleeMonster() {
        this.context = new StrategyContext(new ChaseStrategy());
    }

    public void update(Player player, ArrayList<SlimeMeleeMonster> collidableCharacters) {
            context.executeStrategy(player, this, collidableCharacters);

    }

}
