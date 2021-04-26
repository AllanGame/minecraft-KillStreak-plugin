package me.allangame.killstreak.listeners;

import me.allangame.killstreak.KillStreak;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.HashMap;
import java.util.Objects;


public class PlayerDeath implements Listener {


    HashMap<String, Integer> StreakList = KillStreak.getStreakList();
    KillStreak instance = KillStreak.getInstance();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        Player killer = player.getKiller();

        // if killer is a player
        if(killer != null) {
            if (!StreakList.containsKey(killer.getUniqueId().toString())) {
                StreakList.put(killer.getUniqueId().toString(), 1);
            } else {
                StreakList.put(killer.getUniqueId().toString(), StreakList.get(killer.getUniqueId().toString()) + 1);
            }

            if(Objects.equals(instance.getConfig().getString("config.broadcast_when"), "MULTIPLE_OF_5")) {
                if(StreakList.get(killer.getUniqueId().toString()) % 5 == 0) {
                    Bukkit.broadcastMessage(Objects.requireNonNull(instance.getConfig().getString("config.messages.streak_broadcast"))
                            .replace("%player%", killer.getDisplayName())
                            .replace("%streak%", StreakList.get(killer.getUniqueId().toString())+""));
                }
            }
        }

        // restart player streak
        StreakList.put(player.getUniqueId().toString(), 0);
    }
}
