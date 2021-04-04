package dev.mruniverse.guardianskywars.games.runnables;

import dev.mruniverse.guardianskywars.GuardianSkyWars;
import dev.mruniverse.guardianskywars.games.Game;

public class Start implements Runnable {
    private final GuardianSkyWars plugin;
    private final Game currentGame;
    public Start(GuardianSkyWars main, Game currentGame) {
        plugin = main;
        this.currentGame = currentGame;
    }
    public void run() {
        //for (Game game : plugin.getGameManager().getGames()) {
        //    if (game.gameTimer == 1) {
        //        game.gameCount(GameCountType.START_COUNT);
        //        continue;
        //    }
        //    if (game.gameTimer == 2)
        //        game.gameCount(GameCountType.IN_GAME_COUNT);
        //}
    }
}
