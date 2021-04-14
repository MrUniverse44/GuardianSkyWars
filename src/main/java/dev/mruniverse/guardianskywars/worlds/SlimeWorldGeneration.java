package dev.mruniverse.guardianskywars.worlds;

import com.grinderwolf.swm.api.SlimePlugin;
import com.grinderwolf.swm.api.loaders.SlimeLoader;
import com.grinderwolf.swm.api.world.SlimeWorld;
import com.grinderwolf.swm.api.world.properties.SlimeProperties;
import com.grinderwolf.swm.api.world.properties.SlimePropertyMap;
import dev.mruniverse.guardianskywars.GuardianSkyWars;
import dev.mruniverse.guardianskywars.enums.WorldEnum;
import dev.mruniverse.guardianskywars.interfaces.GameWorld;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class SlimeWorldGeneration implements GameWorld {
    private final SlimePlugin slimePlugin;
    private SlimeLoader slimeLoader;
    public SlimeWorldGeneration() {
        slimePlugin = (SlimePlugin) Bukkit.getPluginManager().getPlugin("SlimeWorldManager");
        if(slimePlugin == null) return;
        slimeLoader = slimePlugin.getLoader(WorldEnum.SLIME.getLoader());
    }
    @Override
    public void createWorld(String worldName) {
        World world = Bukkit.getWorld(worldName);
        if(world != null) return;
        try {
            SlimePropertyMap slimePropertyMap = new SlimePropertyMap();
            slimePropertyMap.setInt(SlimeProperties.SPAWN_X, 0);
            slimePropertyMap.setInt(SlimeProperties.SPAWN_Y, 70);
            slimePropertyMap.setInt(SlimeProperties.SPAWN_Z, 0);
            slimePropertyMap.setBoolean(SlimeProperties.ALLOW_ANIMALS, false);
            slimePropertyMap.setBoolean(SlimeProperties.ALLOW_MONSTERS, false);
            slimePropertyMap.setBoolean(SlimeProperties.PVP, true);
            SlimeWorld slimeWorld = slimePlugin.createEmptyWorld(slimeLoader, worldName, false, slimePropertyMap);
            slimePlugin.generateWorld(slimeWorld);
            Location location = new Location(Bukkit.getWorld(worldName), 0, 64, 0);
            location.getBlock().setType(Material.BEDROCK);
        } catch (Throwable throwable) {
            GuardianSkyWars.getInstance().getLogs().error(throwable);
        }
    }

    @Override
    public void loadWorld(String worldName) {
        try {
            SlimePropertyMap properties = new SlimePropertyMap();
            properties.setString(SlimeProperties.DIFFICULTY, "normal");
            properties.setInt(SlimeProperties.SPAWN_X, 0);
            properties.setInt(SlimeProperties.SPAWN_Y, 70);
            properties.setInt(SlimeProperties.SPAWN_Z, 0);
            properties.setBoolean(SlimeProperties.ALLOW_ANIMALS, false);
            properties.setBoolean(SlimeProperties.ALLOW_MONSTERS, false);
            properties.setBoolean(SlimeProperties.PVP, true);
            SlimeWorld world = slimePlugin.loadWorld(slimeLoader, worldName, false,properties);
            slimePlugin.generateWorld(world);
        } catch (Throwable ignored) {
        }
    }

    @Override
    public void saveWorld(String ignored) {

    }

    @Override
    public void unloadWorld(String worldName) {
        World world = Bukkit.getWorld(worldName);
        if(world == null) return;
        Bukkit.unloadWorld(world,false);
    }

    @Override
    public void cloneWorld(String worldName,String newWorld) {
        SlimeLoader slimeLoader = slimePlugin.getLoader(newWorld);
        SlimePropertyMap properties = new SlimePropertyMap();
        properties.setString(SlimeProperties.DIFFICULTY, "normal");
        properties.setInt(SlimeProperties.SPAWN_X, 0);
        properties.setInt(SlimeProperties.SPAWN_Y, 70);
        properties.setInt(SlimeProperties.SPAWN_Z, 0);
        properties.setBoolean(SlimeProperties.ALLOW_ANIMALS, false);
        properties.setBoolean(SlimeProperties.ALLOW_MONSTERS, false);
        properties.setBoolean(SlimeProperties.PVP, true);
        try {
            SlimeWorld slimeWorld = slimePlugin.loadWorld(slimeLoader, worldName, true, properties).clone(worldName, slimeLoader);
            slimePlugin.generateWorld(slimeWorld);
        }catch (Throwable ignored) {}
    }
}
