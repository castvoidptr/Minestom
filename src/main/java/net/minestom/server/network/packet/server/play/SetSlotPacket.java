package net.minestom.server.network.packet.server.play;

import net.kyori.adventure.text.Component;
import net.minestom.server.item.ItemComponent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.network.NetworkBuffer;
import net.minestom.server.network.packet.server.ServerPacket;
import net.minestom.server.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.UnaryOperator;

import static net.minestom.server.network.NetworkBuffer.*;

public record SetSlotPacket(byte windowId, int stateId, short slot,
                            @NotNull ItemStack itemStack) implements ServerPacket.Play, ServerPacket.ComponentHolding {
    public SetSlotPacket(@NotNull NetworkBuffer reader) {
        this(reader.read(BYTE), reader.read(VAR_INT), reader.read(SHORT),
                reader.read(ItemStack.NETWORK_TYPE));
    }

    @Override
    public void write(@NotNull NetworkBuffer writer) {
        writer.write(BYTE, windowId);
        writer.write(VAR_INT, stateId);
        writer.write(SHORT, slot);
        writer.write(ItemStack.NETWORK_TYPE, itemStack);
    }

    @Override
    public int playId() {
        return ServerPacketIdentifier.SET_SLOT;
    }

    @Override
    public @NotNull Collection<Component> components() {
        final var components = new ArrayList<>(this.itemStack.get(ItemComponent.LORE, List.of()));
        final var displayName = this.itemStack.get(ItemComponent.CUSTOM_NAME);
        if (displayName != null) components.add(displayName);
        final var itemName = this.itemStack.get(ItemComponent.ITEM_NAME);
        if (itemName != null) components.add(itemName);
        return List.copyOf(components);
    }

    @Override
    public @NotNull ServerPacket copyWithOperator(@NotNull UnaryOperator<Component> operator) {
        /*return new SetSlotPacket(this.windowId, this.stateId, this.slot, this.itemStack
                .with(ItemComponent.CUSTOM_NAME, operator)
                .with(ItemComponent.ITEM_NAME, operator)
                .with(ItemComponent.LORE, (UnaryOperator<List<Component>>) lines -> {
                    final var translatedComponents = new ArrayList<Component>();
                    lines.forEach(component -> translatedComponents.add(operator.apply(component)));
                    return translatedComponents;
                }));*/

        return new SetSlotPacket(this.windowId, this.stateId, this.slot, this.itemStack);
    }

    /**
     * Returns a {@link SetSlotPacket} used to change a player cursor item.
     *
     * @param cursorItem the cursor item
     * @return a set slot packet to change a player cursor item
     */
    public static @NotNull SetSlotPacket createCursorPacket(@NotNull ItemStack cursorItem) {
        return new SetSlotPacket((byte) -1, 0, (short) -1, cursorItem);
    }
}
