package dev.mruniverse.guardianskywars.commands.admin;

import dev.mruniverse.guardianlib.core.utils.Utils;
import dev.mruniverse.guardianskywars.GuardianSkyWars;
import dev.mruniverse.guardianskywars.worlds.WorldController;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WorldsCommand {
    private final GuardianSkyWars main;
    private WorldController wController;
    private final String cmdPrefix;
    private final List<String> worlds = new ArrayList<>();
    public WorldsCommand(GuardianSkyWars main,String command) {
        this.main = main;
        cmdPrefix = "&e/" + command;
        this.wController = main.getWorldController();
        if(wController == null) wController = new WorldController(main);
    }

    public void usage(CommandSender sender, String[] arguments) {
        Utils utils = main.getLib().getUtils();
        if(arguments.length == 0 || arguments[0].equalsIgnoreCase("help")) {
            utils.sendMessage(sender,"&b------------ &aGuardian SkyWars &b------------");
            utils.sendMessage(sender,"&6Admin - Worlds Commands:");
            utils.sendMessage(sender,cmdPrefix + " admin worlds create (name) [schematic] &e- &fCreate world");
            utils.sendMessage(sender,cmdPrefix + " admin worlds load (name) &e- &fLoad World");
            utils.sendMessage(sender,cmdPrefix + " admin worlds unload (name) &e- &fUnload World");
            utils.sendMessage(sender,cmdPrefix + " admin worlds list &e- &fList of worlds loaded with this plugin");
            utils.sendMessage(sender,cmdPrefix + " admin worlds tp (name) &e- &fTeleport to a specific world");
            utils.sendMessage(sender,cmdPrefix + " admin worlds clone (name) [cloned world name] &e- &fClone a world");
            utils.sendMessage(sender,cmdPrefix + " admin worlds save (name) &e- &fSave changes in a world");
            utils.sendMessage(sender,"&b------------ &a(Page 2&l/3&a) &b------------");
            return;
        }
        if(arguments[0].equalsIgnoreCase("load")) {
            if(arguments.length == 2) {
                wController.loadWorld(arguments[1]);
                worlds.add(arguments[1]);
                utils.sendMessage(sender, "&aWorld &b" + arguments[1] + "&a loaded!");
                return;
            }
            enoughtArguments(sender);
            return;
        }
        if(arguments[0].equalsIgnoreCase("unload")) {
            if(arguments.length == 2) {
                wController.unloadWorld(arguments[1]);
                worlds.remove(arguments[1]);
                utils.sendMessage(sender, "&aWorld &b" + arguments[1] + "&a unloaded!");
                return;
            }
            enoughtArguments(sender);
            return;
        }
        if(arguments[0].equalsIgnoreCase("save")) {
            if(arguments.length == 2) {
                wController.saveWorld(arguments[1]);
                utils.sendMessage(sender,"&aWorld &b" + arguments[1] + "&a saved!");
                return;
            }
            enoughtArguments(sender);
            return;

        }
        if(arguments[0].equalsIgnoreCase("tp")) {
            if(arguments.length == 2) {
                World world = Bukkit.getWorld(arguments[1]);
                if(world == null) return;
                if(sender instanceof Player) {
                    Player player = (Player) sender;
                    player.teleport(world.getSpawnLocation());
                    utils.sendMessage(player,"&aYou has been teleported to world &b" + arguments[1]);
                    return;
                }
                utils.sendMessage(sender,"&cThis command is only for users!");
            }
            enoughtArguments(sender);
            return;
        }
        if(arguments[0].equalsIgnoreCase("list")) {
            if(worlds.size() > 0) {
                for(String world : worlds) {
                    utils.sendMessage(sender,"&b- &a" + world);
                }
                utils.sendMessage(sender,"&aCurrently the plugin has &b" + worlds.size() + "&a worlds loaded!");
                return;
            }
            utils.sendMessage(sender,"&cNo worlds loaded by the plugin ");
            return;
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
                            World world = Bukkit.getWorld(arguments[1]);
                            if(world != null) world.setSpawnLocation(new Location(world, 0, 71, 0));
                        } else {
                            utils.sendMessage(sender,"&cThis schematic doesn't exists, the world was generated but the schematic wasn't pasted!");
                            utils.sendMessage(sender,"&cYou need paste the schematic manually");
                        }
                    }catch (Throwable ignored) {
                        utils.sendMessage(sender,"&aCan't paste schematic &b" + arguments[2] + " &ain this world");
                    }
                }
                worlds.add(arguments[1]);
                utils.sendMessage(sender,"&aWorld &b" + arguments[1] + "&a created!");
                return;
            }
            enoughtArguments(sender);
            return;
        }
        if(arguments[0].equalsIgnoreCase("clone")) {
            if(arguments.length == 3) {
                wController.cloneWorld(arguments[1],arguments[2]);
                worlds.add(arguments[2]);
                utils.sendMessage(sender,"&aWorld &b" + arguments[1] + "&a cloned to &b" + arguments[2] + "&a!");
                return;
            }
            enoughtArguments(sender);
            return;
        }
        utils.sendMessage(sender,"&cThis command doesn't exists! use &e/gsw admin worlds help &cto to find a correct syntax of the command.");
    }

    public void enoughtArguments(CommandSender sender) {
        main.getLib().getUtils().sendMessage(sender,"&cEnought Arguments! use &e/gsw admin worlds help &cto see correct usage!");
    }
}
