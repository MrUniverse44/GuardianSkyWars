package dev.mruniverse.guardianskywars.commands.admin;

import dev.mruniverse.guardianlib.core.utils.Utils;
import dev.mruniverse.guardianskywars.GuardianSkyWars;
import dev.mruniverse.guardianskywars.worlds.WorldController;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import java.io.File;

public class WorldsCommand {
    private final GuardianSkyWars main;
    private final WorldController wController;
    public WorldsCommand(GuardianSkyWars main) {
        this.main = main;
        this.wController = main.getWorldController();
    }

    public void usage(CommandSender sender, String[] arguments) {
        Utils utils = main.getLib().getUtils();
        if(arguments[0].equalsIgnoreCase("load")) {
            if(arguments.length == 2) {
                wController.loadWorld(arguments[1]);
                utils.sendMessage(sender, "&aWorld &b" + arguments[1] + "&a loaded!");
                return;
            }
            return;
        }
        if(arguments[0].equalsIgnoreCase("unload")) {
            if(arguments.length == 2) {
                wController.unloadWorld(arguments[1]);
                utils.sendMessage(sender, "&aWorld &b" + arguments[1] + "&a unloaded!");
                return;
            }
        }
        if(arguments[0].equalsIgnoreCase("save")) {
            if(arguments.length == 2) {
                wController.saveWorld(arguments[1]);
                utils.sendMessage(sender,"&aWorld &b" + arguments[1] + "&a saved!");
                return;
            }
        }
        if(arguments[0].equalsIgnoreCase("create")) {
            if(arguments.length == 2 || arguments.length == 3) {
                wController.createGameWorld(arguments[1]);
                if (arguments.length == 3) {
                    try {
                        File schematic = new File(main.getStorage().getSchematicFolder(), arguments[2]);
                        if(schematic.exists()) {
                            Location location = new Location(Bukkit.getWorld(arguments[1]), 0, 70, 0);
                            main.getLib().getSchematics().pasteSchematic(schematic, location);
                        } else {
                            utils.sendMessage(sender,"&cThis schematic doesn't exists, the world was generated but the schematic wasn't pasted!");
                            utils.sendMessage(sender,"&cYou need paste the schematic manually");
                        }
                    }catch (Throwable ignored) {
                        utils.sendMessage(sender,"&aCan't paste schematic &b" + arguments[2] + " &ain this world");
                    }
                }
                utils.sendMessage(sender,"&aWorld &b" + arguments[1] + "&a created!");
                return;
            }
        }
        if(arguments[0].equalsIgnoreCase("clone")) {
            if(arguments.length == 3) {
                wController.cloneWorld(arguments[1],arguments[2]);
                utils.sendMessage(sender,"&aWorld &b" + arguments[1] + "&a cloned to &b" + arguments[2] + "&a!");
            }
        }
    }
}
