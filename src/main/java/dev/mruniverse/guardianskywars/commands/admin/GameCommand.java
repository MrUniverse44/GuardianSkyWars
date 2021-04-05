package dev.mruniverse.guardianskywars.commands.admin;

import dev.mruniverse.guardianskywars.GuardianSkyWars;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class GameCommand {
    private final GuardianSkyWars main;
    private final String command;
    public GameCommand(GuardianSkyWars main,String command) {
        this.main = main;
        this.command = command;
    }

    public void usage(CommandSender sender, String[] arguments) {
        //
    }
}
