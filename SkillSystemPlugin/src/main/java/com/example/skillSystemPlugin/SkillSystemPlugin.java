package com.example.skillSystemPlugin;

import org.bukkit.plugin.java.JavaPlugin;

public class SkillSystemPlugin extends JavaPlugin {

    private static SkillSystemPlugin instance;
    private SkillManager skillManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        this.skillManager = new SkillManager(this); // <-- передаём ссылку на плагин
        skillManager.loadData();

        getServer().getPluginManager().registerEvents(new SkillGuiListener(), this);
        getServer().getPluginManager().registerEvents(new IntelligenceListener(skillManager), this);
        getServer().getPluginManager().registerEvents(new CraftingListener(skillManager), this);
        getServer().getPluginManager().registerEvents(new CookingListener(skillManager), this);
        getServer().getPluginManager().registerEvents(new BuildingListener(skillManager), this);
        getServer().getPluginManager().registerEvents(new WandererListener(skillManager), this);
        getServer().getPluginManager().registerEvents(new VitalityListener(skillManager), this);
        getServer().getPluginManager().registerEvents(new ItemRestrictionListener(skillManager), this);
        getServer().getPluginManager().registerEvents(new SkillListener(skillManager), this);
        getServer().getPluginManager().registerEvents(new CombatListener(skillManager), this);

        getCommand("skills").setExecutor(new SkillCommand(skillManager));

        getLogger().info("SkillSystemPlugin включён!");
    }

    @Override
    public void onDisable() {
        skillManager.saveData();
    }

    public static SkillSystemPlugin getInstance() {
        return instance;
    }

    public SkillManager getSkillManager() {
        return skillManager;
    }
}