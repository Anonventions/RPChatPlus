package org.rpchatplus.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.rpchatplus.ChatModeManager;

public class StaffChat implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("rpchatplus.staffchat")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use staff chat.");
            return true;
        }

        ChatModeManager chatModeManager = ChatModeManager.getInstance();

        if (chatModeManager.getPlayerChatMode(player).equals(ChatModeManager.ChatMode.STAFF)) {
            chatModeManager.setPlayerChatMode(player, ChatModeManager.ChatMode.NONE);
            player.sendMessage(ChatColor.YELLOW + "Staff chat mode disabled.");
        } else {
            chatModeManager.setPlayerChatMode(player, ChatModeManager.ChatMode.STAFF);
            player.sendMessage(ChatColor.GREEN + "Staff chat mode enabled. All messages will now be sent in staff chat.");
        }

        return true;
    }
}
