package dev.mruniverse.guardianskywars.interfaces;

public interface GameWorld {
    void createWorld(String worldName);

    void loadWorld(String worldName);

    void saveWorld(String worldName);

    void unloadWorld(String worldName);

    void cloneWorld(String worldName,String newWorld);

}
