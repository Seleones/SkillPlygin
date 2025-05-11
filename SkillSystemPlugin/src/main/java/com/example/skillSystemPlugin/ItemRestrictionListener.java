package com.example.skillSystemPlugin;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ItemRestrictionListener implements Listener {

    private final SkillManager skillManager;

    public ItemRestrictionListener(SkillManager skillManager) {
        this.skillManager = skillManager;
    }

    @EventHandler
    public void onItemUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null || item.getType() == Material.AIR) return;

        Material type = item.getType();
        SkillType skill = null;
        int requiredLevel = 0;

        switch (type) {
            case WOODEN_AXE -> { skill = SkillType.WOODCUTTING; requiredLevel = 1; }
            case STONE_AXE -> { skill = SkillType.WOODCUTTING; requiredLevel = 1; }
            case GOLDEN_AXE -> { skill = SkillType.WOODCUTTING; requiredLevel = 16; }
            case IRON_AXE -> { skill = SkillType.WOODCUTTING; requiredLevel = 21; }
            case DIAMOND_AXE -> { skill = SkillType.WOODCUTTING; requiredLevel = 31; }
            case NETHERITE_AXE -> { skill = SkillType.WOODCUTTING; requiredLevel = 41; }

            case WOODEN_PICKAXE -> { skill = SkillType.MINING; requiredLevel = 1; }
            case STONE_PICKAXE -> { skill = SkillType.MINING; requiredLevel = 1; }
            case GOLDEN_PICKAXE -> { skill = SkillType.MINING; requiredLevel = 26; }
            case IRON_PICKAXE -> { skill = SkillType.MINING; requiredLevel = 36; }
            case DIAMOND_PICKAXE -> { skill = SkillType.MINING; requiredLevel = 51; }
            case NETHERITE_PICKAXE -> { skill = SkillType.MINING; requiredLevel = 71; }

            case WOODEN_SWORD -> { skill = SkillType.COMBAT; requiredLevel = 1; }
            case STONE_SWORD -> { skill = SkillType.COMBAT; requiredLevel = 1; }
            case GOLDEN_SWORD -> { skill = SkillType.COMBAT; requiredLevel = 31; }
            case IRON_SWORD -> { skill = SkillType.COMBAT; requiredLevel = 41; }
            case DIAMOND_SWORD -> { skill = SkillType.COMBAT; requiredLevel = 81; }
            case NETHERITE_SWORD -> { skill = SkillType.COMBAT; requiredLevel = 111; }

            case ENCHANTING_TABLE -> { skill = SkillType.INTELLECT; requiredLevel = 5; }

            case LEATHER_HELMET-> { skill = SkillType.VITALITY; requiredLevel = 1; }
            case LEATHER_CHESTPLATE-> { skill = SkillType.VITALITY; requiredLevel = 1; }
            case LEATHER_LEGGINGS-> { skill = SkillType.VITALITY; requiredLevel = 1; }
            case LEATHER_BOOTS-> { skill = SkillType.VITALITY; requiredLevel = 1; }

            case GOLDEN_HELMET-> { skill = SkillType.VITALITY; requiredLevel = 6; }
            case GOLDEN_CHESTPLATE-> { skill = SkillType.VITALITY; requiredLevel = 6; }
            case GOLDEN_LEGGINGS-> { skill = SkillType.VITALITY; requiredLevel = 6; }
            case GOLDEN_BOOTS-> { skill = SkillType.VITALITY; requiredLevel = 6; }

            case IRON_HELMET-> { skill = SkillType.VITALITY; requiredLevel = 11; }
            case IRON_CHESTPLATE-> { skill = SkillType.VITALITY; requiredLevel = 11; }
            case IRON_LEGGINGS-> { skill = SkillType.VITALITY; requiredLevel = 11; }
            case IRON_BOOTS-> { skill = SkillType.VITALITY; requiredLevel = 11; }

            case DIAMOND_HELMET-> { skill = SkillType.VITALITY; requiredLevel = 16; }
            case DIAMOND_CHESTPLATE-> { skill = SkillType.VITALITY; requiredLevel = 16; }
            case DIAMOND_LEGGINGS-> { skill = SkillType.VITALITY; requiredLevel = 16; }
            case DIAMOND_BOOTS-> { skill = SkillType.VITALITY; requiredLevel = 16; }

            case NETHERITE_HELMET-> { skill = SkillType.VITALITY; requiredLevel = 26; }
            case NETHERITE_CHESTPLATE-> { skill = SkillType.VITALITY; requiredLevel = 26; }
            case NETHERITE_LEGGINGS-> { skill = SkillType.VITALITY; requiredLevel = 26; }
            case NETHERITE_BOOTS-> { skill = SkillType.VITALITY; requiredLevel = 26; }

        }

        if (skill != null) {
            int playerLevel = skillManager.getLevel(player, skill);
            if (playerLevel < requiredLevel) {
                event.setCancelled(true);
                player.sendMessage("§cВы не можете использовать этот предмет. Требуется уровень " + requiredLevel + " в навыке " + skill.name());
            }
        }
    }
}
