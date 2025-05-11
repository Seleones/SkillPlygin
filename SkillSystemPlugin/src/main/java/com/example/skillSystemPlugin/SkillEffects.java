package com.example.skillSystemPlugin;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.*;

import java.util.Random;

public class SkillEffects {

    private static final Random random = new Random();

    public static void applyLevelUpEffect(Player player, SkillType skill, int level) {
        switch (skill) {
            case WOODCUTTING -> {
                if (level <= 50) {
                    if (random.nextDouble() <= level * 0.02) { // шанс
                        player.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 2, 2));
                    }
                }
            }
            case MINING -> {
                if (level <= 80) {
                    if (random.nextDouble() <= level * 0.02) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 2, 2));
                    }
                    if (level % 5 == 0 && random.nextDouble() <= 0.0025 * level) {
                        player.sendMessage("§aВы получили дополнительную руду!");
                        player.getInventory().addItem(new ItemStack(Material.DIAMOND));
                    }
                    if (level % 10 == 0 && random.nextDouble() <= 0.005 * level) {
                        player.sendMessage("§aВаша кирка избежала поломки!");

                    }
                }
            }
            case COMBAT -> {
                if (level % 5 == 0) {

                }
                if (level % 15 == 0) {
                }
            }
        }
    }
}
