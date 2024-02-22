package org.rpchatplus;

import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;

public class ChatModeManager {
    private static ChatModeManager instance;
    private final Map<Player, ChatMode> playerChatModes;

    public enum ChatMode {
        NONE, GLOBAL, STAFF, RP, LOCAL
    }

    private ChatModeManager() {
        playerChatModes = new HashMap<>();
    }

    public static synchronized ChatModeManager getInstance() {
        if (instance == null) {
            instance = new ChatModeManager();
        }
        return instance;
    }

    public void setPlayerChatMode(Player player, ChatMode chatMode) {
        playerChatModes.put(player, chatMode);
    }

    public ChatMode getPlayerChatMode(Player player) {
        return playerChatModes.getOrDefault(player, ChatMode.NONE);
    }


    public void ensureGlobalChatMode(Player player) {
        if (!playerChatModes.containsKey(player)) {
            playerChatModes.put(player, ChatMode.GLOBAL);
        }
    }
}