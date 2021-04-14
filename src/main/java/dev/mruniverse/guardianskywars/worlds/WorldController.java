package dev.mruniverse.guardianskywars.worlds;

import dev.mruniverse.guardianskywars.GuardianSkyWars;
import dev.mruniverse.guardianskywars.enums.GuardianFiles;
import dev.mruniverse.guardianskywars.interfaces.GameWorld;
@SuppressWarnings("unused")
public class WorldController {
    private final GuardianSkyWars plugin;
    private final GameWorld gameWorld;
    public WorldController(GuardianSkyWars plugin) {
        this.plugin = plugin;
        if(plugin.getStorage().getControl(GuardianFiles.SETTINGS).getBoolean("settings.hooks.SlimeWorldManager") && plugin.getServer().getPluginManager().getPlugin("SlimeWorldManager") != null) {
            gameWorld = new SlimeWorldGeneration();
            return;
        }
        gameWorld = new DefaultWorld();
    }

    public void createGameWorld(String worldName) {
        plugin.getLogs().debug("Creating world " + worldName);
        gameWorld.createWorld(worldName);
    }

    public void cloneWorld(String newWorld,String worldToClone) {
        plugin.getLogs().debug("Cloning world world " + worldToClone + " to world " + newWorld);
        gameWorld.cloneWorld(worldToClone,newWorld);
    }

    public void loadWorld(String worldName) {
        plugin.getLogs().debug("Loading world " + worldName);
        gameWorld.loadWorld(worldName);
    }

    public void unloadWorld(String worldName) {
        plugin.getLogs().debug("Unloading world " + worldName);
        gameWorld.unloadWorld(worldName);
    }

    public void saveWorld(String worldName) {
        plugin.getLogs().debug("Saving world " + worldName);
        gameWorld.saveWorld(worldName);
    }

}
