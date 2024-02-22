package org.rpchatplus.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.rpchatplus.ChatModeManager;
import org.rpchatplus.RPChatPlus;

public class LocalChat implements CommandExecutor {
    private RPChatPlus plugin;

    public LocalChat(RPChatPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        ChatModeManager chatModeManager = ChatModeManager.getInstance();

        int localChatRadius = plugin.getConfig().getInt("chat.local.radius", 100);

        if (chatModeManager.getPlayerChatMode(player).equals(ChatModeManager.ChatMode.LOCAL)) {
            chatModeManager.setPlayerChatMode(player, ChatModeManager.ChatMode.NONE);
            player.sendMessage(ChatColor.YELLOW + "Local chat mode disabled.");
        } else {
            chatModeManager.setPlayerChatMode(player, ChatModeManager.ChatMode.LOCAL);
            player.sendMessage(ChatColor.GREEN + "Local chat mode enabled. All messages will now be sent to nearby players.");
        }

        return true;
    }
}
