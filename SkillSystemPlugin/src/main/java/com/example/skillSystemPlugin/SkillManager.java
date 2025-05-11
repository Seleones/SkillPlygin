package com.example.skillSystemPlugin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class SkillManager {

    private final Map<UUID, Map<SkillType, Integer>> xp = new HashMap<>();
    private final Map<UUID, Map<SkillType, Integer>> levels = new HashMap<>();

    private final File dataFile;
    private FileConfiguration dataConfig;

    private final JavaPlugin plugin;

    public SkillManager(JavaPlugin plugin) {
        this.plugin = plugin;

        dataFile = new File(plugin.getDataFolder(), "data.yml");

        if (!dataFile.exists()) {
            try {
                dataFile.getParentFile().mkdirs();
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
        loadData();
    }

    public void setLevel(Player player, SkillType skill, int level) {
        UUID uuid = player.getUniqueId();
        levels.putIfAbsent(uuid, new EnumMap<>(SkillType.class));
        xp.putIfAbsent(uuid, new EnumMap<>(SkillType.class));
        levels.get(uuid).put(skill, level);
        xp.get(uuid).put(skill, 0);
        saveData(uuid);
    }

    public void addXP(Player player, SkillType skill, int amount) {
        UUID uuid = player.getUniqueId();
        xp.putIfAbsent(uuid, new EnumMap<>(SkillType.class));
        levels.putIfAbsent(uuid, new EnumMap<>(SkillType.class));

        Map<SkillType, Integer> playerXP = xp.get(uuid);
        Map<SkillType, Integer> playerLevels = levels.get(uuid);

        int currentXP = playerXP.getOrDefault(skill, 0) + amount;
        int currentLevel = playerLevels.getOrDefault(skill, 1);
        int xpToLevel = getXPToLevel(skill, currentLevel);

        while (currentXP >= xpToLevel) {
            currentXP -= xpToLevel;
            currentLevel++;
            player.sendMessage("§aПовышен уровень навыка " + skill + "! Новый уровень: " + currentLevel);
            SkillEffects.applyLevelUpEffect(player, skill, currentLevel);
            xpToLevel = getXPToLevel(skill, currentLevel);
        }

        playerXP.put(skill, currentXP);
        playerLevels.put(skill, currentLevel);
        saveData();
    }

    public int getXPToLevel(SkillType skill, int level) {
        switch (skill) {
            case WOODCUTTING: return (int) (50 + (level - 1) * 0.5);
            case MINING: return (int) (30 + (level - 1) * 0.5);
            case COMBAT: return (int) (30 + (level - 1) * 1.0);
            case VITALITY: return (int) (20 * Math.pow(0.5, level - 1));
            case WANDERER: return (int) (40 * Math.pow(0.5, level - 1));
            case INTELLECT: return (int) (20 * Math.pow(0.5, level - 1));
            case CRAFTING: return (int) (100 * Math.pow(0.5, level - 1));
            case COOKING: return (int) (50 * Math.pow(0.5, level - 1));
            case BUILDING: return (int) (150 * Math.pow(0.5, level - 1));
            default: return 100;
        }
    }

    public int getLevel(Player player, SkillType skill) {
        return levels.getOrDefault(player.getUniqueId(), new EnumMap<>(SkillType.class)).getOrDefault(skill, 1);
    }

    public int getXP(Player player, SkillType skill) {
        return xp.getOrDefault(player.getUniqueId(), new EnumMap<>(SkillType.class)).getOrDefault(skill, 0);
    }

    public void saveData() {
        for (UUID uuid : xp.keySet()) {
            for (SkillType skill : SkillType.values()) {
                dataConfig.set(uuid + ".xp." + skill.name(), xp.get(uuid).getOrDefault(skill, 0));
                dataConfig.set(uuid + ".level." + skill.name(), levels.get(uuid).getOrDefault(skill, 1));
            }
        }
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveData(UUID uuid) {
        for (SkillType skill : SkillType.values()) {
            dataConfig.set(uuid + ".xp." + skill.name(), xp.get(uuid).getOrDefault(skill, 0));
            dataConfig.set(uuid + ".level." + skill.name(), levels.get(uuid).getOrDefault(skill, 1));
        }
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        for (String uuidStr : dataConfig.getKeys(false)) {
            UUID uuid = UUID.fromString(uuidStr);
            Map<SkillType, Integer> xpMap = new EnumMap<>(SkillType.class);
            Map<SkillType, Integer> levelMap = new EnumMap<>(SkillType.class);
            for (SkillType skill : SkillType.values()) {
                xpMap.put(skill, dataConfig.getInt(uuidStr + ".xp." + skill.name(), 0));
                levelMap.put(skill, dataConfig.getInt(uuidStr + ".level." + skill.name(), 1));
            }
            xp.put(uuid, xpMap);
            levels.put(uuid, levelMap);
        }
    }
}

