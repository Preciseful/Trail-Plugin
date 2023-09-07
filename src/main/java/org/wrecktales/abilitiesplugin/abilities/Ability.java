package org.wrecktales.abilitiesplugin.abilities;

import org.bukkit.Material;

public interface Ability
{
    String getName();

    int getSlot();

    int getLength();

    Material getMaterial();

    Material getIcon();
}
