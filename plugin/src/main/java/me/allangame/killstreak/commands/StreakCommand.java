package me.allangame.killstreak.commands;

import me.allangame.killstreak.KillStreak;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Objects;


public class StreakCommand implements CommandExecutor {

    Integer currentStreak;
    Player target;
    HashMap<String, Integer> StreakList = KillStreak.getStreakList();
    KillStreak instance = KillStreak.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (!sender.hasPermission("killstreak.streak.command")) {
            sender.sendMessage(Objects.requireNonNull(instance.getConfig().getString("config.messages.no_permission")));
            return true;
        }

        target = (Player) sender;

        if (args.length == 0) {
            if(!(sender instanceof Player)) {
                sender.sendMessage("Only Players can use this command");
                return true;
            }
            currentStreak = StreakList.putIfAbsent(target.getUniqueId().toString(), 0);
            target.sendMessage(Objects.requireNonNull(instance.getConfig().getString("config.messages.user_streak"))
                    .replace("%streak%", currentStreak + ""));
            return true;
        } else {
            if (sender.hasPermission("killstreak.seeOtherPlayerStreak")) {
                target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    currentStreak = StreakList.putIfAbsent(target.getUniqueId().toString(), 0);
                    sender.sendMessage(Objects.requireNonNull(instance.getConfig().getString("config.messages.other_user_streak"))
                            .replace("%player%", target.getDisplayName())
                            .replace("%streak%", currentStreak + ""));
                    return true;
                } else {
                    sender.sendMessage(Objects.requireNonNull(
                            instance.getConfig().getString("config.messages.player_not_found")
                    ).replace("%player%", target.getDisplayName()
                    ));
                }

            } else {
                sender.sendMessage(Objects.requireNonNull(instance.getConfig().getString("config.messages.no_permission")));
            }
        }

        return true;
    }
}
