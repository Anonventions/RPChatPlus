package org.rpchatplus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.rpchatplus.chat.RPChat;

public class ChatEventListener implements Listener {

    private final RPChatPlus plugin;

    public ChatEventListener(RPChatPlus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String rpPrefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("chat.rp.prefix"));
        String rpMessage = RPChat.formatRPMessage(event.getMessage(), plugin.getConfig());
        String formattedMessage = rpPrefix + rpMessage;
        boolean isRpEnabled = plugin.getConfig().getBoolean("chat.rp.enabled", true);
        int rpChatRadius = plugin.getConfig().getInt("chat.rp.radius", 50);
        Player player = event.getPlayer();
        String message = event.getMessage();
        ChatModeManager chatModeManager = ChatModeManager.getInstance();
        ChatModeManager.ChatMode chatMode = chatModeManager.getPlayerChatMode(player);

        event.setCancelled(true);

        switch (chatMode) {
            case GLOBAL:
                String globalFormat = plugin.getConfig().getString("chat.global.prefix") + plugin.getConfig().getString("chat.global.style") + player.getDisplayName() + ": " + message;
                Bukkit.getOnlinePlayers().stream()
                        .filter(p -> chatModeManager.getPlayerChatMode(p) != ChatModeManager.ChatMode.RP)
                        .forEach(p -> p.sendMessage(ChatColor.translateAlternateColorCodes('&', globalFormat)));

                break;
            case STAFF:
                String staffFormat = plugin.getConfig().getString("chat.staff.prefix") + plugin.getConfig().getString("chat.staff.style") + player.getDisplayName() + ": " + message;
                Bukkit.getOnlinePlayers().stream()
                        .filter(p -> p.hasPermission("rpchatplus.staffchat"))
                        .forEach(p -> p.sendMessage(ChatColor.translateAlternateColorCodes('&', staffFormat)));
                break;
            case RP:
                String rpFormat = plugin.getConfig().getString("chat.rp.prefix") + player.getDisplayName() + ": " + rpMessage;
                rpMessage = RPChat.formatRPMessage(message, plugin.getConfig());
                Bukkit.getOnlinePlayers().stream()
                        .filter(p -> chatModeManager.getPlayerChatMode(p) == ChatModeManager.ChatMode.RP)
                        .forEach(p -> p.sendMessage(ChatColor.translateAlternateColorCodes('&', rpFormat)));
                break;
            case LOCAL:
                final int CHAT_RADIUS = 100;
                String localFormat = plugin.getConfig().getString("chat.local.prefix") + plugin.getConfig().getString("chat.local.style") + player.getDisplayName() + ": " + message;
                player.getWorld().getPlayers().stream()
                        .filter(p -> p.getLocation().distanceSquared(player.getLocation()) <= CHAT_RADIUS * CHAT_RADIUS && chatModeManager.getPlayerChatMode(p) != ChatModeManager.ChatMode.RP)
                        .forEach(p -> p.sendMessage(ChatColor.translateAlternateColorCodes('&', localFormat)));
                break;
            default:
                Bukkit.getOnlinePlayers().stream()
                        .filter(p -> chatModeManager.getPlayerChatMode(p) != ChatModeManager.ChatMode.RP)
                        .forEach(p -> p.sendMessage(player.getDisplayName() + ": " + message));
                break;
        }
    }
}