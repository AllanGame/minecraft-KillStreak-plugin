package me.allangame.killstreak.listeners;

import me.allangame.killstreak.KillStreak;
import me.allangame.killstreak.streakmanager.Streak;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;
import java.util.Objects;


public class PlayerDeath implements Listener {


    Streak StreakList = KillStreak.getList();
    KillStreak instance = KillStreak.getInstance();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        Player killer = player.getKiller();

        // if killer is a player
        if(killer != null) {
            StreakList.increment(killer);
            if(Objects.equals(instance.getConfig().getString("config.broadcast_when"), "MULTIPLE_OF_5")) {
                if(StreakList.getStreak(killer) % 5 == 0) {
                    Bukkit.broadcastMessage(Objects.requireNonNull(instance.getConfig().getString("config.messages.streak_broadcast"))
                            .replace("%player%", killer.getDisplayName())
                            .replace("%streak%", StreakList.getStreak(killer)+""));
                }
            }
        }

        // reset dead player streak
        StreakList.reset(player);
    }
}
