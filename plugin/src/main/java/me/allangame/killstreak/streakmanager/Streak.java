package me.allangame.killstreak.streakmanager;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class Streak {

    private final HashMap<String, Integer> streakList;

    public Streak() {
        streakList = new HashMap<String, Integer>();
    }

    /**
     *
     * @return streakList The Streak map
     */
    public HashMap<String, Integer> getStreakList() {
        return streakList;
    }


    /**
     *
     * @param p The player
     * @param s The streak
     */
    public void set(Player p, Integer s) {
        getStreakList().put(p.getUniqueId().toString(), s);
    }

    /**
     *
     * @param p The player
     * @return The current streak
     */
    public Integer getStreak(Player p) {
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
    public void increment(Player p) {
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
    public void reduce(Player p) {
        if(this.getStreak(p) > 0) {
            this.getStreakList().put(p.getUniqueId().toString(), this.getStreak(p)-1);
        }
    }

    /**
     *
     * @param p The player
     */
    public void reset(Player p) {
        this.getStreakList().put(p.getUniqueId().toString(), 0);
    }


}
