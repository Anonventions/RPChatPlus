package org.rpchatplus.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.rpchatplus.ChatModeManager;

public class GlobalChat implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        ChatModeManager chatModeManager = ChatModeManager.getInstance();

        if (chatModeManager.getPlayerChatMode(player).equals(ChatModeManager.ChatMode.GLOBAL)) {
            chatModeManager.setPlayerChatMode(player, ChatModeManager.ChatMode.NONE);
            player.sendMessage(ChatColor.YELLOW + "Global chat mode disabled.");
        } else {
            chatModeManager.setPlayerChatMode(player, ChatModeManager.ChatMode.GLOBAL);
            player.sendMessage(ChatColor.GREEN + "Global chat mode enabled. All messages will now be sent globally.");
        }

        String prefix = ChatColor.translateAlternateColorCodes('&', player.getServer().getPluginManager().getPlugin("RPChatPlus").getConfig().getString("chat.global.prefix"));
        String style = ChatColor.translateAlternateColorCodes('&', player.getServer().getPluginManager().getPlugin("RPChatPlus").getConfig().getString("chat.global.style"));
        String formattedMessage = prefix + style + player.getDisplayName() + ": " + String.join(" ", args);

        if (chatModeManager.getPlayerChatMode(player).equals(ChatModeManager.ChatMode.GLOBAL)) {
            Bukkit.getServer().broadcastMessage(formattedMessage);
        }

        return true;
    }
}
