package com.market.market.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

final class BagInventory implements ImplementedInventory {
	private final ItemStack stack;
	private final DefaultedList<ItemStack> items = DefaultedList.ofSize(9, ItemStack.EMPTY);

	BagInventory(ItemStack stack) {
		this.stack = stack;
		ContainerComponent container = stack.get(DataComponentTypes.CONTAINER);

		if (container != null) container.copyTo(items);
	}

	@Override
	public DefaultedList<ItemStack> getItems() {
		return items;
	}

	@Override
	public void markDirty() {
		stack.set(DataComponentTypes.CONTAINER, ContainerComponent.fromStacks(items));
	}
}
