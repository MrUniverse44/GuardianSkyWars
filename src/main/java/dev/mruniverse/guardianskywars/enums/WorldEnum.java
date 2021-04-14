package dev.mruniverse.guardianskywars.enums;

import dev.mruniverse.guardianskywars.GuardianSkyWars;

public enum WorldEnum {
    DEFAULT_GEN,
    SLIME;

    public String getLoader() {
        String result;
        result = GuardianSkyWars.getInstance().getStorage().getControl(GuardianFiles.SETTINGS).getString("settings.SlimeWorldManager.loadType");
        if(result == null) result = "FILE";
        return result;
    }
}
