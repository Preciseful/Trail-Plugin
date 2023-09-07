package org.wrecktales.abilitiesplugin.events;

import net.kyori.adventure.text.Component;
import org.wrecktales.abilitiesplugin.AbilityMain;
import org.wrecktales.abilitiesplugin.abilities.Ability;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.wrecktales.abilitiesplugin.menus.AbilityMenu;

public class AbilityMenuEvents implements Listener
{
    AbilityMain plugin;

    public AbilityMenuEvents(AbilityMain instance)
    {
        plugin = instance;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e)
    {
        Player p = (Player) e.getWhoClicked();

        if (e.getView().title().equals(Component.text("§9§lAbilities Menu")))
        {
            if (e.getSlot() == AbilityMenu.EXIT_SLOT)
            {
                p.closeInventory();
                return;
            }

            Ability ability = AbilityMenu.SLOTS.getOrDefault(e.getSlot(), null);
            if (ability == null)
            {
                e.setCancelled(true);
                return;
            }

            if (plugin.CurrentAbility.get(p) != null && ability == plugin.CurrentAbility.get(p))
            {
                e.setCancelled(true);
                return;
            }

            plugin.CurrentAbility.replace(p, ability);
            plugin.reader.dump(p.getName(), ability.getName());
            p.closeInventory();
        }
    }
}
