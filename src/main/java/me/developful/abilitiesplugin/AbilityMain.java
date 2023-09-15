package me.developful.abilitiesplugin;

import me.developful.abilitiesplugin.IO.Reader;
import me.developful.abilitiesplugin.abilities.Ability;
import me.developful.abilitiesplugin.events.WalkEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import me.developful.abilitiesplugin.menus.AbilityMenu;
import me.developful.abilitiesplugin.events.AbilityMenuEvents;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

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
