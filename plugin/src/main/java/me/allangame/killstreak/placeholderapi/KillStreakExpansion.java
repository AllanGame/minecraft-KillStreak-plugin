package me.allangame.killstreak.placeholderapi;

import me.allangame.killstreak.KillStreak;
import me.allangame.killstreak.streakmanager.StreakList;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class KillStreakExpansion extends PlaceholderExpansion {

    private final StreakList streakList = KillStreak.getList();

    @Override
    public String getIdentifier() {
        return "killstreak";
    }

    @Override
    public String getAuthor() {
        return "AllanGame";
    }

    @Override
    public String getVersion() {
        return "1.0.1";
    }

    @Override
    public String onRequest(OfflinePlayer op, String identifier) {
        Player player = op.getPlayer();

        if(player == null) {
            return "";
        }

        switch (identifier.toLowerCase()) {
            case "streak": {
                return streakList.getStreak(player) + "";
            }
            case "test": {
                return "Working.";
            }
        }

        return null;
    }

}