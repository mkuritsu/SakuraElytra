package io.github.itstaylz.sakuraelytra.data;

import io.github.itstaylz.sakuraelytra.utils.ElytraUtils;
import io.github.itstaylz.sakurarunes.RuneManager;
import io.github.itstaylz.sakurarunes.runes.Rune;
import net.splodgebox.eliteenchantments.enchants.data.CustomEnchant;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ItemData implements Serializable {

    protected final String name;
    protected final List<String> lore;
    protected final String runeID;
    protected final Map<String, Integer> enchants = new HashMap<>();
    protected final Map<String, Integer> customEnchants = new HashMap<>();

    public ItemData(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null)
            throw new RuntimeException("Invalid item");
        this.name = meta.getDisplayName();
        this.lore = meta.getLore() != null ? meta.getLore() : new ArrayList<>();
        Rune appliedRune = RuneManager.getRune(item);
        this.runeID = appliedRune == null ? null : appliedRune.id();
        meta.getEnchants().forEach((enchant, level) -> {
            this.enchants.put(enchant.getKey().toString(), level);
        });
        for (CustomEnchant enchant : ElytraUtils.getCustomEnchants()) {
            int level = enchant.getEnchantLevel(item);
            if (level > 0)
                this.customEnchants.put(enchant.getName(), level);
        }
    }

    public String getName() {
        return name;
    }

    public List<String> getLore() {
        return lore;
    }

    public abstract ItemStack create();
}
