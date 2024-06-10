package net.minestom.server.item;

import net.minestom.server.component.DataComponentMap;
import net.minestom.server.entity.Player;

public interface IComponentable {
    public DataComponentMap components();
    public DataComponentMap components(Player player);
}
