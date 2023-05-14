package io.github.itstaylz.sakuraelytra;

import io.github.itstaylz.sakuraelytra.commands.ElytraCommand;
import io.github.itstaylz.sakuraelytra.commands.ReloadCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class SakuraElytraPlugin extends JavaPlugin {

    private MessageConfig messageConfig;

    @Override
    public void onEnable() {
        this.messageConfig = new MessageConfig(this);
        getCommand("sakuraelytra").setExecutor(new ElytraCommand(this));
        getCommand("sakuraelytrareload").setExecutor(new ReloadCommand(this));
    }

    public MessageConfig getMessageConfig() {
        return messageConfig;
    }
}
