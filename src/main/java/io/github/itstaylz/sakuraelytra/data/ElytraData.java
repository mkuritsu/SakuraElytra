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

public class ElytraData extends ItemData implements Serializable {

    public ElytraData(ItemStack elytra) {
        super(elytra);
    }

    @Override
    public ItemStack create() {
        ItemBuilder elytraBuilder = new ItemBuilder(Material.ELYTRA)
                .setDisplayName(this.name)
                .setLore(this.lore);
        this.enchants.forEach((keyStr, level) -> {
            NamespacedKey key = NamespacedKey.fromString(keyStr);
            elytraBuilder.addEnchantment(Enchantment.getByKey(key), level);
        });
        ItemStack elytra = elytraBuilder.build();
        this.customEnchants.forEach((name, level) -> {
            CustomEnchant enchant = EnchantManager.getInstance().getEnchantController().getEnchant(name);
            enchant.addEnchant(elytra, level, true);
        });
        if (this.runeID != null) {
            Rune rune = RuneManager.getRuneFromID(this.runeID);
            RuneManager.applyRuneToItem(elytra, rune);
        }
        return elytra;
    }

}
