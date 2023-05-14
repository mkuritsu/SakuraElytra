package io.github.itstaylz.sakuraelytra.utils;

import com.google.common.collect.Multimap;
import io.github.itstaylz.hexlib.items.ItemBuilder;
import io.github.itstaylz.hexlib.utils.ObjectUtils;
import io.github.itstaylz.hexlib.utils.PDCUtils;
import io.github.itstaylz.hexlib.utils.StringUtils;
import io.github.itstaylz.sakuraelytra.SakuraElytraPlugin;
import io.github.itstaylz.sakuraelytra.data.ChestplateData;
import io.github.itstaylz.sakuraelytra.data.ElytraData;
import net.splodgebox.eliteenchantments.enchants.EnchantManager;
import net.splodgebox.eliteenchantments.enchants.data.CustomEnchant;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.UUID;

public class ElytraUtils {

    private static final JavaPlugin PLUGIN;
    private static final NamespacedKey ELYTRA_DATA_KEY, CHESTPLATE_DATA_KEY;
    private static final Collection<CustomEnchant> CUSTOM_ENCHANTS;

    static {
        PLUGIN = JavaPlugin.getProvidingPlugin(SakuraElytraPlugin.class);
        ELYTRA_DATA_KEY = new NamespacedKey(PLUGIN, "elytra_data");
        CHESTPLATE_DATA_KEY = new NamespacedKey(PLUGIN, "chestplate_data");
        CUSTOM_ENCHANTS = EnchantManager.getInstance().getEnchantController().getEnchants().values();
    }

    public static boolean isArmoredElytra(ItemStack itemStack) {
        return PDCUtils.hasPDCValue(itemStack, ELYTRA_DATA_KEY, PersistentDataType.BYTE_ARRAY);
    }

    private static String toFancyUpper(String name) {
        String[] splitted = name.split(" ");
        for (int i = 0; i < splitted.length; i++) {
            char upperFirst = Character.toUpperCase(splitted[i].charAt(0));
            splitted[i] = upperFirst + splitted[i].substring(1);
        }
        return StringUtils.joinStrings(splitted, " ");
    }

    public static ItemStack combineElytra(ItemStack elytra, ItemStack chestplate) {
        ElytraData elytraData = new ElytraData(elytra);
        ChestplateData chestplateData = new ChestplateData(chestplate);
        String materialName = chestplate.getType().name().toLowerCase().replace("_", " ");
        String defaultChestName = toFancyUpper(materialName);
        String chestPlateName = chestplateData.getName().isBlank() ? defaultChestName : chestplateData.getName();
        byte[] elytraDataBytes = ObjectUtils.toBytes(elytraData);
        byte[] chestplateDataBytes = ObjectUtils.toBytes(chestplateData);
        ItemBuilder armoredElytraBuilder = new ItemBuilder(elytra);
        armoredElytraBuilder.setLore("", StringUtils.colorize("&d+ &r&f" + chestPlateName));
        armoredElytraBuilder.addLore(elytraData.getLore());
        ItemMeta chestPlateMeta = chestplate.getItemMeta();
        if (chestPlateMeta != null)
            chestPlateMeta.getEnchants().forEach((e, l) -> armoredElytraBuilder.addEnchantment(e, l));
        applyChestplateAttributes(armoredElytraBuilder, chestplate);
        ItemStack armoredElytra = armoredElytraBuilder.build();
        PDCUtils.setPDCValue(armoredElytra, ELYTRA_DATA_KEY, PersistentDataType.BYTE_ARRAY, elytraDataBytes);
        PDCUtils.setPDCValue(armoredElytra, CHESTPLATE_DATA_KEY, PersistentDataType.BYTE_ARRAY, chestplateDataBytes);
        return armoredElytra;
    }

    public static ItemStack[] separateElytra(ItemStack armoredElytra) {
        byte[] elytraDataBytes = PDCUtils.getPDCValue(armoredElytra, ELYTRA_DATA_KEY, PersistentDataType.BYTE_ARRAY);
        ElytraData elytraData = ObjectUtils.fromBytes(elytraDataBytes, ElytraData.class);
        ItemStack elytra = elytraData.create();
        byte[] chestplateDataBytes = PDCUtils.getPDCValue(armoredElytra, CHESTPLATE_DATA_KEY, PersistentDataType.BYTE_ARRAY);
        ChestplateData chestplateData = ObjectUtils.fromBytes(chestplateDataBytes, ChestplateData.class);
        ItemStack chestplate = chestplateData.create();
        return new ItemStack[] {elytra, chestplate};
    }


    private static void applyChestplateAttributes(ItemBuilder elytraBuilder, ItemStack chestplate) {
        double armor = ChestplateUtils.getDefaultArmor(chestplate);
        double toughness = ChestplateUtils.getDefaultArmorToughness(chestplate);
        double kbResistance = ChestplateUtils.getDefaultKnockBackResistance(chestplate);
        AttributeModifier kbModifier = new AttributeModifier(UUID.randomUUID(), "base_kbresistance", kbResistance, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        AttributeModifier toughnessModifier = new AttributeModifier(UUID.randomUUID(), "base_toughness", toughness, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        AttributeModifier armorModifier = new AttributeModifier(UUID.randomUUID(), "base_armor", armor, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST);
        elytraBuilder.addAttributeModifier(Attribute.GENERIC_ARMOR, armorModifier);
        elytraBuilder.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, toughnessModifier);
        elytraBuilder.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, kbModifier);
        ItemMeta meta = chestplate.getItemMeta();
        if (meta != null) {
            Multimap<Attribute, AttributeModifier> modifiers = meta.getAttributeModifiers();
            if (modifiers != null)
                modifiers.forEach(((attribute, attributeModifier) -> elytraBuilder.addAttributeModifier(attribute, attributeModifier)));
        }
    }

    public static Collection<CustomEnchant> getCustomEnchants() {
        return CUSTOM_ENCHANTS;
    }

}
