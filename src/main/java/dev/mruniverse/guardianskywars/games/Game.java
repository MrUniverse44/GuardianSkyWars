package dev.mruniverse.guardianskywars.games;

import dev.mruniverse.guardianskywars.GuardianSkyWars;
import dev.mruniverse.guardianskywars.enums.GuardianFiles;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Game {
    private final GuardianSkyWars main;
    private final String configName;
    private final List<Location> cageLocations;
    private String gameName;

    public Game(GuardianSkyWars main,String configName,String gameName) {
        this.main = main;
        this.configName = configName;
        this.gameName = gameName;
        this.cageLocations = new ArrayList<>();
        loadCages();
    }

    public void loadCages() {
        FileConfiguration gameFile = main.getStorage().getControl(GuardianFiles.GAMES);
        for(String locInString : gameFile.getStringList("games." + configName + ".locations.cages")) {
            Location location = main.getLocationUtils().getLocationFromString(locInString);
            if(location != null) cageLocations.add(location);
        }
    }

    public void setGameName(String gameName) { this.gameName = gameName; }

    public void join(Player player) {

    }

    public void quit(Player player) {

    }

    public void addSpectator(Player player) {

    }

    public void start(GameCountType countType) {

    }

    public List<Location> getCageLocations() { return cageLocations; }

}
