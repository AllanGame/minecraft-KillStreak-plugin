package me.allangame.killstreak.streakmanager;

import com.sun.istack.internal.NotNull;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Streak {

    private final HashMap<String, Integer> StreakList;

    public Streak() {
        StreakList = new HashMap<String, Integer>();
    }

    /**
     *
     * @return StreakList The Streak map
     */
    public HashMap<String, Integer> getStreakList() {
        return StreakList;
    }


    /**
     *
     * @param p The player
     * @param s The streak
     */
    public void set(@NotNull Player p, Integer s) {
        getStreakList().put(p.getUniqueId().toString(), s);
    }

    /**
     *
     * @param p The player
     * @return The current streak
     */
    public Integer getStreak(@NotNull Player p) {
        Integer currentStreak = this.getStreakList().get(p.getUniqueId().toString());
        if(currentStreak == null) {
            this.set(p, 0);
            currentStreak = 0;
        }
        return currentStreak;
    }

    /**
     *
     * @param p The Player
     */
    public void increment(@NotNull Player p) {
        Integer currentStreak = this.getStreakList().get(p.getUniqueId().toString());
        if(currentStreak == null) {
            this.set(p, 0);
            currentStreak = 0;
        }
        this.getStreakList().put(p.getUniqueId().toString(), currentStreak+1);
    }

    /**
     *
     * @param p The player
     */
    public void reduce(@NotNull Player p) {
        if(this.getStreak(p) > 0) {
            this.getStreakList().put(p.getUniqueId().toString(), this.getStreak(p)-1);
        }
    }

    /**
     *
     * @param p The player
     */
    public void reset(@NotNull Player p) {
        this.getStreakList().put(p.getUniqueId().toString(), 0);
    }


}
