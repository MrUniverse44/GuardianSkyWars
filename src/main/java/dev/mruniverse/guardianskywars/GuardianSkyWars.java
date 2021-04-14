package dev.mruniverse.guardianskywars;

import dev.mruniverse.guardianlib.core.GuardianLIB;
import dev.mruniverse.guardianlib.core.utils.ExternalLogger;
import dev.mruniverse.guardianskywars.commands.MainCommand;
import dev.mruniverse.guardianskywars.files.DataStorage;
import dev.mruniverse.guardianskywars.files.FileStorage;
import dev.mruniverse.guardianskywars.utils.LocationUtils;
import dev.mruniverse.guardianskywars.worlds.WorldController;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public final class GuardianSkyWars extends JavaPlugin {
    private ExternalLogger logger;
    private DataStorage dataStorage;
    private FileStorage fileStorage;
    private GuardianLIB lib;
    private LocationUtils locationUtils;
    private WorldController worldController;
    private static GuardianSkyWars instance;
    @Override
    public void onEnable() {
        instance = this;
        new MainCommand(this,"gsw");
        new MainCommand(this,"guardianskywars");
        logger = new ExternalLogger(this,"GuardianSkyWars","dev.mruniverse.guardianskywars.");
        locationUtils = new LocationUtils(this);
        fileStorage = new FileStorage(this);
        worldController = new WorldController(this);
        lib = GuardianLIB.getControl();
        dataStorage = new DataStorage(this);
    }
    public WorldController getWorldController() { return worldController; }
    public DataStorage getData() { return dataStorage; }
    public ExternalLogger getLogs() {
        return logger;
    }
    public LocationUtils getLocationUtils() { return locationUtils; }
    public FileStorage getStorage() {
        return fileStorage;
    }
    public GuardianLIB getLib() {
        return lib;
    }
    public static GuardianSkyWars getInstance() {
        return instance;
    }
    
}
