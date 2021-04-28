package me.allangame.killstreak;

import me.allangame.killstreak.commands.StreakCommand;
import me.allangame.killstreak.commands.streakAdminCommand;
import me.allangame.killstreak.listeners.PlayerDeath;
import me.allangame.killstreak.streakmanager.Streak;
import me.allangame.killstreak.utils.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class KillStreak extends JavaPlugin {

    private Configuration config;
    private static KillStreak instance;
    private static Streak streakList;

    @Override
    public void onEnable() {
        instance = this;

        streakList = new Streak();
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
        getCommand("streakadmin").setExecutor(new streakAdminCommand());
    }

    private void setupListeners(){
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerDeath(), this);
    }

    public static Streak getList() {
        return streakList;
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    public static KillStreak getInstance() {
        return instance;
    }
}
