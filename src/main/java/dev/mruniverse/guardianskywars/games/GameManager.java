package dev.mruniverse.guardianskywars.games;

import dev.mruniverse.guardianskywars.GuardianSkyWars;
import dev.mruniverse.guardianskywars.enums.GuardianFiles;
import dev.mruniverse.guardianskywars.enums.SaveMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
@SuppressWarnings("unused")
public class GameManager {
    private final ArrayList<Game> games = new ArrayList<>();
    private final HashMap<World,Game> gamesWorlds = new HashMap<>();
    //public HashMap<String,GameChests> gameChests = new HashMap<>();
    //public HashMap<GameType,GameMenu> gameMenu = new HashMap<>();
    //private final GameMainMenu gameMainMenu;
    private final GuardianSkyWars plugin;
    public GameManager(GuardianSkyWars plugin) {
        this.plugin = plugin;
        //gameMainMenu = new GameMainMenu(plugin);
    }
    public void loadChests() {
        ConfigurationSection section = plugin.getStorage().getControl(GuardianFiles.CHESTS).getConfigurationSection("chests");
        if(section == null) return;
        //for(String chest : section.getKeys(false)) {
        //    gameChests.put(chest,new GameChests(plugin,chest));
        //}
    }
    //public GameChests getGameChest(String chestName) {
    //    return gameChests.get(chestName);
    //}
    public Game getGame(String gameName) {
        if (this.games.size() < 1)
            return null;
        //for (Game game : this.games) {
        //    if (game.getConfigName().equalsIgnoreCase(gameName))
        //        return game;
        //}
        return null;
    }

    public void loadGames() {
        try {
            if(plugin.getStorage().getControl(GuardianFiles.GAMES).contains("games")) {
                for (String gameName : Objects.requireNonNull(plugin.getStorage().getControl(GuardianFiles.GAMES).getConfigurationSection("games")).getKeys(false)) {
                    if(plugin.getStorage().getControl(GuardianFiles.GAMES).getBoolean("games." + gameName + ".enabled")) {
                        String mapName = plugin.getStorage().getControl(GuardianFiles.GAMES).getString("games." + gameName + ".gameName");
                        if(mapName == null) {
                            plugin.getStorage().getControl(GuardianFiles.GAMES).set("games." + gameName + ".gameName",gameName);
                            mapName = gameName;
                            plugin.getStorage().save(SaveMode.GAMES_FILES);
                        }
                        loadGameWorlds();
                        Game game = new Game(plugin, gameName, mapName);
                        this.games.add(game);
                        plugin.getLogs().debug("Game " + gameName + " loaded!");
                    } else {
                        plugin.getLogs().debug("Game " + gameName + " is not enabled in games.yml, this game wasn't loaded.");
                    }
                }
                plugin.getLogs().info(this.games.size() + " game(s) loaded!");
            } else {
                plugin.getLogs().info("You don't have games created yet.");
            }
        }catch (Throwable throwable) {
            plugin.getLogs().error("Can't load games plugin games :(");
            plugin.getLogs().error(throwable);
        }
    }
    public void loadGameWorlds() {
        //for(Game game : getGames()) {
        //    gamesWorlds.put(game.gameCenter.getWorld(),game);
        //}
    }

    public void addGame(String configName,String gameName) {
        if(getConfigGame(configName) != null) {
            return;
        }
        if(gameName == null) {
            plugin.getStorage().getControl(GuardianFiles.GAMES).set("games." + configName + ".gameName",configName);
            gameName = configName;
            plugin.getStorage().save(SaveMode.GAMES_FILES);
        }
        Game game = new Game(plugin,configName,gameName);
        this.games.add(game);
        plugin.getLogs().debug("Game " + gameName + " loaded!");
    }
    public void delGame(String gameName) {
        if(getGame(gameName) != null) {
            this.games.remove(getGame(gameName));
        }
        plugin.getLogs().debug("Game " + gameName + " unloaded!");
    }
    public ArrayList<Game> getGames() {
        return games;
    }
    public HashMap<World,Game> getGameWorlds() { return gamesWorlds; }

    //public Game getGame(Player player) {
    //    return plugin.getPlayerData(player.getUniqueId()).getGame();
    //}

    public Game getConfigGame(String name) {
        if (this.games.size() < 1)
            return null;
        //for (Game game : this.games) {
        //    if (game.getConfigName().equalsIgnoreCase(name))
        //        return game;
        //}
        return null;
    }

    public boolean existGame(String name) {
        boolean exist = false;
        if(getConfigGame(name) != null) exist = true;
        return exist;
    }

    public void joinGame(Player player,String gameName) {
        //if(!existGame(gameName)) {
        //    plugin.getUtils().sendMessage(player, Objects.requireNonNull(plugin.getStorage().getControl(GuardianFiles.MESSAGES).getString("messages.admin.arenaError")).replace("%arena_id%",gameName));
        //    return;
        //}
        //Game game = getGame(gameName);
        //game.join(player);
    }
    public void createGameFiles(String gameName) {
        FileConfiguration gameFiles = plugin.getStorage().getControl(GuardianFiles.GAMES);
        gameFiles.set("games." + gameName + ".enabled", false);
        gameFiles.set("games." + gameName + ".time", 500);
        gameFiles.set("games." + gameName + ".gameName", gameName);
        gameFiles.set("games." + gameName + ".disableRain", true);
        gameFiles.set("games." + gameName + ".max", 10);
        gameFiles.set("games." + gameName + ".min", 2);
        gameFiles.set("games." + gameName + ".worldTime", 0);
        gameFiles.set("games." + gameName + ".gameType","SOLO");
        gameFiles.set("games." + gameName + ".locations.waiting", "notSet");
        gameFiles.set("games." + gameName + ".locations.superChests", new ArrayList<>());
        gameFiles.set("games." + gameName + ".locations.cages", new ArrayList<>());
        gameFiles.set("games." + gameName + ".signs", new ArrayList<>());
        plugin.getStorage().save(SaveMode.GAMES_FILES);
    }
    public void setWaiting(String gameName, Location location) {
        try {
            String gameLoc = Objects.requireNonNull(location.getWorld()).getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch();
            plugin.getStorage().getControl(GuardianFiles.GAMES).set("games." + gameName + ".locations.waiting", gameLoc);
            plugin.getStorage().save(SaveMode.GAMES_FILES);
        }catch (Throwable throwable) {
            plugin.getLogs().error("Can't set waiting lobby for game: " + gameName);
            plugin.getLogs().error(throwable);
        }
    }

    public void setGameType(String gameName, GameType gameType) {
        try {
            plugin.getStorage().getControl(GuardianFiles.GAMES).set("games." + gameName + ".gameType", gameType.toString().toUpperCase());
            plugin.getStorage().save(SaveMode.GAMES_FILES);
        }catch (Throwable throwable) {
            plugin.getLogs().error("Can't set game Type for game: " + gameName);
            plugin.getLogs().error(throwable);
        }
    }

    public void setGameName(String configName, String gameName) {
        try {
            plugin.getStorage().getControl(GuardianFiles.GAMES).set("games." + configName + ".gameName", gameName);
            plugin.getStorage().save(SaveMode.GAMES_FILES);
        }catch (Throwable throwable) {
            plugin.getLogs().error("Can't set game name for game: " + configName);
            plugin.getLogs().error(throwable);
        }
    }
    public void setMax(String gameName,Integer max) {
        plugin.getStorage().getControl(GuardianFiles.GAMES).set("games." + gameName + ".max", max);
        plugin.getStorage().save(SaveMode.GAMES_FILES);
    }
    public void setMin(String gameName,Integer min) {
        plugin.getStorage().getControl(GuardianFiles.GAMES).set("games." + gameName + ".min", min);
        plugin.getStorage().save(SaveMode.GAMES_FILES);
    }
    public void setMode(String gameName,GameType type) {
        plugin.getStorage().getControl(GuardianFiles.GAMES).set("games." + gameName + ".gameType", type.toString().toUpperCase());
        plugin.getStorage().save(SaveMode.GAMES_FILES);
    }






}


