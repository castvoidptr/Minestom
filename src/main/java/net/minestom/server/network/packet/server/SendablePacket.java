package net.minestom.server.network.packet.server;

import net.minestom.server.network.ConnectionState;
import net.minestom.server.network.player.PlayerConnection;
import net.minestom.server.network.player.PlayerSocketConnection;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a packet that can be sent to a {@link PlayerConnection}.
 */
@ApiStatus.Experimental
public sealed interface SendablePacket
        permits CachedPacket, FramedPacket, LazyPacket, ServerPacket {

    @ApiStatus.Experimental
    static @NotNull ServerPacket extractServerPacket(@NotNull ConnectionState state, @NotNull SendablePacket packet, PlayerSocketConnection conn) {
        if (packet instanceof ServerPacket serverPacket) {
            return serverPacket;
        } else if (packet instanceof CachedPacket cachedPacket) {
            return cachedPacket.packet(state, conn);
        } else if (packet instanceof FramedPacket framedPacket) {
            return framedPacket.packet();
        } else if (packet instanceof LazyPacket lazyPacket) {
            return lazyPacket.packet();
        } else {
            throw new RuntimeException("Unknown packet type: " + packet.getClass().getName());
        }
    }
}
