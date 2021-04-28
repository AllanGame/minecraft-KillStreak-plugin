package me.allangame.killstreak.streakmanager;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class Streak {

    private final HashMap<String, Integer> StreakList;

    public Streak() {
        StreakList = new HashMap<String, Integer>();
    }

    public HashMap<String, Integer> getStreakList() {
        return StreakList;
    }

    public void set(Player p, Integer s) {
        getStreakList().put(p.getUniqueId().toString(), s);
    }

    public Integer getStreak(Player p) {
        Integer currentStreak = this.getStreakList().get(p.getUniqueId().toString());
        if(currentStreak == null) {
            this.set(p, 0);
            currentStreak = 0;
        }
        return currentStreak;
    }

    public void increment(Player p) {
        Integer currentStreak = this.getStreakList().get(p.getUniqueId().toString());
        if(currentStreak == null) {
            this.set(p, 0);
            currentStreak = 0;
        }
        this.getStreakList().put(p.getUniqueId().toString(), currentStreak+1);
    }

    public void reduce(Player p) {
        if(this.getStreak(p) > 0) {
            this.getStreakList().put(p.getUniqueId().toString(), this.getStreak(p)-1);
        }
    }

    public void reset(Player p) {
        this.getStreakList().put(p.getUniqueId().toString(), 0);
    }


}
