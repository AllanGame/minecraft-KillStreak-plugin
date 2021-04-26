package me.allangame.killstreak.utils;

import org.bukkit.ChatColor;

public class ChatColorUtils {
    public static String cc(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
}
