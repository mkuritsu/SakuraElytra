package io.github.itstaylz.sakuraelytra.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ChestplateUtils {

    public static double getDefaultArmor(ItemStack chestplate) {
        return switch (chestplate.getType()) {
            case LEATHER_CHESTPLATE -> 3;
            case CHAINMAIL_CHESTPLATE, GOLDEN_CHESTPLATE -> 5;
            case IRON_CHESTPLATE -> 6;
            case DIAMOND_CHESTPLATE, NETHERITE_CHESTPLATE -> 8;
            default -> 0;
        };
    }

    public static double getDefaultArmorToughness(ItemStack chestplate) {
        return switch (chestplate.getType()) {
            case DIAMOND_CHESTPLATE -> 2;
            case NETHERITE_CHESTPLATE -> 3;
            default -> 0;
        };
    }

    public static double getDefaultKnockBackResistance(ItemStack chestplate) {
        return chestplate.getType() == Material.NETHERITE_CHESTPLATE ? 0.1 : 0;
    }
}
