package me.developful.abilitiesplugin.abilities;

import org.bukkit.Material;

public class LavaAbility implements Ability
{
    @Override
    public String getName() { return "Lava"; }

    @Override
    public int getSlot() { return 10; }

    @Override
    public int getLength() { return 3; }

    @Override
    public Material getMaterial()
    {
        return Material.MAGMA_BLOCK;
    }

    @Override
    public Material getIcon()
    {
        return Material.MAGMA_BLOCK;
    }
}
