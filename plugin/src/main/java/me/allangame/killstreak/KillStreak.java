package me.allangame.killstreak;

import me.allangame.killstreak.commands.StreakCommand;
import me.allangame.killstreak.listeners.PlayerDeath;
import me.allangame.killstreak.utils.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class KillStreak extends JavaPlugin {

    private Configuration config;
    private static KillStreak instance;
    private static HashMap<String, Integer> StreakList = new HashMap<String, Integer>();


    @Override
    public void onEnable() {
        instance = this;

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
    }

    private void setupListeners(){
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerDeath(), this);
    }

    public static HashMap getStreakList() {
        return StreakList;
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    public static KillStreak getInstance() {
        return instance;
    }
}
