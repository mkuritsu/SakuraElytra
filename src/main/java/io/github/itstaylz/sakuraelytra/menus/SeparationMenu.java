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

public class SeparationMenu extends Menu {

    private static final MenuSettings SETTINGS = MenuSettings.builder()
            .withNumberOfRows(6)
            .withTitle(StringUtils.colorize("&bSeparation Menu"))
            .allowPlayerInventoryClicks(true)
            .withClickHandler(MenuUtils.NO_SHIFT_CLICK_HANDLER)
            .withCloseHandler(MenuUtils.MENUS_CLOSE_HANDLER)
            .build();

    private static final ItemStack NETHER_STAR = new ItemBuilder(Material.NETHER_STAR)
            .setDisplayName(StringUtils.colorize("&bSeparation Menu"))
            .setLore(
                    " ",
                    StringUtils.colorize("&d^ &7Place your &bArmored Elytra &7above"),
                    " ",
                    StringUtils.colorize("&aClick me to separate it!")
            )
            .build();

    private static final ItemStack AIR = new ItemStack(Material.AIR);

    private final SakuraElytraPlugin plugin;
    private final Placeable elytraPlaceable;

    public SeparationMenu(SakuraElytraPlugin plugin) {
        super(SETTINGS);
        this.plugin = plugin;
        MenuUtils.fillGlassPanes(this);
        IMenuClickHandler invalidItemClickHandler = (menu, player, event) -> player.sendMessage(StringUtils.colorize(this.plugin.getMessageConfig().getMessage("invalid_item_message")));
        this.elytraPlaceable = new Placeable(i -> i.getType() == Material.ELYTRA && ElytraUtils.isArmoredElytra(i), invalidItemClickHandler);
        setComponent(31, new Button(NETHER_STAR, this::onSeparateClick));
        setComponent(30, new Label(AIR));
        setComponent(32, new Label(AIR));
        setComponent(22, this.elytraPlaceable);
    }

    private void onSeparateClick(Menu menu, Player player, InventoryClickEvent event) {
        ItemStack armored = menu.getInventory().getItem(22);
        ItemStack elytra = menu.getInventory().getItem(30);
        ItemStack chestplate = menu.getInventory().getItem(32);
        if (armored == null || armored.getType() == Material.AIR) {
            player.sendMessage(StringUtils.colorize(this.plugin.getMessageConfig().getMessage("items_not_placed")));
            return;
        }
        if ((elytra != null && elytra.getType() != Material.AIR) || (chestplate != null && chestplate.getType() != Material.AIR)) {
            player.sendMessage(StringUtils.colorize(this.plugin.getMessageConfig().getMessage("remove_items_message")));
            return;
        }
        ItemStack[] separated = ElytraUtils.separateElytra(armored);
        menu.setComponent(22, this.elytraPlaceable);
        menu.setComponent(30, new Collectable(separated[0], new Label(AIR)));
        menu.setComponent(32, new Collectable(separated[1], new Label(AIR)));
        player.playSound(player, Sound.BLOCK_ANVIL_USE, 1f, 1f);
    }

}
