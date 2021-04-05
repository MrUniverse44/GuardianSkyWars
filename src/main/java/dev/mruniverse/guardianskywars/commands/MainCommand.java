package dev.mruniverse.guardianskywars.commands;

import dev.mruniverse.guardianlib.core.utils.Utils;
import dev.mruniverse.guardianskywars.GuardianSkyWars;
import dev.mruniverse.guardianskywars.commands.admin.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;


public class MainCommand implements CommandExecutor {

    private final GuardianSkyWars plugin;
    private final String cmdPrefix;
    private final GameCommand gameCommand;
    private final NPCCommand npcCommand;
    private final HoloCommand holoCommand;
    private final CoinCommand coinCommand;
    private final WorldsCommand worldsCommand;

    public MainCommand(GuardianSkyWars main, String command) {
        this.plugin = main;
        this.cmdPrefix = "&6/" + command;
        gameCommand = new GameCommand(main,command);
        npcCommand = new NPCCommand(main,command);
        holoCommand = new HoloCommand(main,command);
        coinCommand = new CoinCommand(main,command);
        worldsCommand = new WorldsCommand(main,command);
        PluginCommand cmd = main.getCommand(command);
        if(cmd != null) {
            cmd.setExecutor(this);
        }
    }

    private boolean hasPermission(CommandSender sender,String permission,boolean sendMessage) {
        boolean check = true;
        if(sender instanceof Player) {
            Player player = (Player)sender;
            check = player.hasPermission(permission);
            if(sendMessage) {
                String permissionMsg = plugin.getStorage().getLang().getString("messages.lobby.others.no-perms");
                if (permissionMsg == null) permissionMsg = "&cYou need permission &7%permission% &cfor this action.";
                if (!check)
                    plugin.getLib().getUtils().sendMessage(player, permissionMsg.replace("%permission%", permission));
            }
        }
        return check;
    }


    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            Utils utils = plugin.getLib().getUtils();
            if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
                utils.sendMessage(sender,cmdPrefix + " join (name) &e- &7Join Arena");
                utils.sendMessage(sender,cmdPrefix + " leave &e- &7Leave CMD");
                if(hasPermission(sender,"gsw.admin.help",false)) utils.sendMessage(sender,cmdPrefix + " admin &e- &7Admin commands");
                return true;
            }
            if (args[0].equalsIgnoreCase("admin")) {
                if(args.length == 1 || args[1].equalsIgnoreCase("1")) {
                    if(hasPermission(sender,"gsw.admin.help.game",true)) {
                        utils.sendMessage(sender,cmdPrefix + " admin game create (game) (worldTemplate) &e- &7Create Arena");
                        utils.sendMessage(sender,cmdPrefix + " admin game delete (game) &e- &7Delete Arena");
                        utils.sendMessage(sender,cmdPrefix + " admin game setName (game) (name) &e- &7Set game name");
                        utils.sendMessage(sender,cmdPrefix + " admin game setMin (game) (min) &e- &7Set Min Players");
                        utils.sendMessage(sender,cmdPrefix + " admin game setMax (game) (max) &e- &7Set Max Players");
                        utils.sendMessage(sender,cmdPrefix + " admin game addSC (game) &e- &7Add SuperChest");
                        utils.sendMessage(sender,cmdPrefix + " admin game delSC (game) &e- &7Remove SuperChest");
                        utils.sendMessage(sender,cmdPrefix + " admin game addCage (game) &e- &7Add Cage Location");
                        utils.sendMessage(sender,cmdPrefix + " admin game removeCage (game) [cageID] &e- &7Remove Cage Location");
                        utils.sendMessage(sender,cmdPrefix + " admin game setWaiting (game) &e- &7Set Waiting Location");
                        utils.sendMessage(sender,cmdPrefix + " admin game setSpectator (game) &e- &7Set Spectator Location");
                        utils.sendMessage(sender, cmdPrefix + " admin [page number]");
                        utils.sendMessage(sender,"&e(GuardianSkyWars: Page 1&6/3&e)");
                    }
                    return true;
                }

                if(args[1].equalsIgnoreCase("2")) {
                    if(hasPermission(sender,"gsw.admin.help.worlds",true)) {
                        utils.sendMessage(sender,cmdPrefix + " admin worlds load (name) [schematic] &e- &7Load World");
                        utils.sendMessage(sender,cmdPrefix + " admin worlds unload (name) &e- &7Unload World");
                        utils.sendMessage(sender,cmdPrefix + " admin worlds list &e- &7List of worlds loaded with this plugin");
                        utils.sendMessage(sender,cmdPrefix + " admin worlds clone (name) [cloned world name] &e- &7Clone a world");
                        utils.sendMessage(sender,cmdPrefix + " admin worlds save (name) &e- &7Save changes in a world");
                        utils.sendMessage(sender, cmdPrefix + " admin [page number]");
                        utils.sendMessage(sender,"&e(GuardianSkyWars: Page 2&6/3&e)");
                    }
                    return true;
                }

                if(args[1].equalsIgnoreCase("3")) {
                    if(hasPermission(sender,"gsw.admin.help.others",true)) {
                        utils.sendMessage(sender,"&ePlugin Holograms:");
                        utils.sendMessage(sender,cmdPrefix + " admin holo setHolo (topKills-topWins-playerStats) &e- &7Set Holo");
                        utils.sendMessage(sender,cmdPrefix + " admin holo delHolo (topKills-topWins-playerStats) &e- &7Del Holo");
                        utils.sendMessage(sender,cmdPrefix + " admin holo list &e- &7List of holograms");
                        utils.sendMessage(sender,"&ePlugin NPCs:");
                        utils.sendMessage(sender,cmdPrefix + " admin npc setNPC (Solo-Duos-Trios-Team-Ranked) &e- &7Set NPC");
                        utils.sendMessage(sender,cmdPrefix + " admin npc delNPC (Solo-Duos-Trios-Team-Ranked) &e- &7Del NPC");
                        utils.sendMessage(sender,cmdPrefix + " admin npc list &e- &7List of NPCs");
                        utils.sendMessage(sender,"&ePlugin Coin System:");
                        utils.sendMessage(sender,cmdPrefix + " admin coins set (player) (coins) &e- &7Set coins of a player");
                        utils.sendMessage(sender,cmdPrefix + " admin coins add (player) (coins) &e- &7Add coins to a player");
                        utils.sendMessage(sender,cmdPrefix + " admin coins remove (player) (coins) &e- &7Remove coins from a player");
                        utils.sendMessage(sender,"&ePlugin Cages:");
                        utils.sendMessage(sender,cmdPrefix + " admin cage add (schem name) (SOLO-DUOS-TRIOS-TEAM-RANKED) &e- &7Add Cage");
                        utils.sendMessage(sender,cmdPrefix + " admin cage price (cageID) (price) &e- &7Change the price of an cage");
                        utils.sendMessage(sender,cmdPrefix + " admin cage name (cageID) (name) &e- &7Change the name of an cage");
                        utils.sendMessage(sender, "&ePlugin Admin Commands:");
                        utils.sendMessage(sender,cmdPrefix + " admin reload (Holograms- &e- &7Reload the plugin");
                        utils.sendMessage(sender, cmdPrefix + " admin [page number]");
                        utils.sendMessage(sender,"&e(GuardianSkyWars: Page 3&6/3&e)");
                    }
                    return true;
                }
                if(args[1].equalsIgnoreCase("game") && args.length >= 4) {
                    gameCommand.usage(getArguments(args));
                    return true;
                }
                if(args[1].equalsIgnoreCase("holo") && args.length >= 4) {
                    holoCommand.usage(getArguments(args));
                    return true;
                }
                if(args[1].equalsIgnoreCase("coins") && args.length >= 4) {
                    coinCommand.usage(getArguments(args));
                    return true;
                }
                if(args[1].equalsIgnoreCase("npc") && args.length >= 4) {
                    npcCommand.usage(getArguments(args));
                    return true;
                }
                if(args[1].equalsIgnoreCase("worlds") && args.length >= 4) {
                    worldsCommand.usage(getArguments(args));
                    return true;
                }
            }
            return true;
        } catch (Throwable throwable) {
            plugin.getLogs().error(throwable);
        }
        return true;
    }
    private String[] getArguments(String[] args){
        String[] arguments = new String[args.length - 3];
        int argID = 0;
        int aID = 0;
        for(String arg : args) {
            if(aID != 0 && aID != 1) {
                arguments[argID] = arg;
                argID++;
            }
            aID++;
        }
        return arguments;
    }
}