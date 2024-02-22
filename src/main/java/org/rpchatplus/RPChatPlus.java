package org.rpchatplus;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.rpchatplus.chat.GlobalChat;
import org.rpchatplus.chat.StaffChat;
import org.rpchatplus.chat.RPChat;
import org.rpchatplus.chat.LocalChat;

public final class RPChatPlus extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new ChatEventListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinEventListener(), this);

        this.getCommand("global").setExecutor(new GlobalChat());
        this.getCommand("staff").setExecutor(new StaffChat());
        this.getCommand("rp").setExecutor(new RPChat(this)); // Passing 'this' for RPChat
        this.getCommand("local").setExecutor(new LocalChat(this));
        this.getCommand("rpc").setExecutor((sender, command, label, args) -> {
            if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("rpchatplus.reload")) {
                    sender.sendMessage(ChatColor.RED + "You do not have permission to perform this command.");
                    return true;
                }

                reloadConfig();
                sender.sendMessage(ChatColor.GREEN + "Configuration reloaded.");
                return true;
            }
            sender.sendMessage(ChatColor.YELLOW + "Usage: /rpc <subcommand>");
            return true;
        });
    }

    @Override
    public void onDisable() {
        getLogger().info("RPChatPlus has been disabled.");
    }
}