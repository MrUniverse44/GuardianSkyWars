package dev.mruniverse.guardianskywars.worlds;

import dev.mruniverse.guardianskywars.GuardianSkyWars;
import dev.mruniverse.guardianskywars.enums.GuardianWorld;
import dev.mruniverse.guardianskywars.interfaces.GameWorld;
import dev.mruniverse.guardianskywars.worlds.chunks.EmptyChunkGenerator;
import org.bukkit.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DefaultWorld implements GameWorld {
    @Override
    public void createWorld(String worldName) {
        WorldCreator worldCreator = new WorldCreator(worldName);
        worldCreator.generator(new EmptyChunkGenerator());
        worldCreator.createWorld();
        Location location = new Location(Bukkit.getWorld(worldName), 0, 1, 0);
        location.getBlock().setType(Material.BEDROCK);
    }

    @Override
    public void loadWorld(String worldName) {
        WorldCreator worldCreator = new WorldCreator(worldName);
        worldCreator.generator(new EmptyChunkGenerator());
        worldCreator.createWorld();
    }

    @Override
    public void saveWorld(String worldName) {
        World world = Bukkit.getWorld(worldName);
        if(world == null) return;
        world.save();
    }

    @Override
    public void unloadWorld(String worldName) {
        World world = Bukkit.getWorld(worldName);
        if(world == null) return;
        Bukkit.unloadWorld(worldName,false);
    }

    @Override
    public void cloneWorld(String worldName,String newWorld) {
        unloadWorld(worldName);
        File save = GuardianSkyWars.getInstance().getStorage().getWorldsFolder(GuardianWorld.NORMAL);
        File worldFile = new File(save,worldName);
        File NewWorldFile = new File(save,newWorld);
        if(!worldFile.exists()) {
            GuardianSkyWars.getInstance().getLogs().error("Can't clone world " + worldName + " because this world doesn't exists");
            return;
        }
        copyFileStructure(worldFile,NewWorldFile);
    }

    private void copyFileStructure(File source, File target){
        try {
            ArrayList<String> ignore = new ArrayList<>(Arrays.asList("uid.dat", "session.lock"));
            if(!ignore.contains(source.getName())) {
                if(source.isDirectory()) {
                    if(!target.exists())
                        if (!target.mkdirs())
                            throw new IOException("Couldn't create world directory!");
                    String[] files = source.list();
                    if (files != null) {
                        for (String file : files) {
                            File srcFile = new File(source, file);
                            File destFile = new File(target, file);
                            copyFileStructure(srcFile, destFile);
                        }
                    }
                } else {
                    InputStream in = new FileInputStream(source);
                    OutputStream out = new FileOutputStream(target);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0)
                        out.write(buffer, 0, length);
                    in.close();
                    out.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
