package io.github.itstaylz.sakuraelytra.utils;

import io.github.itstaylz.hexlib.items.ItemBuilder;
import io.github.itstaylz.hexlib.menu.Menu;
import io.github.itstaylz.hexlib.menu.components.Label;
import io.github.itstaylz.hexlib.menu.handlers.IMenuClickHandler;
import io.github.itstaylz.hexlib.menu.handlers.IMenuCloseHandler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuUtils {

    private static final ItemStack PURPLE_GLASS_PANE = new ItemBuilder(Material.PURPLE_STAINED_GLASS_PANE)
            .setDisplayName(" ")
            .build();

    private static final ItemStack CYAN_GLASS_PANE = new ItemBuilder(Material.CYAN_STAINED_GLASS_PANE)
            .setDisplayName(" ")
            .build();

    private static final ItemStack BLACK_GLASS_PANE = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)
            .setDisplayName(" ")
            .build();

    public static void fillGlassPanes(Menu menu) {
        int size = menu.getInventory().getSize();
        for (int i = 0; i < size; i++) {
            if (i == 9)
                i = size - 9;
            if (i % 2 == 0)
                menu.setComponent(i, new Label(PURPLE_GLASS_PANE));
            else
                menu.setComponent(i, new Label(CYAN_GLASS_PANE));
        }
        for (int i = 9; i < size - 9; i++) {
            menu.setComponent(i, new Label(BLACK_GLASS_PANE));
        }
    }


    public static final IMenuClickHandler NO_SHIFT_CLICK_HANDLER = (Menu menu, Player player, InventoryClickEvent event) -> {
        Inventory inv = event.getClickedInventory();
        if (inv != null && !(inv.getHolder() instanceof Menu) && event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY)
            event.setCancelled(true);
    };

    public static final IMenuCloseHandler MENUS_CLOSE_HANDLER = (Menu menu, Player player) -> {
        List<ItemStack> items = new ArrayList<>();
        ItemStack armored = menu.getInventory().getItem(22);
        ItemStack elytra = menu.getInventory().getItem(30);
        ItemStack chestplate = menu.getInventory().getItem(32);
        if (armored != null)
            items.add(armored);
        if (elytra != null)
            items.add(elytra);
        if (chestplate != null)
            items.add(chestplate);
        HashMap<Integer, ItemStack> itemsToDrops = player.getInventory().addItem(items.toArray(new ItemStack[0]));
        for (ItemStack item : itemsToDrops.values()) {
            player.getWorld().dropItemNaturally(player.getLocation(), item);
        }
    };
}
