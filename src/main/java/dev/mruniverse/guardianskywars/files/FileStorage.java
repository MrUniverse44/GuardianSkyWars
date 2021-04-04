package dev.mruniverse.guardianskywars.files;

import dev.mruniverse.guardianskywars.GuardianSkyWars;
import dev.mruniverse.guardianskywars.enums.GuardianFiles;
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

    private FileConfiguration kits;

    private final File rxSettings;

    private final File rxMessages;

    private final File rxMySQL;

    private final File rxData;

    private final File rxMenus;

    private final File rxItems;

    private final File rxGames;

    private final File rxBoards;

    private final File rxChests;

    private final File rxKits;

    public FileStorage(GuardianSkyWars main) {
        this.plugin = main;
        this.rxSettings = new File(main.getDataFolder(), "settings.yml");
        this.rxMessages = new File(main.getDataFolder(), "messages.yml");
        this.rxMySQL = new File(main.getDataFolder(), "mysql.yml");
        this.rxData = new File(main.getDataFolder(), "data.yml");
        this.rxMenus = new File(main.getDataFolder(), "menus.yml");
        this.rxItems = new File(main.getDataFolder(), "items.yml");
        this.rxGames = new File(main.getDataFolder(), "games.yml");
        this.rxBoards = new File(main.getDataFolder(), "scoreboards.yml");
        this.rxChests = new File(main.getDataFolder(), "chests.yml");
        this.rxKits = new File(main.getDataFolder(), "kits.yml");
        this.settings = loadConfig("settings");
        this.menus = loadConfig("menus");
        this.messages = loadConfig("messages");
        this.items = loadConfig("items");
        this.mysql = loadConfig("mysql");
        this.data = loadConfig("data");
        this.games = loadConfig("games");
        this.boards = loadConfig("scoreboards");
        this.chests = loadConfig("chests");
        this.kits = loadConfig("kits");
    }

    public File getFile(GuardianFiles fileToGet) {
        switch (fileToGet) {
            case CHESTS:
                return this.rxChests;
            case ITEMS:
                return this.rxItems;
            case DATA:
                return this.rxData;
            case MENUS:
                return this.rxGames;
            case MESSAGES:
                return this.rxMenus;
            case KITS:
                return this.rxKits;
            case MYSQL:
                return this.rxMySQL;
            case SETTINGS:
                return this.rxMessages;
            case SCOREBOARD:
                return this.rxBoards;
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
                    getControl(GuardianFiles.CHESTS).save(this.rxChests);
                    return;
                case ITEMS:
                    getControl(GuardianFiles.ITEMS).save(this.rxItems);
                    return;
                case DATA:
                    getControl(GuardianFiles.DATA).save(this.rxData);
                    return;
                case KITS:
                    getControl(GuardianFiles.KITS).save(this.rxKits);
                    return;
                case GAMES_FILES:
                    getControl(GuardianFiles.GAMES).save(this.rxGames);
                    return;
                case MENUS:
                    getControl(GuardianFiles.MENUS).save(this.rxMenus);
                    return;
                case SCOREBOARDS:
                    getControl(GuardianFiles.SCOREBOARD).save(this.rxBoards);
                    return;
                case MYSQL:
                    getControl(GuardianFiles.MYSQL).save(this.rxMySQL);
                    return;
                case MESSAGES:
                    getControl(GuardianFiles.MESSAGES).save(this.rxMessages);
                    return;
                case SETTINGS:
                    getControl(GuardianFiles.SETTINGS).save(this.rxSettings);
                    return;
            }
            getControl(GuardianFiles.SETTINGS).save(this.rxSettings);
            getControl(GuardianFiles.CHESTS).save(this.rxChests);
            getControl(GuardianFiles.DATA).save(this.rxData);
            getControl(GuardianFiles.KITS).save(this.rxKits);
            getControl(GuardianFiles.GAMES).save(this.rxGames);
            getControl(GuardianFiles.SCOREBOARD).save(this.rxBoards);
            getControl(GuardianFiles.ITEMS).save(this.rxItems);
            getControl(GuardianFiles.MENUS).save(this.rxMenus);
            getControl(GuardianFiles.MYSQL).save(this.rxMySQL);
            getControl(GuardianFiles.MESSAGES).save(this.rxMessages);
        } catch (Throwable throwable) {
            this.plugin.getLogs().error("Can't save a file!");
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

    public FileConfiguration getLang() {
        String language = getControl(GuardianFiles.SETTINGS).getString("settings.defaultLang");
        if(language == null) language = "en_US";
        if(language.equalsIgnoreCase("es_ES") || language.equalsIgnoreCase("Spanish")) {
            return getControl(GuardianFiles.MESSAGES_ES);
        }
        return getControl(GuardianFiles.MESSAGES);
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
                if (this.games == null)
                    this.games = loadConfig(this.rxGames);
                return this.games;
            case MESSAGES:
                if (this.menus == null)
                    this.menus = loadConfig(this.rxMenus);
                return this.menus;
            case KITS:
                if (this.kits == null)
                    this.kits = loadConfig(this.rxKits);
                return this.kits;
            case MYSQL:
                if (this.mysql == null)
                    this.mysql = loadConfig(this.rxMySQL);
                return this.mysql;
            case SETTINGS:
                if (this.messages == null)
                    this.messages = loadConfig(this.rxMessages);
                return this.messages;
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

