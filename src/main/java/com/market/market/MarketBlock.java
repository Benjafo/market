package com.market.market;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MarketBlock extends Block {
    public MarketBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    private boolean exchangeItem(ItemStack heldItem, PlayerEntity player) {
        // Ensure item is eligible to sell
        if (ItemAppraiser.itemIsUnsellable(heldItem)) {
            return false;
        }

        // Generate the value of the item
        Integer value = ItemAppraiser.calculateValue(heldItem);
        ItemStack coinStack = new ItemStack(Items.END_ENERGY_FRAGMENT, value);

        // Try to insert the coins into the player's inventory
        if (!player.getInventory().insertStack(coinStack)) {
            // Add any leftover coins to a separate item stack
            ItemStack leftoverStack = coinStack.copy();

            // Move all coins that can't fit in the inventory to the ground
            player.getInventory().offer(leftoverStack, false);
            if (!leftoverStack.isEmpty()) {
                player.dropItem(leftoverStack, false);
            }
        }

        // Remove exchanged item from player's inventory
        heldItem.decrement(1);

        // Display message to player
        Text message = Text.translatable("market.exchange_message", heldItem.getName(), value,
                Text.translatable(value == 1 ? "market.exchange_message.singular" : "market.exchange_message.plural"));
        player.sendMessage(message);

        return true;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS; // On client side, just return success
        }

        // Check if the player is holding an item
        ItemStack heldItem = player.getMainHandStack();

        if (!heldItem.isEmpty()) {
            // Exchange item and update inventory
            boolean exchangeSuccessful = exchangeItem(heldItem, player);

            // Return success only if the exchange was successful
            return exchangeSuccessful ? ActionResult.SUCCESS : ActionResult.PASS;
        }

        return ActionResult.PASS;
    }
}
