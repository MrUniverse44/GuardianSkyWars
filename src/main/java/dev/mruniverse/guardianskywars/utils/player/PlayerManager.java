package dev.mruniverse.guardianskywars.utils.player;

import dev.mruniverse.guardianskywars.GuardianSkyWars;
import dev.mruniverse.guardianskywars.enums.GuardianBoard;
import dev.mruniverse.guardianskywars.enums.GuardianFiles;
import dev.mruniverse.guardianskywars.games.Game;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@SuppressWarnings("unused")
public class PlayerManager {
    private PlayerStatus playerStatus;
    private GuardianBoard guardianBoard;
    private final Player player;
    private final GuardianSkyWars plugin;
    private boolean pointStatus;
    private Location lastCheckpoint;
    private Game currentGame;
    private int leaveDelay;
    private int kills;
    private int coins;
    private int deaths;
    private int wins;
    private String selectedKit;
    private String kits;

    public PlayerManager(Player p, GuardianSkyWars main) {
        plugin = main;
        player = p;
        leaveDelay = 0;
        pointStatus = false;
        currentGame = null;
        if (plugin.getStorage().getControl(GuardianFiles.MYSQL).getBoolean("mysql.enabled")) {
            String table = plugin.getStorage().getControl(GuardianFiles.MYSQL).getString("mysql.table");
            if (!plugin.getData().isRegistered(table, "Player", getID())) {
                List<String> values = new ArrayList<>();
                values.add("Player-" + getID());
                values.add("Coins-0");
                values.add("Kits-K" + plugin.getStorage().getControl(GuardianFiles.SETTINGS).getString("settings.defaultKitID"));
                values.add("SelectedKit-NONE");
                values.add("Kills-0");
                values.add("Deaths-0");
                values.add("Score-0");
                values.add("Wins-0");
                plugin.getData().register(table, values);
                kills = 0;
                coins = 0;
                deaths = 0;
                wins = 0;
                selectedKit = "NONE";
                kits = "Kits-K" + plugin.getStorage().getControl(GuardianFiles.SETTINGS).getString("settings.defaultKitID");
            } else {
                kills = plugin.getData().getInt(table, "Kills", "Player", player.getUniqueId().toString());
                coins = plugin.getData().getInt(table, "Coins", "Player", player.getUniqueId().toString());
                deaths = plugin.getData().getInt(table, "Deaths", "Player", player.getUniqueId().toString());
                wins = plugin.getData().getInt(table, "Wins", "Player", player.getUniqueId().toString());
                selectedKit = plugin.getData().getString(table,"SelectedKit","Player",getID());
                kits = plugin.getData().getString(table,"Kits","Player",getID());
            }
        }

    }
    //public KitMenu getKitMenu() { return playerKits; }
    public void setLeaveDelay(int delay) { leaveDelay = delay; }
    public void setStatus(PlayerStatus status) {
        playerStatus = status;
    }
    public void setBoard(GuardianBoard board) {
        guardianBoard = board;
    }
    public void setGame(Game game) { currentGame = game; }
    public GuardianBoard getBoard() {
        return guardianBoard;
    }
    public PlayerStatus getStatus() {
        return playerStatus;
    }
    public String getName() {
        return player.getName();
    }
    public Game getGame() { return currentGame; }
    public Player getPlayer() {
        return player;
    }
    public int getLeaveDelay() { return leaveDelay; }
    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void addWins() {
        setWins(getWins() + 1);
    }

    public boolean getPointStatus() {
        return pointStatus;
    }

    public Location getLastCheckpoint() {
        return lastCheckpoint;
    }

    public void setPointStatus(boolean bol) {
        pointStatus = bol;
    }

    public void setLastCheckpoint(Location location) {
        lastCheckpoint = location;
    }

    public int getCoins() {
        return coins;
    }
    public void updateCoins(int addOrRemove) {
        setCoins(getCoins() + addOrRemove);
    }
    public void setCoins(int coinCounter) {
        this.coins = coinCounter;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setSelectedKit(String kitID) {
        this.selectedKit = kitID;
    }

    public String getSelectedKit() {
        return selectedKit;
    }

    public void addKit(String kitID) {
        String lastResult = kits;
        if(!lastResult.equalsIgnoreCase("")) {
            kits = lastResult + ",K" + kitID;
        } else {
            kits = "K"+kitID;
        }
    }

    public List<String> getKits() {
        String kitsBuy = kits;
        kitsBuy = kitsBuy.replace(" ","");
        String[] kitShortList = kitsBuy.split(",");
        return Arrays.asList(kitShortList);
    }

    public void addKills() {
        setKills(getKills() + 1);
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public String getID() {
        return player.getUniqueId().toString().replace("-","");
    }

    public void addDeaths() {
        registerDefault();
        setDeaths(getDeaths() + 1);
    }


    public void registerDefault() {
        String table = plugin.getStorage().getControl(GuardianFiles.MYSQL).getString("mysql.table");
        if (!plugin.getData().isRegistered(table, "Player", getID())) {
            List<String> values = new ArrayList<>();
            values.add("Player-" + getID());
            values.add("Coins-0");
            values.add("Kits-K" + plugin.getStorage().getControl(GuardianFiles.SETTINGS).getString("settings.defaultKitID"));
            values.add("SelectedKit-NONE");
            values.add("Kills-0");
            values.add("Deaths-0");
            values.add("Score-0");
            values.add("Wins-0");
            plugin.getData().register(table, values);
        }
    }
}


