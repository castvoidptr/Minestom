package net.minestom.server.network.packet.client.play;

import net.minestom.server.item.ItemStack;
import net.minestom.server.network.NetworkBuffer;
import net.minestom.server.network.packet.client.ClientPacket;
import org.jetbrains.annotations.NotNull;

import static net.minestom.server.network.NetworkBuffer.SHORT;

/*public record ClientCreativeInventoryActionPacket(short slot, @NotNull ItemStack item) implements ClientPacket {
    public ClientCreativeInventoryActionPacket(@NotNull NetworkBuffer reader) {
        this(reader.read(SHORT), reader.read(ItemStack.NETWORK_TYPE));
    }

    @Override
    public void write(@NotNull NetworkBuffer writer) {
        writer.write(SHORT, slot);
        writer.write(ItemStack.NETWORK_TYPE, item);
    }
}*/

// Modified: 8/6/2024
// Reason:
//   So that you can change the item that is provided, allowing you to overwrite it.

public class ClientCreativeInventoryActionPacket implements ClientPacket
{
    private short slot;
    private ItemStack item;
    public ClientCreativeInventoryActionPacket(short slot, @NotNull ItemStack item)
    {
        this.slot = slot;
        this.item = item;
    }

    public ClientCreativeInventoryActionPacket(@NotNull NetworkBuffer reader)
    {
        this(reader.read(SHORT), reader.read(ItemStack.NETWORK_TYPE));
    }

    @Override
    public void write(@NotNull NetworkBuffer writer)
    {
        writer.write(SHORT, slot);
        writer.write(ItemStack.NETWORK_TYPE, item);
    }

    public ItemStack item()
    {
        return item;
    }

    public short slot()
    {
        return slot;
    }

    public void setItem(ItemStack item)
    {
        this.item = item;
    }

    public void setSlot(short slot)
    {
        this.slot = slot;
    }
}