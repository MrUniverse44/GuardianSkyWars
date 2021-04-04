package dev.mruniverse.guardianskywars.files;

import dev.mruniverse.guardianskywars.GuardianSkyWars;
import dev.mruniverse.guardianskywars.enums.GuardianFiles;
import dev.mruniverse.guardianskywars.enums.SaveMode;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class SQL {
    public HashMap<String, Integer> kills = new HashMap<>();
    public HashMap<String, Integer> deaths = new HashMap<>();
    public HashMap<String, Integer> wins = new HashMap<>();
    public HashMap<String, Integer> score = new HashMap<>();
    public HashMap<String, Integer> coins = new HashMap<>();
    public HashMap<String, Integer> levelXP = new HashMap<>();
    public HashMap<String, String> kits = new HashMap<>();
    public HashMap<String, String> selectedKits = new HashMap<>();
    private final GuardianSkyWars plugin;
    public SQL(GuardianSkyWars main) {
        plugin = main;
    }

    public void putData() {
        if (kills.size() != 0)
            for (Map.Entry<String, Integer> k : kills.entrySet())
                plugin.getStorage().getControl(GuardianFiles.DATA).set("Kills." + k.getKey(), k.getValue());
        if (deaths.size() != 0)
            for (Map.Entry<String, Integer> k : deaths.entrySet())
                plugin.getStorage().getControl(GuardianFiles.DATA).set("Deaths." + k.getKey(), k.getValue());
        if (wins.size() != 0)
            for (Map.Entry<String, Integer> k : wins.entrySet())
                plugin.getStorage().getControl(GuardianFiles.DATA).set("Wins." + k.getKey(), k.getValue());
        if (score.size() != 0)
            for (Map.Entry<String, Integer> k : score.entrySet())
                plugin.getStorage().getControl(GuardianFiles.DATA).set("Score." + k.getKey(), k.getValue());
        if (coins.size() != 0)
            for (Map.Entry<String, Integer> k : coins.entrySet())
                plugin.getStorage().getControl(GuardianFiles.DATA).set("Coins." + k.getKey(), k.getValue());
        if (levelXP.size() != 0)
            for (Map.Entry<String, Integer> k : levelXP.entrySet())
                plugin.getStorage().getControl(GuardianFiles.DATA).set("LevelXP." + k.getKey(), k.getValue());
        if (kits.size() != 0)
            for (Map.Entry<String, String> k : kits.entrySet())
                plugin.getStorage().getControl(GuardianFiles.DATA).set("UserKits." + k.getKey(), k.getValue());
        if (selectedKits.size() != 0)
            for (Map.Entry<String, String> k : selectedKits.entrySet())
                plugin.getStorage().getControl(GuardianFiles.DATA).set("selectedKits." + k.getKey(), k.getValue());
        plugin.getStorage().save(SaveMode.DATA);
    }

    public void loadData() {
        if (plugin.getStorage().getControl(GuardianFiles.DATA).contains("Kills"))
            for (String str : plugin.getStorage().getContent(GuardianFiles.DATA,"Kills",true)) {
                int p = plugin.getStorage().getControl(GuardianFiles.DATA).getInt("Kills." + str);
                kills.put(str.replace("Kills.", ""), p);
            }
        if (plugin.getStorage().getControl(GuardianFiles.DATA).contains("Deaths"))
            for (String str : plugin.getStorage().getContent(GuardianFiles.DATA,"Deaths",true)) {
                int p = plugin.getStorage().getControl(GuardianFiles.DATA).getInt("Deaths." + str);
                deaths.put(str.replace("Deaths.", ""), p);
            }
        if (plugin.getStorage().getControl(GuardianFiles.DATA).contains("UserKits"))
            for (String str : plugin.getStorage().getContent(GuardianFiles.DATA,"UserKits",true)) {
                String p = plugin.getStorage().getControl(GuardianFiles.DATA).getString("UserKits." + str);
                kits.put(str.replace("UserKits.", ""), p);
            }
        if (plugin.getStorage().getControl(GuardianFiles.DATA).contains("selectedKits"))
            for (String str : plugin.getStorage().getContent(GuardianFiles.DATA,"selectedKits",true)) {
                String p = plugin.getStorage().getControl(GuardianFiles.DATA).getString("selectedKits." + str);
                selectedKits.put(str.replace("selectedKits.", ""), p);
            }
        if (plugin.getStorage().getControl(GuardianFiles.DATA).contains("Wins"))
            for (String str : plugin.getStorage().getContent(GuardianFiles.DATA,"Wins",true)) {
                int p = plugin.getStorage().getControl(GuardianFiles.DATA).getInt("Wins." + str);
                wins.put(str.replace("Wins.", ""), p);
            }
        if (plugin.getStorage().getControl(GuardianFiles.DATA).contains("Score"))
            for (String str : plugin.getStorage().getContent(GuardianFiles.DATA,"Score",true)) {
                int p = plugin.getStorage().getControl(GuardianFiles.DATA).getInt("Score." + str);
                score.put(str.replace("Score.", ""), p);
            }
        if (plugin.getStorage().getControl(GuardianFiles.DATA).contains("LevelXP"))
            for (String str : plugin.getStorage().getContent(GuardianFiles.DATA,"LevelXP",true)) {
                int p = plugin.getStorage().getControl(GuardianFiles.DATA).getInt("LevelXP." + str);
                levelXP.put(str.replace("LevelXP.", ""), p);
            }
        if (plugin.getStorage().getControl(GuardianFiles.DATA).contains("Coins"))
            for (String str : plugin.getStorage().getContent(GuardianFiles.DATA,"Coins",true)) {
                int p = plugin.getStorage().getControl(GuardianFiles.DATA).getInt("Coins." + str);
                levelXP.put(str.replace("Coins.", ""), p);
            }
    }

    public void createPlayer(Player player) {
        kills.put(player.getUniqueId().toString(), 0);
        deaths.put(player.getUniqueId().toString(), 0);
        wins.put(player.getUniqueId().toString(), 0);
        coins.put(player.getUniqueId().toString(), 0);
        levelXP.put(player.getUniqueId().toString(), 0);
        kits.put(player.getUniqueId().toString(),plugin.getStorage().getControl(GuardianFiles.SETTINGS).getString("settings.defaultKitID"));
        selectedKits.put(player.getUniqueId().toString(),"NONE");
    }
}

