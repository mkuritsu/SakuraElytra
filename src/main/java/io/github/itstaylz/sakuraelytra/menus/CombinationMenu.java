package io.github.itstaylz.sakuraelytra.menus;

import io.github.itstaylz.hexlib.items.ItemBuilder;
import io.github.itstaylz.hexlib.menu.Menu;
import io.github.itstaylz.hexlib.menu.MenuSettings;
import io.github.itstaylz.hexlib.menu.components.Button;
import io.github.itstaylz.hexlib.menu.components.Collectable;
import io.github.itstaylz.hexlib.menu.components.Label;
import io.github.itstaylz.hexlib.menu.components.Placeable;
import io.github.itstaylz.hexlib.menu.handlers.IMenuClickHandler;
import io.github.itstaylz.hexlib.utils.StringUtils;
import io.github.itstaylz.sakuraelytra.SakuraElytraPlugin;
import io.github.itstaylz.sakuraelytra.utils.ElytraUtils;
import io.github.itstaylz.sakuraelytra.utils.MenuUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class CombinationMenu extends Menu {

    private static final MenuSettings SETTINGS = MenuSettings.builder()
            .withNumberOfRows(6)
            .withTitle(StringUtils.colorize("&dCombination Menu"))
            .allowPlayerInventoryClicks(true)
            .withClickHandler(MenuUtils.NO_SHIFT_CLICK_HANDLER)
            .withCloseHandler(MenuUtils.MENUS_CLOSE_HANDLER)
            .build();

    private static final ItemStack NETHER_STAR = new ItemBuilder(Material.NETHER_STAR)
            .setDisplayName(StringUtils.colorize("&bSeparation Menu"))
            .setLore(
                    " ",
                    StringUtils.colorize("&d< &7Place your &bElytra &7on the left"),
                    StringUtils.colorize("&7Place your &bChestplate &7on the right &d>"),
                    " ",
                    StringUtils.colorize("&aClick me to combine them!")
            )
            .build();

    private static final ItemStack AIR = new ItemStack(Material.AIR);

    private final SakuraElytraPlugin plugin;
    private final Placeable elytraPlaceable;
    private final Placeable chestplatePlaceable;

    public CombinationMenu(SakuraElytraPlugin plugin) {
        super(SETTINGS);
        this.plugin = plugin;
        MenuUtils.fillGlassPanes(this);
        IMenuClickHandler invalidItemClickHandler = (menu, player, event) -> player.sendMessage(StringUtils.colorize(this.plugin.getMessageConfig().getMessage("invalid_item_message")));
        this.elytraPlaceable = new Placeable(i -> i.getType() == Material.ELYTRA && !ElytraUtils.isArmoredElytra(i), invalidItemClickHandler);
        this.chestplatePlaceable = new Placeable(i -> i.getType().name().contains("CHESTPLATE"), invalidItemClickHandler);

        setComponent(31, new Button(NETHER_STAR, this::onCombineClick));
        setComponent(30, this.elytraPlaceable);
        setComponent(32, this.chestplatePlaceable);
        setComponent(22, new Label(AIR));
    }

    private void onCombineClick(Menu menu, Player player, InventoryClickEvent event) {
        ItemStack armored = menu.getInventory().getItem(22);
        ItemStack elytra = menu.getInventory().getItem(30);
        ItemStack chestplate = menu.getInventory().getItem(32);
        if (elytra == null || elytra.getType() == Material.AIR || chestplate == null || chestplate.getType() == Material.AIR) {
            player.sendMessage(StringUtils.colorize(this.plugin.getMessageConfig().getMessage("items_not_placed")));
            return;
        }
        if (armored != null && armored.getType() != Material.AIR) {
            player.sendMessage(StringUtils.colorize(this.plugin.getMessageConfig().getMessage("remove_items_message")));
            return;
        }
        ItemStack combinedElytra = ElytraUtils.combineElytra(elytra, chestplate);
        menu.setComponent(30, this.elytraPlaceable);
        menu.setComponent(32, this.chestplatePlaceable);
        menu.setComponent(22, new Collectable(combinedElytra, new Label(AIR)));
        player.playSound(player, Sound.BLOCK_ANVIL_USE, 1f, 1f);
    }
}
