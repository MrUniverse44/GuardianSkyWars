package dev.mruniverse.guardianskywars.games;

import dev.mruniverse.guardianskywars.GuardianSkyWars;
import dev.mruniverse.guardianskywars.enums.GameType;
import dev.mruniverse.guardianskywars.enums.GuardianFiles;
import dev.mruniverse.guardianskywars.enums.SaveMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
@SuppressWarnings("unused")
public class GameManager {
    private final ArrayList<GameInfo> games = new ArrayList<>();
    private final HashMap<World, GameInfo> gamesWorlds = new HashMap<>();
    private final GuardianSkyWars plugin;
    public GameManager(GuardianSkyWars plugin) {
        this.plugin = plugin;
    }
    public GameInfo getGame(String gameName) {
        if (this.games.size() < 1)
            return null;
        for (GameInfo game : games) {
            if (game.getConfigName().equalsIgnoreCase(gameName))
                return game;
        }
        return null;
    }

    public void registerGame(String gameName) {
        // * Working now..
    }

    public void unloadGame(String gameName) {
        // * Working now..
    }

    public void loadGame(String gameName) {
        // * Working now..
    }

    public void createPrivateGame(Player player) {
        // * Coming Soon..
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
                        GameInfo gameInfo = new GameInfo(plugin, gameName, mapName);
                        this.games.add(gameInfo);
                        plugin.getLogs().debug("game " + gameName + " loaded!");
                    } else {
                        plugin.getLogs().debug("game " + gameName + " is not enabled in games.yml, this game wasn't loaded.");
                    }
                }
                plugin.getLogs().info(this.games.size() + " game(s) loaded!");
            } else {
                plugin.getLogs().info("You don't have games created yet.");
            }
        }catch (Throwable throwable) {
            plugin.getLogs().error("Can't load games of the plugin :(");
            plugin.getLogs().error(throwable);
        }
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
        GameInfo gameInfo = new GameInfo(plugin,configName,gameName);
        games.add(gameInfo);
        plugin.getLogs().debug("GameInfo " + gameName + " loaded!");
    }
    public void delGame(String gameName) {
        if(getGame(gameName) != null) {
            this.games.remove(getGame(gameName));
        }
        plugin.getLogs().debug("GameInfo " + gameName + " unloaded!");
    }
    public ArrayList<GameInfo> getGames() {
        return games;
    }
    public HashMap<World, GameInfo> getGameWorlds() { return gamesWorlds; }

    public GameInfo getConfigGame(String name) {
        if(games.size() < 1)
            return null;
        for (GameInfo game : games) {
            if (game.getConfigName().equalsIgnoreCase(name))
                return game;
        }
        return null;
    }

    public boolean existGame(String name) {
        return getConfigGame(name) != null;
    }

    public void joinGame(Player player,String gameName) {
        GameInfo game = getGame(gameName);
        game.join(player);
    }
    public void createGameFiles(String gameName,String worldName) {
        FileConfiguration gameFiles = plugin.getStorage().getControl(GuardianFiles.GAMES);
        String path = "games." + gameName + ".";
        gameFiles.set(path + "enabled", false);
        gameFiles.set(path + "duration",500);
        gameFiles.set(path + "max",10);
        gameFiles.set(path + "min",2);
        gameFiles.set(path + "time",0);
        gameFiles.set(path + "name",gameName);
        gameFiles.set(path + "mode","SKYISLANDS_NORMAL");
        gameFiles.set(path + "locations.center","notSet");
        gameFiles.set(path + "locations.waiting","notSet");
        gameFiles.set(path + "cages", new ArrayList<>());
        gameFiles.set(path + "center-chests", new ArrayList<>());
        gameFiles.set(path + "super-chests", new ArrayList<>());
        gameFiles.set(path + "signs", new ArrayList<>());
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

    // * Boolean, True: REMOVED, False: THIS CAGE DOESN'T EXIST.
    public boolean removeCageLocation(String gameName,Location location) {
        try {
            World world = location.getWorld();
            if(world == null) return false;
            String gameLoc = world.getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch();
            List<String> cages;
            if (plugin.getStorage().getControl(GuardianFiles.GAMES).get("games." + gameName + ".cages") != null) {
                cages = plugin.getStorage().getControl(GuardianFiles.GAMES).getStringList("games." + gameName + ".cages");
                if(cages.contains(gameLoc)) {
                    cages.remove(gameLoc);
                    plugin.getStorage().getControl(GuardianFiles.GAMES).set("games." + gameName + ".cages", cages);
                    plugin.getStorage().save(SaveMode.GAMES_FILES);
                    return true;
                }
                return false;
            }
            return false;
        }catch (Throwable throwable) {
            plugin.getLogs().error("Can't remove cage location.");
            plugin.getLogs().error(throwable);
        }
        return false;
    }

    // * Boolean, True: REMOVED, False: THIS CHEST DOESN'T EXIST.
    public boolean removeCenterChest(String gameName,Location location) {
        try {
            World world = location.getWorld();
            if(world == null) return false;
            String gameLoc = world.getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch();
            List<String> center;
            if (plugin.getStorage().getControl(GuardianFiles.GAMES).get("games." + gameName + ".center-chests") != null) {
                center = plugin.getStorage().getControl(GuardianFiles.GAMES).getStringList("games." + gameName + ".center-chests");
                if(center.contains(gameLoc)) {
                    center.remove(gameLoc);
                    plugin.getStorage().getControl(GuardianFiles.GAMES).set("games." + gameName + ".center-chests", center);
                    plugin.getStorage().save(SaveMode.GAMES_FILES);
                    return true;
                }
                return false;
            }
            return false;
        }catch (Throwable throwable) {
            plugin.getLogs().error("Can't remove chest location.");
            plugin.getLogs().error(throwable);
        }
        return false;
    }

    // * Boolean, True: REMOVED, False: THIS CHEST DOESN'T EXIST.
    public boolean removeSuperChest(String gameName,Location location) {
        try {
            World world = location.getWorld();
            if(world == null) return false;
            String gameLoc = world.getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch();
            List<String> sChest;
            if (plugin.getStorage().getControl(GuardianFiles.GAMES).get("games." + gameName + ".super-chests") != null) {
                sChest = plugin.getStorage().getControl(GuardianFiles.GAMES).getStringList("games." + gameName + ".super-chests");
                if(sChest.contains(gameLoc)) {
                    sChest.remove(gameLoc);
                    plugin.getStorage().getControl(GuardianFiles.GAMES).set("games." + gameName + ".super-chests", sChest);
                    plugin.getStorage().save(SaveMode.GAMES_FILES);
                    return true;
                }
                return false;
            }
            return false;
        }catch (Throwable throwable) {
            plugin.getLogs().error("Can't remove chest location.");
            plugin.getLogs().error(throwable);
        }
        return false;
    }

    // * Boolean, True: ADDED, False: CAN'T ADD THIS CHEST.
    public boolean addCenterChests(String gameName,Location location) {
        try {
            World world = location.getWorld();
            if(world == null) return false;
            String gameLoc = world.getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch();
            List<String> center;
            if (plugin.getStorage().getControl(GuardianFiles.GAMES).get("games." + gameName + ".center-chests") != null) {
                center = plugin.getStorage().getControl(GuardianFiles.GAMES).getStringList("games." + gameName + ".center-chests");
            } else {
                center = new ArrayList<>();
            }
            if(!center.contains(gameLoc)) {
                center.add(gameLoc);
                plugin.getStorage().getControl(GuardianFiles.GAMES).set("games." + gameName + ".center-chests", center);
                plugin.getStorage().save(SaveMode.GAMES_FILES);
                return true;
            }
            return false;
        }catch (Throwable throwable) {
            plugin.getLogs().error("Can't add chest location.");
            plugin.getLogs().error(throwable);
        }
        return false;
    }


    // * Boolean, True: ADDED, False: CAN'T ADD THIS CHEST.
    public boolean addSuperChest(String gameName,Location location) {
        try {
            World world = location.getWorld();
            if(world == null) return false;
            String gameLoc = world.getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch();
            List<String> sChest;
            if (plugin.getStorage().getControl(GuardianFiles.GAMES).get("games." + gameName + ".super-chests") != null) {
                sChest = plugin.getStorage().getControl(GuardianFiles.GAMES).getStringList("games." + gameName + ".super-chests");
            } else {
                sChest = new ArrayList<>();
            }
            if(!sChest.contains(gameLoc)) {
                sChest.add(gameLoc);
                plugin.getStorage().getControl(GuardianFiles.GAMES).set("games." + gameName + ".super-chests", sChest);
                plugin.getStorage().save(SaveMode.GAMES_FILES);
                return true;
            }
            return false;
        }catch (Throwable throwable) {
            plugin.getLogs().error("Can't add chest location.");
            plugin.getLogs().error(throwable);
        }
        return false;
    }

    // * Boolean, True: ADDED, False: CAN'T ADD THIS CAGE.
    public boolean addCageLocation(String gameName,Location location) {
        try {
            World world = location.getWorld();
            if(world == null) return false;
            String gameLoc = world.getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch();
            List<String> cages;
            if (plugin.getStorage().getControl(GuardianFiles.GAMES).get("games." + gameName + ".cages") != null) {
                cages = plugin.getStorage().getControl(GuardianFiles.GAMES).getStringList("games." + gameName + ".cages");
            } else {
                cages = new ArrayList<>();
            }
            if(!cages.contains(gameLoc)) {
                cages.add(gameLoc);
                plugin.getStorage().getControl(GuardianFiles.GAMES).set("games." + gameName + ".cages", cages);
                plugin.getStorage().save(SaveMode.GAMES_FILES);
                return true;
            }
            return false;
        }catch (Throwable throwable) {
            plugin.getLogs().error("Can't add cage location.");
            plugin.getLogs().error(throwable);
        }
        return false;
    }

    // pending: addCages, removeCages, addSuperChests, removeSuperChest, addSigns, removeSigns!

    public void setCenter(String gameName, Location location) {
        try {
            World world = location.getWorld();
            if(world == null) return;
            String gameLoc = world.getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch();
            plugin.getStorage().getControl(GuardianFiles.GAMES).set("games." + gameName + ".locations.center", gameLoc);
            plugin.getStorage().save(SaveMode.GAMES_FILES);
        }catch (Throwable throwable) {
            plugin.getLogs().error("Can't set arena-center for game: " + gameName);
            plugin.getLogs().error(throwable);
        }
    }

    public void setGameType(String gameName, GameType gameType) {
        try {
            plugin.getStorage().getControl(GuardianFiles.GAMES).set("games." + gameName + ".mode", gameType.toString().toUpperCase());
            plugin.getStorage().save(SaveMode.GAMES_FILES);
        }catch (Throwable throwable) {
            plugin.getLogs().error("Can't set game Type for game: " + gameName);
            plugin.getLogs().error(throwable);
        }
    }

    public void setGameName(String configName, String gameName) {
        try {
            plugin.getStorage().getControl(GuardianFiles.GAMES).set("games." + configName + ".name", gameName);
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
        plugin.getStorage().getControl(GuardianFiles.GAMES).set("games." + gameName + ".mode", type.toString().toUpperCase());
        plugin.getStorage().save(SaveMode.GAMES_FILES);
    }

    public static boolean isChest(Material evalMaterial) {
        if(evalMaterial.equals(Material.CHEST)) return true;
        if(evalMaterial.equals(Material.TRAPPED_CHEST)) return true;
        return evalMaterial.equals(Material.ENDER_CHEST);
    }






}


