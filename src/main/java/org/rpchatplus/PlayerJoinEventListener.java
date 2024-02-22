package org.rpchatplus;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinEventListener implements Listener {
    private final ChatModeManager chatModeManager;

    public PlayerJoinEventListener() {
        this.chatModeManager = ChatModeManager.getInstance();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        chatModeManager.ensureGlobalChatMode(event.getPlayer());
    }
}