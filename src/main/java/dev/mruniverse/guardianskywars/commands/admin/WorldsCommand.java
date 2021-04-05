package dev.mruniverse.guardianskywars.commands.admin;

import dev.mruniverse.guardianskywars.GuardianSkyWars;

public class WorldsCommand {
    private final GuardianSkyWars main;
    private final String command;
    public WorldsCommand(GuardianSkyWars main,String command) {
        this.main = main;
        this.command = command;
    }

    public void usage(String[] arguments) {

    }
}
