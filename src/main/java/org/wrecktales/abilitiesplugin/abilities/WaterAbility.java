package org.wrecktales.abilitiesplugin.abilities;

import org.bukkit.Material;

public class WaterAbility implements Ability
{
    @Override
    public String getName() { return "Water"; }

    @Override
    public int getSlot()
    {
        return 11;
    }

    @Override
    public int getLength() { return 5; }

    @Override
    public Material getMaterial()
    {
        return Material.WATER;
    }

    @Override
    public Material getIcon()
    {
        return Material.WATER_BUCKET;
    }
}