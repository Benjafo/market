package com.market.market.screen;

import com.market.market.Market;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.Generic3x3ContainerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public class MarketScreenHandler extends Generic3x3ContainerScreenHandler implements IMarketScreenHandler {
	private final BlockPos pos;
	private final PlayerInventory inventory;

	public MarketScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos pos) {
		super(syncId, playerInventory);
		this.pos = pos;
		this.inventory = playerInventory;
	}

	public MarketScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
		super(syncId, playerInventory, inventory);
		this.pos = BlockPos.ORIGIN;
		this.inventory = playerInventory;
	}

	public PlayerInventory getInventory() {
		return inventory;
	}

	public int getInventorySize() {
		return inventory.size();
	}

	@Override
	public Slot getSlot(int index) {
		return this.slots.get(index);
	}

	@Override
	public BlockPos getPos() {
		return pos;
	}

	@Override
	public ScreenHandlerType<?> getType() {
		return Market.MARKET_BLOCK_SCREEN_HANDLER;
	}
}
