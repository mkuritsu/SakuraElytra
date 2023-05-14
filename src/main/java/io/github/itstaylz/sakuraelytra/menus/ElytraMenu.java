package io.github.itstaylz.sakuraelytra.menus;

import io.github.itstaylz.hexlib.items.ItemBuilder;
import io.github.itstaylz.hexlib.menu.Menu;
import io.github.itstaylz.hexlib.menu.MenuSettings;
import io.github.itstaylz.hexlib.menu.components.Button;
import io.github.itstaylz.hexlib.utils.StringUtils;
import io.github.itstaylz.sakuraelytra.SakuraElytraPlugin;
import io.github.itstaylz.sakuraelytra.utils.MenuUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ElytraMenu extends Menu {

    private static final MenuSettings SETTINGS = MenuSettings.builder()
            .withNumberOfRows(3)
            .withTitle(StringUtils.colorize("&dSakura Elytra"))
            .build();

    private static final ItemStack ANVIL = new ItemBuilder(Material.ANVIL)
            .setDisplayName(StringUtils.colorize("&dCombine"))
            .setLore("", StringUtils.colorize("&7Left-Click to open the combination menu"))
            .build();

    private static final ItemStack GRINDSTONE = new ItemBuilder(Material.GRINDSTONE)
            .setDisplayName(StringUtils.colorize("&bSeparation"))
            .setLore("", StringUtils.colorize("&7Left-Click to open the separation menu"))
            .build();

    public ElytraMenu(SakuraElytraPlugin plugin) {
        super(SETTINGS);
        MenuUtils.fillGlassPanes(this);
        setComponent(12, new Button(ANVIL, (menu, player, event) -> {
            new CombinationMenu(plugin).open(player);
        }));
        setComponent(14, new Button(GRINDSTONE, (menu, player, event) -> {
            new SeparationMenu(plugin).open(player);
        }));
    }
}
