package dev.mruniverse.guardianskywars.worlds;

import dev.mruniverse.guardianskywars.GuardianSkyWars;
import dev.mruniverse.guardianskywars.enums.GuardianFiles;
import dev.mruniverse.guardianskywars.worlds.chunks.EmptyChunkGenerator;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class PluginWorlds {
    private final GuardianSkyWars plugin;
    private List<String> worlds;
    public PluginWorlds(GuardianSkyWars plugin) {
        this.plugin = plugin;
        worlds = plugin.getStorage().getContent(GuardianFiles.PLUGIN_WORLDS,"worlds.list",false);
    }
    @SuppressWarnings("unused")
    public List<String> getWorldsNames() {
        if(worlds == null) worlds = new ArrayList<>();
        return worlds;
    }
    public void loadWorlds() {
        FileConfiguration worldConfiguration = plugin.getStorage().getControl(GuardianFiles.PLUGIN_WORLDS);
        for(String worldName : worlds) {
            WorldCreator worldCreator = new WorldCreator(worldName);
            String path = "worlds.list." + worldName + ".";
            String environment = worldConfiguration.getString(path + "environment");
            String difficulty = worldConfiguration.getString(path + "difficulty");
            String spawnLocation = worldConfiguration.getString(path + "spawnLocation");
            if(difficulty == null) difficulty = "PEACEFUL";
            if(spawnLocation == null) {
                spawnLocation = worldName + ",0,60,0,0,0";
            } else {
                spawnLocation = worldName + "," + spawnLocation;
            }
            if(environment == null) environment = "EMPTY";
            if(environment.equalsIgnoreCase("EMPTY")) {
                worldCreator.generator(new EmptyChunkGenerator());
            } else {
                worldCreator.environment(World.Environment.valueOf(environment.toUpperCase()));
            }
            World world = worldCreator.createWorld();
            if(world != null) {
                world.setDifficulty(Difficulty.valueOf(difficulty.toUpperCase()));
                Location location = plugin.getLocationUtils().getLocationFromString(spawnLocation);
                if(location != null) world.setSpawnLocation(location);
                world.setPVP(worldConfiguration.getBoolean(path + "pvp"));
                if(worldConfiguration.getBoolean(path + "monsters")) world.setMonsterSpawnLimit(0);
                if(worldConfiguration.getBoolean(path + "animals")) {
                    world.setAnimalSpawnLimit(0);
                    world.setAmbientSpawnLimit(0);
                }
                world.setAutoSave(worldConfiguration.getBoolean(path + "autoSave"));
            }
            plugin.getLogs().debug("World " + worldName + " loaded!");
        }
    }
    @SuppressWarnings("unused")
    public void unloadWorlds() {
        FileConfiguration worldConfiguration = plugin.getStorage().getControl(GuardianFiles.PLUGIN_WORLDS);
        for(String worldName : worlds) {
            World world = Bukkit.getWorld(worldName);
            if(world == null) return;
            Bukkit.unloadWorld(worldName,worldConfiguration.getBoolean("worlds.list." + worldName + ".autoSave"));
            plugin.getLogs().debug("World " + worldName + " unloaded!");
        }
    }
}
