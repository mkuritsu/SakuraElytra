package io.github.itstaylz.sakuraelytra;

import io.github.itstaylz.hexlib.storage.file.YamlFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class MessageConfig {

    private final YamlFile messagesYaml;

    public MessageConfig(JavaPlugin plugin) {
        plugin.saveResource("messages.yml", false);
        this.messagesYaml = new YamlFile(new File(plugin.getDataFolder(), "messages.yml"));
    }

    public void reload() {
        this.messagesYaml.reload();
    }

    public String getMessage(String id) {
        return this.messagesYaml.getConfig().getString(id);
    }

}
