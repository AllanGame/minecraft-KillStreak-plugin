package me.allangame.killstreak;

import me.allangame.killstreak.commands.StreakCommand;
import me.allangame.killstreak.commands.StreakAdminCommand;
import me.allangame.killstreak.listeners.PlayerDeath;
import me.allangame.killstreak.placeholderapi.KillStreakExpansion;
import me.allangame.killstreak.streakmanager.StreakList;
import me.allangame.killstreak.utils.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class KillStreak extends JavaPlugin {

    private Configuration config;
    private static KillStreak instance;
    private static StreakList streakList;


    @Override
    public void onEnable() {
        instance = this;

        streakList = new StreakList();
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new KillStreakExpansion().register();
        } else {
            Bukkit.getLogger().info("Placeholder is not detected, if you want to use the killstreak variables install PlaceholderAPI.");
        }

        setupCommands();
        setupConfiguration();
        setupListeners();
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    private void setupConfiguration(){
        this.config = new Configuration(this, "config");
    }

    private void setupCommands(){
        getCommand("streak").setExecutor(new StreakCommand());
        getCommand("streakadmin").setExecutor(new StreakAdminCommand());
    }

    private void setupListeners(){
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerDeath(), this);
    }

    public static StreakList getList() {
        return streakList;
    }

    public Configuration getConfig() {
        return this.config;
    }

    public static KillStreak getInstance() {
        return instance;
    }
}
