package dev.mruniverse.guardianskywars.utils;

import dev.mruniverse.guardianskywars.GuardianSkyWars;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtils {
    private final GuardianSkyWars main;
    public LocationUtils(GuardianSkyWars main) {
        this.main = main;
    }
    public String getStringFromLocation(Location location) {
        try {
            World currentWorld = location.getWorld();
            String worldName = "world";
            if(currentWorld != null) worldName = location.getWorld().getName();
            return worldName + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch();
        }catch (Throwable throwable) {
            main.getLogs().error("Can't get String from location " + location.toString());
            main.getLogs().error(throwable);
        }
        return null;
    }
    public Location getLocationFromString(String location) {
        if(!location.equalsIgnoreCase("notSet")) {
            String[] loc = location.split(",");
            World w = Bukkit.getWorld(loc[0]);
            if(w != null) {
                double x = Double.parseDouble(loc[1]);
                double y = Double.parseDouble(loc[2]);
                double z = Double.parseDouble(loc[3]);
                float yaw = Float.parseFloat(loc[4]);
                float pitch = Float.parseFloat(loc[5]);
                return new Location(w, x, y, z, yaw, pitch);
            }
            main.getLogs().error("Can't get world named: " + loc[0]);
            return null;
        }
        return null;
    }
}
