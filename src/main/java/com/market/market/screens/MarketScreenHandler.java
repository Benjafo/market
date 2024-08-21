package com.market.market.screens;

import com.market.market.blocks.Blocks;
import com.market.market.items.MarketItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.Generic3x3ContainerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.SlotActionType;

public class MarketScreenHandler extends Generic3x3ContainerScreenHandler {
    private final ScreenHandlerType<?> type;

    public MarketScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(9));
    }

    public MarketScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        this(Blocks.MARKET_SCREEN_HANDLER, syncId, playerInventory, inventory);
    }

    protected MarketScreenHandler(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(syncId, playerInventory, inventory);
        this.type = type;
    }

    @Override
    public ScreenHandlerType<?> getType() {
        return type;
    }

    @Override
    public void onSlotClick(int slotId, int clickData, SlotActionType actionType, PlayerEntity playerEntity) {
        if (slotId >= 0) { // slotId < 0 are used for networking internals
            ItemStack stack = getSlot(slotId).getStack();

        }

        super.onSlotClick(slotId, clickData, actionType, playerEntity);
    }
}