package dev.mruniverse.guardianskywars.commands.admin;

import dev.mruniverse.guardianskywars.GuardianSkyWars;
import org.bukkit.command.CommandSender;

public class CoinCommand {
    private final GuardianSkyWars main;
    private final String command;
    public CoinCommand(GuardianSkyWars main,String command) {
        this.main = main;
        this.command = command;
    }

    public void usage(CommandSender sender, String[] arguments) {

    }
}
