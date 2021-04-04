package dev.mruniverse.guardianskywars.commands;

import dev.mruniverse.guardianlib.core.enums.BorderColor;
import dev.mruniverse.guardianlib.core.holograms.GlobalHologram;
import dev.mruniverse.guardianlib.core.holograms.PersonalHologram;
import dev.mruniverse.guardianlib.core.utils.Utils;
import dev.mruniverse.guardianskywars.GuardianSkyWars;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MainCommand implements CommandExecutor {

    private final GuardianSkyWars plugin;
    public GlobalHologram superHolograms;
    public final HashMap<Player, PersonalHologram> superPersonalHologramsHashMap = new HashMap<>();

    public MainCommand(GuardianSkyWars main, String command) {
        this.plugin = main;
        Objects.requireNonNull(main.getCommand(command)).setExecutor(this);
    }


    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            Utils utils = plugin.getLib().getUtils();
            if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
                if (sender instanceof Player) {
                    Player player = (Player)sender;
                    utils.sendCenteredMessage(player,"&b&lGUARDIAN LIB TEST #1");
                    utils.sendCenteredMessage(player," ");
                    utils.sendCenteredMessage(player,"&f/gl holo set (global-personal)");
                    utils.sendCenteredMessage(player,"&f/gl holo delete (global-personal)");
                    utils.sendCenteredMessage(player,"&f/gl sound play (soundName)");
                    utils.sendCenteredMessage(player,"&f/gl worldBorder (borderSize)");
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("worldBorder")) {
                if(args.length == 1) {
                    plugin.getLib().getUtils().sendCenteredMessage((Player)sender,"&f/gl worldBorder (borderSize)");
                    return true;
                }
                Player player = (Player)sender;
                utils.setPlayerWorldBorder(player,player.getLocation(),Integer.parseInt(args[1]), BorderColor.DEFAULT);
                utils.sendCenteredMessage(player,"&aDone!");
            }
            if (args[0].equalsIgnoreCase("holo")) {
                if(args.length == 1) {
                    utils.sendMessage(sender,"&f/gl holo set (global-personal)");
                    utils.sendMessage(sender,"&f/gl holo delete (global-personal)");
                }
                if(args[1].equalsIgnoreCase("set")) {
                    if(args.length == 2) {
                        utils.sendMessage(sender,"&f/gl holo set (global-personal)");
                    }
                    if(args[2].equalsIgnoreCase("global")) {
                        Player player = (Player)sender;
                        List<String> lines = new ArrayList<>();
                        lines.add(ChatColor.translateAlternateColorCodes('&',"&b&lGLOBAL HOLO"));
                        lines.add(ChatColor.translateAlternateColorCodes('&',"&fThis hologram appear for all players"));
                        superHolograms = new GlobalHologram(player.getLocation(),lines);
                        superHolograms.spawn();
                        utils.sendCenteredMessage(player,"&aDone!");
                    }
                    if(args[2].equalsIgnoreCase("personal")) {
                        Player player = (Player)sender;
                        List<String> lines = new ArrayList<>();
                        lines.add(ChatColor.translateAlternateColorCodes('&',"&b&lPERSONAL HOLO"));
                        lines.add(ChatColor.translateAlternateColorCodes('&',"&f&oThis hologram appear only for you " + player.getName()));
                        PersonalHologram superPersonalHologram = new PersonalHologram(plugin.getLib(),player,player.getLocation(),"holo1",lines);
                        superPersonalHologram.spawn();
                        superPersonalHologramsHashMap.put(player,superPersonalHologram);
                        utils.sendCenteredMessage(player,"&aDone!");
                    }
                }
                if(args[1].equalsIgnoreCase("delete")) {
                    if(args.length == 2) {
                        utils.sendMessage(sender,"&f/gl holo delete (normal-normalP-Super-SuperP)");
                    }
                    if(args[2].equalsIgnoreCase("global")) {
                        superHolograms.remove();
                        utils.sendCenteredMessage((Player)sender,"&aDone!");
                    }
                    if(args[2].equalsIgnoreCase("personal")) {
                        Player player = (Player)sender;
                        superPersonalHologramsHashMap.get(player).delete();
                        superPersonalHologramsHashMap.remove(player);
                        utils.sendCenteredMessage(player,"&aDone!");
                    }
                }
                return true;
            }
            return true;
        } catch (Throwable throwable) {
            plugin.getLogs().error(throwable);
        }
        return true;
    }
}