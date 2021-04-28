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

    private final KillStreak instance = KillStreak.getInstance();
    private final Streak streakList = KillStreak.getList();
    private Player target;


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!sender.hasPermission("killstreak.admin")) {
            sender.sendMessage(Objects.requireNonNull(instance.getConfig().getString("config.messages.no_permission")));
            return true;
        }

        String infoMessage = cc("&6==============&lKILL STREAK&6==============\n" +
                "&8- &fhelp: &7Show this message \n" +
                "&8- &freset: &7Reset a player streak \n" +
                "&8- &fset &7Define an specific streak to a player \n" +
                "&bVersion: 1.0.1\n" +
                "&8----------------------------------------");


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

                streakList.reset(target);
                sender.sendMessage(cc("&aSuccesfully. Now &f"+target.getDisplayName()+"'s &astreak is &f0"));

            break;
            case "set":
                if(args.length < 2) {
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
                int newValue;

                try {
                    newValue = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(cc("&cValue must be a number!"));
                    return true;
                }

                int previousValue = streakList.getStreak(target);
                sender.sendMessage(cc("&8&oUpdating the user streak..."));

                if(previousValue == newValue) {
                    sender.sendMessage(cc("&cThe user streak is already "+newValue));
                    return true;
                }

                streakList.set(target, newValue);
                sender.sendMessage(cc("&aSuccessfully! \n&7Changes: &8"+previousValue + " &7--> &f"+newValue));

                if(Objects.equals(instance.getConfig().getString("config.broadcast_when"), "MULTIPLE_OF_5")) {
                    if(newValue == 0) return true;
                    if(newValue % 5 == 0) {
                        Bukkit.broadcastMessage(Objects.requireNonNull(instance.getConfig().getString("config.messages.streak_broadcast"))
                                .replace("%player%", target.getDisplayName())
                                .replace("%streak%", streakList.getStreak(target)+""));
                    }
                }
                break;
            default:
                sender.sendMessage(infoMessage);
                break;
        }

        return true;
    }
}
