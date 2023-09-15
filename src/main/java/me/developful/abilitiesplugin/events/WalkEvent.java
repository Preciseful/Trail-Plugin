package me.developful.abilitiesplugin.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.NotNull;
import me.developful.abilitiesplugin.AbilityMain;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;
import me.developful.abilitiesplugin.abilities.Ability;
import me.developful.abilitiesplugin.menus.AbilityMenu;

import java.util.*;

class ChangedAbilityBlock {
    public Block Block;
    public ArrayList<Material> PreviousMaterials = new ArrayList<>();

    public ChangedAbilityBlock(Block block) {
        PreviousMaterials.add(block.getType());
        Block = block;
    }
}

public class WalkEvent implements Listener
{
    AbilityMain plugin;
    private final HashMap<Player, List<ChangedAbilityBlock>> BlocksWalkedOnByPlayer = new HashMap<>();
    private final HashMap<Block, ChangedAbilityBlock> SingleBlocksWalkedOn = new HashMap<>();

    public WalkEvent(AbilityMain instance)
    {
        plugin = instance;
    }

    @EventHandler
    public void joinEvent(PlayerJoinEvent e)
    {
        Player p = e.getPlayer();
        if (!plugin.reader.data.containsKey(p.getName()))
        {
            plugin.CurrentAbility.put(p, null);
        }
        else
        {
            plugin.CurrentAbility.put(p, AbilityMenu.NAMES.get(plugin.reader.data.get(p.getName())));
        }

        BlocksWalkedOnByPlayer.put(p, new ArrayList<>());
    }

    @EventHandler
    public void walkEvent(PlayerMoveEvent e)
    {
        if (!e.hasChangedBlock())
        {
            return;
        }

        Block blockTo = e.getFrom().getBlock().getRelative(0, -1, 0);
        addBlock(e.getPlayer(), blockTo, plugin.CurrentAbility.get(e.getPlayer()));
    }

    private void addBlock(@NotNull Player player, @NotNull Block block, Ability ability)
    {
        if (ability == null)
        {
            return;
        }

        List<ChangedAbilityBlock> walkedOn = BlocksWalkedOnByPlayer.get(player);

        if (ability.getLength() < walkedOn.size())
        {
            remove(walkedOn);
        }

        if (block.getType() == Material.AIR || player.isFlying() || player.isSwimming())
        {
            walkedOn.add(null);
            return;
        }

        ChangedAbilityBlock abilityBlock;
        if (SingleBlocksWalkedOn.containsKey(block))
        {
            abilityBlock = SingleBlocksWalkedOn.get(block);
        }
        else
        {
            abilityBlock = new ChangedAbilityBlock(block);
            SingleBlocksWalkedOn.put(block, abilityBlock);
        }

        walkedOn.add(abilityBlock);

        abilityBlock.PreviousMaterials.add(ability.getMaterial());
        block.setType(ability.getMaterial());
    }

    private void remove(@NotNull List<ChangedAbilityBlock> list)
    {
        if (list.get(0) == null)
        {
            list.remove(0);
            return;
        }

        Block block = list.get(0).Block;
        ChangedAbilityBlock changedAbilityBlock = SingleBlocksWalkedOn.get(block);

        ArrayList<Material> PreviousMaterials = changedAbilityBlock.PreviousMaterials;
        Optional<Material> material = PreviousMaterials.stream().filter(m -> m == block.getType()).findFirst();

        material.ifPresent(PreviousMaterials::remove);
        changedAbilityBlock.Block.setType(PreviousMaterials.get(PreviousMaterials.size() - 1));

        if (PreviousMaterials.size() == 1)
        {
            SingleBlocksWalkedOn.remove(block);
        }

        list.remove(0);
    }
}