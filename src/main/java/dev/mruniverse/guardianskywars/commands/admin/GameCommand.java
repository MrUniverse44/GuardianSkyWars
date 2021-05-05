package dev.mruniverse.guardianskywars.commands.admin;

import dev.mruniverse.guardianlib.core.utils.Utils;
import dev.mruniverse.guardianskywars.GuardianSkyWars;
import dev.mruniverse.guardianskywars.enums.GuardianFiles;
import org.bukkit.command.CommandSender;


public class GameCommand {
    private final GuardianSkyWars main;
    private final String cmdPrefix;
    public GameCommand(GuardianSkyWars main,String command) {
        this.main = main;
        cmdPrefix = "&e/" + command;
    }

    public void usage(CommandSender sender, String[] arguments) {
        Utils utils = main.getLib().getUtils();
        if (arguments.length == 0 || arguments[0].equalsIgnoreCase("help")) {
            utils.sendMessage(sender, "&b------------ &aGuardian SkyWars &b------------");
            utils.sendMessage(sender, "&6Admin - Game Commands:");
            utils.sendMessage(sender,cmdPrefix + " admin game create (game) (world_name) &e- &fCreate Arena");
            utils.sendMessage(sender,cmdPrefix + " admin game delete (game) &e- &fDelete Arena");
            utils.sendMessage(sender,cmdPrefix + " admin game setMode (game) (mode) &e- &fSet game mode &b&lOPTIONAL");
            utils.sendMessage(sender,cmdPrefix + " admin game setName (game) (name) &e- &fSet game name");
            utils.sendMessage(sender,cmdPrefix + " admin game setMin (game) (min) &e- &fSet Min Players");
            utils.sendMessage(sender,cmdPrefix + " admin game setMax (game) (max) &e- &fSet Max Players");
            utils.sendMessage(sender,cmdPrefix + " admin game setDuration (game) (duration) &e- &fSet game duration &b&lOPTIONAL");
            utils.sendMessage(sender,cmdPrefix + " admin game addCC (game) &e- &fAdd CenterChest");
            utils.sendMessage(sender,cmdPrefix + " admin game delCC (game) &e- &fRemove CenterChest");
            utils.sendMessage(sender,cmdPrefix + " admin game addSC (game) &e- &fAdd SuperChest");
            utils.sendMessage(sender,cmdPrefix + " admin game delSC (game) &e- &fRemove SuperChest");
            utils.sendMessage(sender,cmdPrefix + " admin game addCage (game) &e- &fAdd Cage Location");
            utils.sendMessage(sender,cmdPrefix + " admin game removeCage (game) [cageID] &e- &fRemove Cage Location");
            utils.sendMessage(sender,cmdPrefix + " admin game setWaiting (game) &e- &fSet Waiting Location");
            utils.sendMessage(sender,cmdPrefix + " admin game setSpectator (game) &e- &fSet Spectator Location");
            utils.sendMessage(sender,cmdPrefix + " admin game save (game) &e- &fSave and load game.");
            utils.sendMessage(sender,cmdPrefix + " admin game load (game) &e- &Load game.");
            utils.sendMessage(sender,cmdPrefix + " admin game unload (game) &e- &fUnload game.");
            utils.sendMessage(sender, "&b------------------------------------");
            return;
        }
        if (arguments[0].equalsIgnoreCase("create")) {
            if (arguments.length == 3) {
                String game = arguments[1];
                String world = arguments[2];
                if (main.getGameManager().getGame(game) == null) {
                    if (!main.getStorage().getControl(GuardianFiles.GAMES).contains("games." + game)) {
                        main.getGameManager().createGameFiles(game,world);
                        String message = main.getStorage().getControl(GuardianFiles.MESSAGES).getString("messages.lobby.admin.create");
                        if (message == null) message = "&aArena &b%arena_id% &acreated correctly!";
                        utils.sendMessage(sender, message.replace("%arena_id%", game));
                        return;
                    }
                    utils.sendMessage(sender, "&cThis game already exists in &6games.yml");
                    return;
                }
                utils.sendMessage(sender, "&cThis game already exists");
                return;
            }
            enoughtArguments(sender);
            return;
        }
    }

    public void enoughtArguments(CommandSender sender) {
        main.getLib().getUtils().sendMessage(sender,"&cEnought Arguments! use &e/gsw admin game help &cto see correct usage!");
    }
}
