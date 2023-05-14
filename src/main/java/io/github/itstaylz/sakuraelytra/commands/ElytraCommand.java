package io.github.itstaylz.sakuraelytra.commands;

import io.github.itstaylz.hexlib.utils.StringUtils;
import io.github.itstaylz.sakuraelytra.SakuraElytraPlugin;
import io.github.itstaylz.sakuraelytra.menus.ElytraMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ElytraCommand implements CommandExecutor {

    private final SakuraElytraPlugin plugin;

    public ElytraCommand(SakuraElytraPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("sakuraelytra")) {
            if (sender instanceof Player player)
                new ElytraMenu(this.plugin).open(player);
            else
                sender.sendMessage(StringUtils.colorize("&cThis command can only be executed by players!"));
            return true;
        }
        return false;
    }
}
