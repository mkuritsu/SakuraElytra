package io.github.itstaylz.sakuraelytra.commands;

import io.github.itstaylz.hexlib.utils.StringUtils;
import io.github.itstaylz.sakuraelytra.SakuraElytraPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    private final SakuraElytraPlugin plugin;

    public ReloadCommand(SakuraElytraPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("sakuraelytra")) {
            this.plugin.getMessageConfig().reload();
            sender.sendMessage(StringUtils.colorize("&aPlugin has been reloaded!"));
            return true;
        }
        return false;
    }
}
