package me.developful.abilitiesplugin.menus;

import me.developful.abilitiesplugin.AbilityMain;
import me.developful.abilitiesplugin.abilities.Ability;
import me.developful.abilitiesplugin.abilities.LavaAbility;
import me.developful.abilitiesplugin.abilities.WaterAbility;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.*;

public class AbilityMenu implements CommandExecutor
{
    public static int[] outline = {0, 1, 2, 3, 4, 5, 6, 7, 8, 17, 26, 35, 44, 53, 52, 51, 50, 49, 48, 47, 46, 45, 36, 27, 18, 9};
    public static final int EXIT_SLOT = 49;
    public static final HashMap<Integer, Ability> SLOTS = new HashMap<>()
    {{
        put(10, new LavaAbility());
        put(11, new WaterAbility());
    }};

    public static final HashMap<String, Ability> NAMES = new HashMap<>()
    {{
        put("Lava", new LavaAbility());
        put("Water", new WaterAbility());
    }};

    AbilityMain plugin;

    public AbilityMenu(AbilityMain instance)
    {
        plugin = instance;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args)
    {
        if (commandSender instanceof Player p)
        {
            if (args.length > 0)
            {
                return false;
            }

            createAbilityMenu(p);
        }

        return false;
    }

    public void createAbilityMenu(Player p)
    {
        ArrayList<String> lore = new ArrayList<>();
        Inventory inv = Bukkit.createInventory(null, 54, Component.text("§9§lAbilities Menu"));
        for (int j : outline)
        {
            inv.setItem(j, newItem(Material.GRAY_STAINED_GLASS_PANE, " "));
        }

        for (Map.Entry<Integer, Ability> map : SLOTS.entrySet())
        {
            Ability abil = map.getValue();
            Integer slot = map.getKey();

            lore.add(getAppropriateLore(p, abil));

            inv.setItem(slot, newItem(abil.getIcon(), abil.getName(), lore));
            lore.clear();
        }

        inv.setItem(EXIT_SLOT, newItem(Material.BARRIER, "§c§lClose"));
        p.openInventory(inv);
    }

    private String getAppropriateLore(Player p, Ability inventoryAbility)
    {
        Ability currentAbility = plugin.CurrentAbility.get(p);

        if (currentAbility == null)
        {
            return "Click to enable the " + inventoryAbility.getName().toLowerCase() + " ability.";
        }

        if (currentAbility.getSlot() != inventoryAbility.getSlot())
        {
            return "Click to enable the " + inventoryAbility.getName().toLowerCase() + " ability.";
        }

        return inventoryAbility.getName() + " ability is already enabled.";
    }


    private ItemStack newItem(Material mat, String name)
    {
        ItemStack item = new ItemStack(mat);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(Component.text(name));
        item.setItemMeta(itemMeta);
        return item;
    }


    private ItemStack newItem(Material mat, String name, List<String> lore)
    {
        ItemStack item = new ItemStack(mat);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(Component.text(name));
        itemMeta.lore(lore.stream().map(Component::text).toList());
        item.setItemMeta(itemMeta);
        return item;
    }
}
