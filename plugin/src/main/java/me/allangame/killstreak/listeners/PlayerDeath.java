package me.allangame.killstreak.listeners;

import me.allangame.killstreak.KillStreak;
import me.allangame.killstreak.streakmanager.StreakList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Objects;


public class PlayerDeath implements Listener {

    private final StreakList streakList = KillStreak.getList();
    private final KillStreak instance = KillStreak.getInstance();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        Player killer = player.getKiller();

        if(killer == null) return;

        streakList.increment(killer);
        streakList.reset(player);


        // Broadcast if the player reached a multiple of 5 kill streak
        if(streakList.getStreak(killer) % 5 == 0 &&
                Objects.equals(instance.getConfig().getString("config.broadcast_when"), "MULTIPLE_OF_5")) {
                Bukkit.broadcastMessage(Objects.requireNonNull(
                        instance.getConfig().getString("config.messages.streak_broadcast"))
                        .replace("%player%", killer.getDisplayName())
                        .replace("%streak%", streakList.getStreak(killer)+""));
        }


        // Rewards

        if(!instance.getConfig().getBoolean("config.rewards.enabled")) return;
        // Rewards aren't finished yet.
        if(!instance.getConfig().contains("config.reward.default_reward")) return;

        Bukkit.getServer().dispatchCommand(
                Bukkit.getServer().getConsoleSender(),
                Objects.requireNonNull(instance.getConfig().getString("config.rewards.default_reward.console")
                .replace("%killer_name%", killer.getDisplayName())
                .replace("%dead_player_name", player.getDisplayName())
                .replace("%killer_streak%", streakList.getStreak(killer)+"")
                )
        );

        killer.sendMessage(
                Objects.requireNonNull(
                        instance.getConfig().getString("config.rewards.default_reward.message_to_killer")
                        .replace("%killer_name%", killer.getDisplayName())
                        .replace("%dead_player_name%", player.getDisplayName())
                        .replace("%killer_streak%", streakList.getStreak(killer)+"")
                )
        );
    }
}
