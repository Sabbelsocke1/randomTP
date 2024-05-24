package org.sabbelsocke.randomtp;

import org.bukkit.plugin.java.JavaPlugin;
import org.sabbelsocke.randomtp.commands.RTPCommand;

public final class RandomTP extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("randomtp").setExecutor(new RTPCommand(this));

    }

    @Override
    public void onDisable() {
    }
}
