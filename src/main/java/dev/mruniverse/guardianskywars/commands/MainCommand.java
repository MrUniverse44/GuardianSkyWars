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
        this.cmdPrefix = "&e/" + command;
        gameCommand = new GameCommand(main,command);
        npcCommand = new NPCCommand(main,command);
        holoCommand = new HoloCommand(main,command);
        coinCommand = new CoinCommand(main,command);
        worldsCommand = new WorldsCommand(main);
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
                sender.sendMessage(" ");
                utils.sendMessage(sender,"&b------------ &aGuardian SkyWars &b------------");
                utils.sendMessage(sender,cmdPrefix + " join (name) &e- &fJoin Arena");
                utils.sendMessage(sender,cmdPrefix + " leave &e- &fLeave CMD");
                if(hasPermission(sender,"gsw.admin.help",false)) utils.sendMessage(sender,cmdPrefix + " admin &e- &fAdmin commands");
                utils.sendMessage(sender,"&b------------ &aGuardian SkyWars &b------------");
                return true;
            }
            if (args[0].equalsIgnoreCase("admin")) {
                if(args.length == 1 || args[1].equalsIgnoreCase("1")) {
                    if(hasPermission(sender,"gsw.admin.help.game",true)) {
                        sender.sendMessage(" ");
                        utils.sendMessage(sender,"&b------------ &aGuardian SkyWars &b------------");
                        utils.sendMessage(sender,"&6Admin - Game Commands:");
                        utils.sendMessage(sender,cmdPrefix + " admin game create (game) (worldTemplate) &e- &fCreate Arena");
                        utils.sendMessage(sender,cmdPrefix + " admin game delete (game) &e- &fDelete Arena");
                        utils.sendMessage(sender,cmdPrefix + " admin game setName (game) (name) &e- &fSet game name");
                        utils.sendMessage(sender,cmdPrefix + " admin game setMin (game) (min) &e- &fSet Min Players");
                        utils.sendMessage(sender,cmdPrefix + " admin game setMax (game) (max) &e- &fSet Max Players");
                        utils.sendMessage(sender,cmdPrefix + " admin game addSC (game) &e- &fAdd SuperChest");
                        utils.sendMessage(sender,cmdPrefix + " admin game delSC (game) &e- &fRemove SuperChest");
                        utils.sendMessage(sender,cmdPrefix + " admin game addCage (game) &e- &fAdd Cage Location");
                        utils.sendMessage(sender,cmdPrefix + " admin game removeCage (game) [cageID] &e- &fRemove Cage Location");
                        utils.sendMessage(sender,cmdPrefix + " admin game setWaiting (game) &e- &fSet Waiting Location");
                        utils.sendMessage(sender,cmdPrefix + " admin game setSpectator (game) &e- &fSet Spectator Location");
                        utils.sendMessage(sender,"&b------------ &a(Page 1&l/3&a) &b------------");
                    }
                    return true;
                }

                if(args[1].equalsIgnoreCase("2")) {
                    if(hasPermission(sender,"gsw.admin.help.worlds",true)) {
                        sender.sendMessage(" ");
                        utils.sendMessage(sender,"&b------------ &aGuardian SkyWars &b------------");
                        utils.sendMessage(sender,"&6Admin - Worlds Commands:");
                        utils.sendMessage(sender,cmdPrefix + " admin worlds create (name) [schematic] &e- &fCreate world");
                        utils.sendMessage(sender,cmdPrefix + " admin worlds load (name) &e- &fLoad World");
                        utils.sendMessage(sender,cmdPrefix + " admin worlds unload (name) &e- &fUnload World");
                        utils.sendMessage(sender,cmdPrefix + " admin worlds list &e- &fList of worlds loaded with this plugin");
                        utils.sendMessage(sender,cmdPrefix + " admin worlds clone (name) [cloned world name] &e- &fClone a world");
                        utils.sendMessage(sender,cmdPrefix + " admin worlds save (name) &e- &fSave changes in a world");
                        utils.sendMessage(sender,"&b------------ &a(Page 2&l/3&a) &b------------");
                    }
                    return true;
                }

                if(args[1].equalsIgnoreCase("3")) {
                    if(hasPermission(sender,"gsw.admin.help.others",true)) {
                        sender.sendMessage(" ");
                        utils.sendMessage(sender,"&b------------ &aGuardian SkyWars &b------------");
                        utils.sendMessage(sender,"&6Admin - Holograms Commands:");
                        utils.sendMessage(sender,cmdPrefix + " admin holo setHolo (kills-wins-stats) &e- &fSet Holo");
                        utils.sendMessage(sender,cmdPrefix + " admin holo delHolo (kills-wins-stats) &e- &fDel Holo");
                        utils.sendMessage(sender,cmdPrefix + " admin holo list &e- &fList of holograms");
                        utils.sendMessage(sender,"&6Admin - NPC Commands:");
                        utils.sendMessage(sender,cmdPrefix + " admin npc setNPC (Mode) &e- &fSet NPC");
                        utils.sendMessage(sender,cmdPrefix + " admin npc delNPC (Mode) &e- &fDel NPC");
                        utils.sendMessage(sender,cmdPrefix + " admin npc list &e- &fList of NPCs");
                        utils.sendMessage(sender,"&6Admin - Coins Commands:");
                        utils.sendMessage(sender,cmdPrefix + " admin coins set (player) (coins) &e- &fSet coins of a player");
                        utils.sendMessage(sender,cmdPrefix + " admin coins add (player) (coins) &e- &fAdd coins to a player");
                        utils.sendMessage(sender,cmdPrefix + " admin coins del (player) (coins) &e- &fDel coins of a player");
                        utils.sendMessage(sender,"&6Admin - Cages Commands:");
                        utils.sendMessage(sender,cmdPrefix + " admin cage add (schem name) (Mode) &e- &fAdd Cage");
                        utils.sendMessage(sender,cmdPrefix + " admin cage price (cID) (int) &e- &fChange the price of an cage");
                        utils.sendMessage(sender,cmdPrefix + " admin cage name (cID) (name) &e- &fChange the name of an cage");
                        utils.sendMessage(sender,"&6Admin - Plugin Commands:");
                        utils.sendMessage(sender,cmdPrefix + " admin reload (Holograms-Messages) &e- &fReload the plugin");
                        utils.sendMessage(sender,cmdPrefix + " admin setlobby &e- &fSet Main Lobby");
                        utils.sendMessage(sender,cmdPrefix + " admin modes &e- &fView all modes of the plugin");
                        utils.sendMessage(sender,"&b------------ &a(Page 3&l/3&a) &b------------");
                    }
                    return true;
                }
                if(args[1].equalsIgnoreCase("game") && args.length >= 4) {
                    if(hasPermission(sender,"gsw.admin.cmd.game",true)) {
                        gameCommand.usage(sender,getArguments(args));

                    }
                    return true;
                }
                if(args[1].equalsIgnoreCase("holo") && args.length >= 4) {
                    if(hasPermission(sender,"gsw.admin.cmd.holo",true)) {
                        holoCommand.usage(sender,getArguments(args));
                    }
                    return true;
                }
                if(args[1].equalsIgnoreCase("coins") && args.length >= 4) {
                    if(hasPermission(sender,"gsw.admin.cmd.coins",true)) {
                        coinCommand.usage(sender,getArguments(args));
                    }
                    return true;
                }
                if(args[1].equalsIgnoreCase("npc") && args.length >= 4) {
                    if(hasPermission(sender,"gsw.admin.cmd.npc",true)) {
                        npcCommand.usage(sender,getArguments(args));
                    }
                    return true;
                }
                if(args[1].equalsIgnoreCase("worlds") && args.length >= 4) {
                    if(hasPermission(sender,"gsw.admin.cmd.worlds",true)) {
                        worldsCommand.usage(sender,getArguments(args));
                    }
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
        String[] arguments = new String[args.length - 2];
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