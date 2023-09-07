package org.wrecktales.abilitiesplugin;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.wrecktales.abilitiesplugin.IO.Reader;
import org.wrecktales.abilitiesplugin.abilities.Ability;
import org.wrecktales.abilitiesplugin.menus.AbilityMenu;
import org.wrecktales.abilitiesplugin.events.AbilityMenuEvents;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.wrecktales.abilitiesplugin.events.WalkEvent;

import java.util.HashMap;

public final class AbilityMain extends JavaPlugin
{
    public HashMap<Player, Ability> CurrentAbility = new HashMap<>();
    public Reader reader;

    @Override
    public void onEnable()
    {
        //Events
        reader = Reader.create("./abilityData.yml", "./abilityData.yml");

        Bukkit.getServer().getPluginManager().registerEvents(new WalkEvent(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new AbilityMenuEvents(this), this);

        //Commands
        getCommand("abilitymenu").setExecutor(new AbilityMenu(this));

        //Debug
        Bukkit.getConsoleSender().sendMessage(Component.text("§9§lAbilities Plugin Enabled"));
        Bukkit.getConsoleSender().sendMessage(Component.text("§c§lALSO THIS IS A PROTOTYPE"));
    }

    @Override
    public void onDisable()
    {
        // Plugin shutdown logic
    }
}
