package de.leonkoth.blockparty.listener;

import de.leonkoth.blockparty.BlockParty;
import de.leonkoth.blockparty.arena.Arena;
import de.leonkoth.blockparty.event.PlayerLeaveArenaEvent;
import de.leonkoth.blockparty.locale.Locale;
import de.leonkoth.blockparty.locale.Messenger;
import de.leonkoth.blockparty.player.PlayerInfo;
import de.leonkoth.blockparty.player.PlayerState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PlayerLeaveArenaListener implements Listener {

    private BlockParty blockParty;

    public PlayerLeaveArenaListener(BlockParty blockParty) {
        this.blockParty = blockParty;

        Bukkit.getPluginManager().registerEvents(this, blockParty.getPlugin());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLeaveArena(PlayerLeaveArenaEvent event) {
        Arena arena = event.getArena();
        Player player = event.getPlayer();
        PlayerInfo playerInfo = event.getPlayerInfo();

        playerInfo.setPlayerState(PlayerState.DEFAULT);
        playerInfo.setCurrentArena(null);

        if (playerInfo.getPlayerData() != null) {
            playerInfo.getPlayerData().apply(player);
            playerInfo.setPlayerData(null);
        }

        arena.broadcast(true, Locale.PLAYER_LEFT_GAME, false, playerInfo, "%PLAYER%", player.getName());
        Messenger.message(true, player, Locale.LEFT_GAME, "%ARENA%", arena.getName());
        arena.getPlayersInArena().remove(playerInfo);
    }

}
