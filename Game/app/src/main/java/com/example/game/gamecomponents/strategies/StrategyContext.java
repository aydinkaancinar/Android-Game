package com.example.game.gamecomponents.strategies;

import com.example.game.gamecomponents.characters.Character;
import com.example.game.gamecomponents.characters.monsters.SlimeMeleeMonster;
import com.example.game.gamecomponents.characters.player.Player;

import java.util.ArrayList;

public class StrategyContext {
    private final Strategy strategy;

    public StrategyContext(Strategy strategy) {
        this.strategy = strategy;
    }

    public void executeStrategy(Player player, Character character,
                                ArrayList<SlimeMeleeMonster> collidableCharacters) {
        strategy.move(player, character, collidableCharacters);

    }
}