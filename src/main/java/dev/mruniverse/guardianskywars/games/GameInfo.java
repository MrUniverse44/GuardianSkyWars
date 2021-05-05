package dev.mruniverse.guardianskywars.games;

import dev.mruniverse.guardianskywars.GuardianSkyWars;
import dev.mruniverse.guardianskywars.enums.GameStatus;
import dev.mruniverse.guardianskywars.enums.GuardianFiles;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;

//games:
//  Mexico:
//    enabled: true
//    duration: 500
//    max: 10
//    min: 2
//    time: 0
//    name: Name
//    mode: CLASSIC
//    locations:
//      center: <location>
//      waiting: <location>
//    cages: []
//    center-chests: []
//    super-chests: []
//    signs: []

@SuppressWarnings("unused")
public class GameInfo {

    private final GuardianSkyWars main;

    private final ArrayList<Location> cages = new ArrayList<>();
    private final ArrayList<Location> signs = new ArrayList<>();
    private final ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<Player> spectators = new ArrayList<>();
    private final ArrayList<Player> visitants = new ArrayList<>();

    private final String config;
    private final String path;

    private String name;

    private Location center;
    private Location waiting;

    private GameStatus status;

    private int last_runnable;
    private int duration;
    private int time;
    private int max;
    private int min;

    private boolean invincible = true;
    private boolean doubleCountPrevent = false;

    public GameInfo(GuardianSkyWars main, String config, String name) {
        this.main = main;
        this.config = config;
        this.path = "games." + config + ".";
        this.name = name;
        status = GameStatus.RESTARTING;
        loadGame();
        loadCagesLocations();
        loadStatus();
    }

    public void loadGame() {
        try {
            FileConfiguration gameFile = main.getStorage().getControl(GuardianFiles.GAMES);
            min = gameFile.getInt(path + "min");
            max = gameFile.getInt(path + "max");
            duration = gameFile.getInt(path + "duration");
            time = gameFile.getInt(path + "time");
            center = getLocation(gameFile.getString(path + "locations.center"));
            waiting = getLocation(gameFile.getString(path + "locations.waiting"));
        }catch (Throwable throwable) {
            main.getLogs().error("Can't load game " + config + "! check your game configuration or contact the developer!");
            main.getLogs().error(throwable);
        }
    }

    public void loadStatus() {
        status = GameStatus.WAITING;
    }

    public void restart() {
        invincible = true;
        doubleCountPrevent = false;
    }

    public void loadCagesLocations() {
        FileConfiguration gameFile = main.getStorage().getControl(GuardianFiles.GAMES);
        for(String locInString : gameFile.getStringList(path + "cages")) {
            Location location = main.getLocationUtils().getLocationFromString(locInString);
            if(location != null) cages.add(location);
        }
    }

    public void setGameName(String gameName) { this.name = gameName; }

    public void join(Player player) {

    }

    public void quit(Player player) {

    }

    public void addSpectator(Player player) {

    }

    public void setGameStatus(GameStatus gameStatus) { this.status = gameStatus; }


    private Location getLocation(String location) {
        if(location == null) {
            location = "notSet";
        }
        return main.getLocationUtils().getLocationFromString(location);
    }


    public String getConfigName() { return config; }

    public boolean isInvincible() { return invincible; }
    public boolean isDoubleCountPrevented() { return doubleCountPrevent; }

    public int getMax() { return max; }
    public int getMin() { return min; }
    public int getDuration() { return duration; }
    public int getTime() { return time; }

    public Location getWaiting() { return waiting; }
    public Location getCenter() { return center; }

    public GameStatus getStatus() { return status; }

    public ArrayList<Location> getCages() { return cages; }
    public ArrayList<Location> getSigns() { return signs; }

}
