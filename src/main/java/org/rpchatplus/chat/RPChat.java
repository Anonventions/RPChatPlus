package org.rpchatplus.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.rpchatplus.ChatModeManager;
import org.rpchatplus.RPChatPlus;

public class RPChat implements CommandExecutor {
    private final RPChatPlus plugin;

    public RPChat(RPChatPlus plugin) {
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

        if (chatModeManager.getPlayerChatMode(player).equals(ChatModeManager.ChatMode.RP)) {
            chatModeManager.setPlayerChatMode(player, ChatModeManager.ChatMode.NONE);
            player.sendMessage(ChatColor.RED + "Roleplay chat mode disabled.");
        } else {
            chatModeManager.setPlayerChatMode(player, ChatModeManager.ChatMode.RP);
            player.sendMessage(ChatColor.GREEN + "Roleplay chat mode enabled. All messages will now be visible only to roleplay chat participants.");
        }

        return true;
    }

    public static String formatRPMessage(String message, FileConfiguration config) {
        String actionColor = ChatColor.translateAlternateColorCodes('&', config.getString("chat.rp.action_color"));
        String messageColor = ChatColor.translateAlternateColorCodes('&', config.getString("chat.rp.message_color"));

        StringBuilder formattedMessage = new StringBuilder();
        boolean isActionPart = false;

        for (String part : message.split("(?<=\\*)|(?=\\*)")) {
            if (part.equals("*")) {
                isActionPart = !isActionPart;
            } else {
                if (isActionPart) {
                    formattedMessage.append(actionColor).append(part).append(ChatColor.RESET);
                } else {
                    formattedMessage.append(messageColor).append("‘").append(part).append("’ ").append(ChatColor.RESET);
                }
            }
        }

        return formattedMessage.toString();
    }
}
