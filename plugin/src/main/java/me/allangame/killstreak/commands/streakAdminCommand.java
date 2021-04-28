package me.allangame.killstreak.commands;

import me.allangame.killstreak.KillStreak;
import me.allangame.killstreak.streakmanager.Streak;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

import static me.allangame.killstreak.utils.ChatColorUtils.cc;

public class streakAdminCommand implements CommandExecutor {

    KillStreak instance = KillStreak.getInstance();
    Streak StreakList = KillStreak.getList();
    Player target;

    String infoMessage = cc("&6==============&lKILL STREAK&6==============\n" +
                     "&8- &fhelp: &7Show this message \n" +
                     "&8- &freset: &7Reset a player streak \n" +
                     "&8- &fset &7Define an specific streak to a player \n"+
                     "&bVersion: 1.0.0\n"+
                     "&8----------------------------------------");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!sender.hasPermission("killstreak.admin")) {
            sender.sendMessage(Objects.requireNonNull(instance.getConfig().getString("config.messages.no_permission")));
            return true;
        }

        if(args.length == 0) {
            sender.sendMessage(infoMessage);
            return true;
        }

        switch (args[0]) {
            case "reset":
                if(args.length == 1) {
                    sender.sendMessage(
                            Objects.requireNonNull(instance.getConfig().getString("config.messages.no_user_provided"))
                    );
                    return true;
                }

                target = Bukkit.getPlayer(args[1]);
                if(target == null) {
                    sender.sendMessage(
                            Objects.requireNonNull(instance.getConfig().getString("config.messages.player_not_found"))
                                    .replace("%player%", target.getDisplayName())
                    );
                    return true;
                }

                StreakList.reset(target);

            break;
            case "set":
                if(args.length < 2) {
                    sender.sendMessage(
                            Objects.requireNonNull(instance.getConfig().getString("config.messages.no_user_provided"))
                    );
                    return true;
                }

                target = Bukkit.getPlayer(args[1]);
                int newValue;

                try {
                    newValue = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(cc("&cValue must be a number!"));
                    return true;
                }

                StreakList.set(target, newValue);
                break;
            default:
                sender.sendMessage(infoMessage);
                break;
        }

        return true;
    }
}
