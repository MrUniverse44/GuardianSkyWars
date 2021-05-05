package dev.mruniverse.guardianskywars.enums;

import dev.mruniverse.guardianskywars.GuardianSkyWars;
import dev.mruniverse.guardianskywars.enums.GuardianFiles;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public enum GameStatus {
    WAITING,
    STARTING,
    PLAYING,
    RESTARTING;

    public String getStatus() {
        FileConfiguration fileConfiguration = GuardianSkyWars.getInstance().getStorage().getControl(GuardianFiles.SETTINGS);
        String status = this.toString().toLowerCase();
        return color(fileConfiguration.getString("settings.status." + status));
    }

    public String getName() {
        FileConfiguration fileConfiguration = GuardianSkyWars.getInstance().getStorage().getControl(GuardianFiles.SETTINGS);
        String status = this.toString().toLowerCase();
        return color(fileConfiguration.getString("settings.names." + status));
    }

    private static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&',message);
    }
}
