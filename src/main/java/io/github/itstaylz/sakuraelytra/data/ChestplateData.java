package io.github.itstaylz.sakuraelytra.data;

import io.github.itstaylz.hexlib.items.ItemBuilder;
import io.github.itstaylz.sakurarunes.RuneManager;
import io.github.itstaylz.sakurarunes.runes.Rune;
import net.splodgebox.eliteenchantments.enchants.EnchantManager;
import net.splodgebox.eliteenchantments.enchants.data.CustomEnchant;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

public class ChestplateData extends ItemData implements Serializable {

    private final String materialName;

    public ChestplateData(ItemStack item) {
        super(item);
        this.materialName = item.getType().name();
    }

    @Override
    public ItemStack create() {
        ItemBuilder chestplateBuilder = new ItemBuilder(Material.valueOf(this.materialName))
                .setDisplayName(this.name)
                .setLore(this.lore);
        this.enchants.forEach((keyStr, level) -> {
            NamespacedKey key = NamespacedKey.fromString(keyStr);
            chestplateBuilder.addEnchantment(Enchantment.getByKey(key), level);
        });
        ItemStack chestplate = chestplateBuilder.build();
        this.customEnchants.forEach((name, level) -> {
            CustomEnchant enchant = EnchantManager.getInstance().getEnchantController().getEnchant(name);
            enchant.addEnchant(chestplate, level, true);
        });
        if (this.runeID != null) {
            Rune rune = RuneManager.getRuneFromID(this.runeID);
            RuneManager.applyRuneToItem(chestplate, rune);
        }
        return chestplate;
    }
}
