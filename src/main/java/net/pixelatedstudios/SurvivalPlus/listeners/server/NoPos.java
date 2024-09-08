package net.pixelatedstudios.SurvivalPlus.listeners.server;


import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import net.kyori.adventure.text.format.NamedTextColor;
import net.pixelatedstudios.SurvivalPlus.Survival;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class NoPos implements Listener {
    /**
     * Disable a player's coordinates in their Minecraft Debug screen
     *
     * @param player The player to disable coords for
     */
    @SuppressWarnings("WeakerAccess")
    public static void disableF3(Player player) {
        try {
            PacketContainer container = new PacketContainer(PacketType.Play.Server.ENTITY_STATUS);
            container.getIntegers().write(0, player.getEntityId());
            container.getBytes().write(0, (byte)22);
            Survival.getInstance().getProtocolManager().sendServerPacket(player, container);
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("[SurvivalPlus] " + NamedTextColor.RED + e.getMessage());
        }
    }

    /**
     * Enable a player's coordinates in their Minecraft Debug screen
     *
     * @param player The player to enable coords for
     */
    @SuppressWarnings("WeakerAccess")
    public static void enableF3(Player player) {
        try {
            PacketContainer container = new PacketContainer(PacketType.Play.Server.ENTITY_STATUS);
            container.getIntegers().write(0, player.getEntityId());
            container.getBytes().write(0, (byte)23);
            Survival.getInstance().getProtocolManager().sendServerPacket(player, container);
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("[SurvivalPlus] " + NamedTextColor.RED + e.getMessage());
        }
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
            disableF3(player);
    }

    @EventHandler
    private void onGamemodeChange(PlayerGameModeChangeEvent e) {
        Player player = e.getPlayer();
        if (e.getNewGameMode() == GameMode.ADVENTURE || e.getNewGameMode() == GameMode.SURVIVAL) {
            disableF3(player);
        } else {
            enableF3(player);
        }
    }

    @EventHandler
    private void onWorldChange(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        if (player.getGameMode() == GameMode.ADVENTURE || player.getGameMode() == GameMode.SURVIVAL) {
            disableF3(player);
        }
    }
}
