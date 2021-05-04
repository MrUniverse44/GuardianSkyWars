package dev.mruniverse.guardianskywars.files;

import dev.mruniverse.guardianskywars.GuardianSkyWars;
import dev.mruniverse.guardianskywars.enums.GuardianFiles;
import dev.mruniverse.guardianskywars.enums.GuardianWorld;
import dev.mruniverse.guardianskywars.enums.SaveMode;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

@SuppressWarnings("unused")
public class FileStorage {
    private final GuardianSkyWars plugin;

    private FileConfiguration settings;

    private FileConfiguration messages;

    private FileConfiguration mysql;

    private FileConfiguration data;

    private FileConfiguration menus;

    private FileConfiguration items;

    private FileConfiguration games;

    private FileConfiguration boards;

    private FileConfiguration chests;

    private FileConfiguration pluginWorlds;

    private FileConfiguration kits;

    private final File rxSettings;

    private final File rxMessages;

    private final File rxPluginWorlds;

    private final File rxMySQL;

    private final File rxData;

    private final File rxMenus;

    private final File rxItems;

    private final File rxGames;

    private final File rxBoards;

    private final File rxChests;

    private final File rxKits;

    private final File worldsFolder;

    private final File gameBackup;

    private final File schematicFolder;

    public FileStorage(GuardianSkyWars main) {
        this.plugin = main;
        File dataFolder = main.getDataFolder();
        this.worldsFolder = new File(dataFolder,"worlds");
        this.gameBackup = new File(dataFolder,"backups");
        this.schematicFolder = new File(dataFolder,"schematics");
        this.rxSettings = new File(dataFolder, "settings.yml");
        this.rxMessages = new File(dataFolder, "messages.yml");
        this.rxMySQL = new File(dataFolder, "mysql.yml");
        this.rxData = new File(dataFolder, "data.yml");
        this.rxMenus = new File(dataFolder, "menus.yml");
        this.rxItems = new File(dataFolder, "items.yml");
        this.rxGames = new File(dataFolder, "games.yml");
        this.rxBoards = new File(dataFolder, "scoreboards.yml");
        this.rxChests = new File(dataFolder, "chests.yml");
        this.rxPluginWorlds = new File(dataFolder, "others_worlds.yml");
        this.rxKits = new File(dataFolder, "kits.yml");
        this.settings = loadConfig("settings");
        this.menus = loadConfig("menus");
        this.messages = loadConfig("messages");
        this.items = loadConfig("items");
        this.mysql = loadConfig("mysql");
        this.data = loadConfig("data");
        this.games = loadConfig("games");
        this.boards = loadConfig("scoreboards");
        this.chests = loadConfig("chests");
        this.pluginWorlds = loadConfig("others_worlds");
        this.kits = loadConfig("kits");
    }

    public File getFile(GuardianFiles fileToGet) {
        switch (fileToGet) {
            case PLUGIN_WORLDS:
                return rxPluginWorlds;
            case CHESTS:
                return rxChests;
            case GAMES:
                return rxGames;
            case ITEMS:
                return rxItems;
            case DATA:
                return rxData;
            case MENUS:
                return rxMenus;
            case MESSAGES:
                return rxMessages;
            case KITS:
                return rxKits;
            case MYSQL:
                return rxMySQL;
            case SETTINGS:
                return rxSettings;
            case SCOREBOARD:
                return rxBoards;
        }
        return this.rxSettings;
    }

    public FileConfiguration loadConfig(String configName) {
        YamlConfiguration yamlConfiguration = null;
        File configFile = new File(this.plugin.getDataFolder(), configName + ".yml");
        if (!configFile.exists())
            saveConfig(configName);
        try {
            yamlConfiguration = YamlConfiguration.loadConfiguration(configFile);
            this.plugin.getLogs().info(String.format("&7File &e%s.yml &7has been loaded", configName));
        } catch (Exception e) {
            this.plugin.getLogs().warn(String.format("A error occurred while loading the settings file. Error: %s", e));
        }
        return yamlConfiguration;
    }

    public FileConfiguration loadConfig(File rigoxFile) {
        YamlConfiguration yamlConfiguration = null;
        if (!rigoxFile.exists())
            saveConfig(rigoxFile);
        try {
            yamlConfiguration = YamlConfiguration.loadConfiguration(rigoxFile);
        } catch (Exception e) {
            this.plugin.getLogs().warn(String.format("A error occurred while loading the settings file. Error: %s", e));
        }
        this.plugin.getLogs().info(String.format("&7File &e%s &7has been loaded", rigoxFile.getName()));
        return yamlConfiguration;
    }

    public void reloadFile(SaveMode Mode) {
        switch (Mode) {
            case CHESTS:
                this.chests = YamlConfiguration.loadConfiguration(this.rxChests);
                return;
            case ITEMS:
                this.items = YamlConfiguration.loadConfiguration(this.rxItems);
                return;
            case DATA:
                this.data = YamlConfiguration.loadConfiguration(this.rxData);
                return;
            case MENUS:
                this.menus = YamlConfiguration.loadConfiguration(this.rxMenus);
                return;
            case MESSAGES:
                this.messages = YamlConfiguration.loadConfiguration(this.rxMessages);
                return;
            case KITS:
                this.kits = YamlConfiguration.loadConfiguration(this.rxKits);
                return;
            case MYSQL:
                this.mysql = YamlConfiguration.loadConfiguration(this.rxMySQL);
                return;
            case SETTINGS:
                this.settings = YamlConfiguration.loadConfiguration(this.rxSettings);
                return;
            case SCOREBOARDS:
                this.boards = YamlConfiguration.loadConfiguration(this.rxBoards);
                return;
            case GAMES_FILES:
                this.games = YamlConfiguration.loadConfiguration(this.rxGames);
                return;
        }
        this.messages = YamlConfiguration.loadConfiguration(this.rxMessages);
        this.data = YamlConfiguration.loadConfiguration(this.rxData);
        this.items = YamlConfiguration.loadConfiguration(this.rxItems);
        this.chests = YamlConfiguration.loadConfiguration(this.rxChests);
        this.kits = YamlConfiguration.loadConfiguration(this.rxKits);
        this.menus = YamlConfiguration.loadConfiguration(this.rxMenus);
        this.mysql = YamlConfiguration.loadConfiguration(this.rxMySQL);
        this.settings = YamlConfiguration.loadConfiguration(this.rxSettings);
        this.boards = YamlConfiguration.loadConfiguration(this.rxBoards);
        this.games = YamlConfiguration.loadConfiguration(this.rxGames);
    }

    public void save(SaveMode fileToSave) {
        try {
            switch (fileToSave) {
                case CHESTS:
                    chests.save(this.rxChests);
                    return;
                case ITEMS:
                    items.save(this.rxItems);
                    return;
                case DATA:
                    data.save(this.rxData);
                    return;
                case KITS:
                    kits.save(this.rxKits);
                    return;
                case GAMES_FILES:
                    games.save(this.rxGames);
                    return;
                case MENUS:
                    menus.save(this.rxMenus);
                    return;
                case SCOREBOARDS:
                    boards.save(this.rxBoards);
                    return;
                case MYSQL:
                    mysql.save(this.rxMySQL);
                    return;
                case MESSAGES:
                    messages.save(this.rxMessages);
                    return;
                case SETTINGS:
                    settings.save(this.rxSettings);
                    return;
            }
            settings.save(rxSettings);
            chests.save(rxChests);
            data.save(rxData);
            kits.save(rxKits);
            games.save(rxGames);
            boards.save(rxBoards);
            items.save(rxItems);
            menus.save(rxMenus);
            pluginWorlds.save(rxPluginWorlds);
            mysql.save(rxMySQL);
            messages.save(rxMessages);
        } catch (Throwable throwable) {
            plugin.getLogs().error("Can't save files!");
            plugin.getLogs().error(throwable);
        }
    }

    public void saveConfig(String configName) {
        File folderDir = this.plugin.getDataFolder();
        File file = new File(this.plugin.getDataFolder(), configName + ".yml");
        if (!folderDir.exists()) {
            boolean createFile = folderDir.mkdir();
            if (createFile)
                this.plugin.getLogs().info("&7Folder created!");
        }
        if (!file.exists())
            try (InputStream in = this.plugin.getResource(configName + ".yml")) {
                if (in != null)
                    Files.copy(in, file.toPath());
            } catch (Throwable throwable) {
                this.plugin.getLogs().error(String.format("A error occurred while copying the config %s to the plugin data folder. Error: %s", configName, throwable));
                this.plugin.getLogs().error(throwable);
            }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public File getWorldsFolder(GuardianWorld guardianWorld) {
        if(!gameBackup.exists()) gameBackup.mkdirs();
        if(!worldsFolder.exists()) worldsFolder.mkdirs();
        if(guardianWorld == GuardianWorld.BACKUP) {
            return gameBackup;
        }
        return worldsFolder;
    }
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public File getSchematicFolder() {
        if(!schematicFolder.exists()) schematicFolder.mkdirs();
        return schematicFolder;
    }

    public void saveConfig(File fileToSave) {
        if (!fileToSave.getParentFile().exists()) {
            boolean createFile = fileToSave.mkdir();
            if (createFile)
                this.plugin.getLogs().info("&7Folder created!!");
        }
        if (!fileToSave.exists()) {
            this.plugin.getLogs().debug(fileToSave.getName());
            try (InputStream in = this.plugin.getResource(fileToSave.getName() + ".yml")) {
                if (in != null)
                    Files.copy(in, fileToSave.toPath());
            } catch (Throwable throwable) {
                this.plugin.getLogs().error(String.format("A error occurred while copying the config %s to the plugin data folder. Error: %s", fileToSave.getName(), throwable));
                this.plugin.getLogs().error(throwable);
            }
        }
    }

    public FileConfiguration getControl(GuardianFiles fileToControl) {
        switch (fileToControl) {
            case CHESTS:
                if (this.chests == null)
                    this.items = loadConfig(this.rxChests);
                return this.chests;
            case ITEMS:
                if (this.items == null)
                    this.items = loadConfig(this.rxItems);
                return this.items;
            case DATA:
                if (this.data == null)
                    this.data = loadConfig(this.rxData);
                return this.data;
            case SCOREBOARD:
                if (this.boards == null)
                    this.boards = loadConfig(this.rxBoards);
                return this.boards;
            case MENUS:
                if (this.menus == null)
                    this.menus = loadConfig(this.rxMenus);
                return this.menus;
            case GAMES:
                if (this.games == null)
                    this.games = loadConfig(this.rxGames);
                return this.games;
            case MESSAGES:
                if (this.messages == null)
                    this.messages = loadConfig(this.rxMessages);
                return this.messages;
            case KITS:
                if (this.kits == null)
                    this.kits = loadConfig(this.rxKits);
                return this.kits;
            case MYSQL:
                if (this.mysql == null)
                    this.mysql = loadConfig(this.rxMySQL);
                return this.mysql;
            case PLUGIN_WORLDS:
                if (this.pluginWorlds == null)
                    this.pluginWorlds = loadConfig(this.rxPluginWorlds);
                return this.pluginWorlds;
            case SETTINGS:
                if (this.settings == null)
                    this.settings = loadConfig(this.rxSettings);
                return this.settings;
        }
        if (this.settings == null)
            this.settings = loadConfig(this.rxSettings);
        return this.settings;
    }

    public List<String> getContent(GuardianFiles file, String path, boolean getKeys) {
        List<String> rx = new ArrayList<>();
        ConfigurationSection section = getControl(file).getConfigurationSection(path);
        if (section == null)
            return rx;
        rx.addAll(section.getKeys(getKeys));
        return rx;
    }
}

