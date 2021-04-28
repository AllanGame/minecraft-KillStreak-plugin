package me.allangame.killstreak.commands;

import me.allangame.killstreak.KillStreak;
import me.allangame.killstreak.streakmanager.Streak;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;


public class StreakCommand implements CommandExecutor {

    KillStreak instance = KillStreak.getInstance();
    Streak streakList = KillStreak.getList();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("killstreak.streak.command")) {
            sender.sendMessage(Objects.requireNonNull(instance.getConfig().getString("config.messages.no_permission")));
            return true;
        }

        if(args.length == 0) {
            if(!(sender instanceof Player)) {
                sender.sendMessage("Sorry console but you can't play :(");
                return true;
            }

            Player player = (Player) sender;
            player.sendMessage(Objects.requireNonNull(instance.getConfig().getString("config.messages.user_streak"))
            .replace("%streak%", streakList.getStreak(player)+""));

        } else {
            if(!sender.hasPermission("killstreak.seeOtherPlayerStreak")) {
                sender.sendMessage(Objects.requireNonNull(instance.getConfig().getString("config.messages.no_permission")));
                return true;
            }
                Player target = Bukkit.getPlayer(args[1]);

                if(target == null) {
                    sender.sendMessage(
                            Objects.requireNonNull(instance.getConfig().getString("config.messages.player_not_found"))
                            .replace("%player%", target.getDisplayName())
                    );
                    return true;
                }

                target.sendMessage(
                        Objects.requireNonNull(instance.getConfig().getString("config.messages.other_user_streak"))
                        .replace("%player%", target.getDisplayName())
                        .replace("%streak%", streakList.getStreak(target)+"")
                );
        }
        return true;
    }
}
