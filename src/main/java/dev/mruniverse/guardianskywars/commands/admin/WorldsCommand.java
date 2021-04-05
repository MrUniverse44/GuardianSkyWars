package dev.mruniverse.guardianskywars.commands.admin;

import dev.mruniverse.guardianlib.core.utils.Utils;
import dev.mruniverse.guardianskywars.GuardianSkyWars;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

public class WorldsCommand {
    private final GuardianSkyWars main;
    private final String command;
    public WorldsCommand(GuardianSkyWars main,String command) {
        this.main = main;
        this.command = command;
    }

    public void usage(CommandSender sender, String[] arguments) {
        Utils utils = main.getLib().getUtils();
        if(arguments[0].equalsIgnoreCase("load")) {
            if(arguments.length == 2) {
                World world = Bukkit.getWorld(arguments[1]);
                if(world == null) {
                    main.getLib().getWorldManager().loadWorld(arguments[1]);
                    utils.sendMessage(sender, "&aWorld &b" + arguments[1] + "&a loaded!");
                    return;
                }
                utils.sendMessage(sender,"&cThis world is already loaded.");
                return;
            }
            return;
        }
        if(arguments[0].equalsIgnoreCase("unload")) {
            if(arguments.length == 2) {
                World world = Bukkit.getWorld(arguments[1]);
                if(world != null) {
                    Bukkit.unloadWorld(arguments[1], false);
                    utils.sendMessage(sender, "&aWorld &b" + arguments[1] + "&a unloaded!");
                    return;
                }
                utils.sendMessage(sender,"&cThis world isn't loaded.");
                return;
            }
        }
        if(arguments[0].equalsIgnoreCase("save")) {
            if(arguments.length == 2) {
                World world = Bukkit.getWorld(arguments[1]);
                if(world != null) {
                    world.save();
                    utils.sendMessage(sender,"&aWorld &b" + arguments[1] + "&a saved!");
                    return;
                }
                utils.sendMessage(sender,"&cThis world isn't loaded.");
            }
        }
    }
}
