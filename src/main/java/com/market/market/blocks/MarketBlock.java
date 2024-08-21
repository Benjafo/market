package com.market.market.blocks;

import com.market.market.items.ItemAppraiser;
import com.market.market.items.Items;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MarketBlock extends BlockWithEntity {
    public MarketBlock() {
        super(AbstractBlock.Settings.create()
                .sounds(BlockSoundGroup.ANVIL)
                .strength(3.0f, 3.0f)
        );
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MarketBlockEntity(pos, state);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        //With inheriting from BlockWithEntity this defaults to INVISIBLE, so we need to change that!
        return BlockRenderType.MODEL;
    }

    private boolean exchangeItem(ItemStack heldItem, PlayerEntity player) {
        // Ensure item is eligible to sell
        if (ItemAppraiser.itemIsUnsellable(heldItem)) {
            return false;
        }

        // Generate the value of the item
        Double value = Math.round(ItemAppraiser.calculateValue(heldItem) * 100.0) / 100.0;

        // Define coin types and their values
        Item[] coinTypes = {Items.DOLLAR, Items.QUARTER, Items.DIME, Items.NICKEL, Items.PENNY};
        double[] coinValues = {1.0, 0.25, 0.1, 0.05, 0.01};

        // Calculate coin counts
        int[] coinCounts = new int[coinTypes.length];
        double remainingValue = value;

        for (int i = 0; i < coinTypes.length; i++) {
            coinCounts[i] = (int) (remainingValue / coinValues[i]);
            remainingValue -= coinCounts[i] * coinValues[i];
        }

        // Give coins to player
        for (int i = 0; i < coinTypes.length; i++) {
            if (coinCounts[i] > 0) {
                ItemStack coinStack = new ItemStack(coinTypes[i], coinCounts[i]);
                if (!player.getInventory().insertStack(coinStack)) {
                    ItemStack leftoverStack = coinStack.copy();
                    player.getInventory().offer(leftoverStack, false);
                    if (!leftoverStack.isEmpty()) {
                        player.dropItem(leftoverStack, false);
                    }
                }
            }
        }

        // Remove exchanged item from player's inventory and display message to player
        Text message = Text.translatable("market.exchange_message", heldItem.getName(), String.format("%.2f", value));
        heldItem.decrement(1);
        player.sendMessage(message);

        return true;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient) {
            // On client side, just return success
            return ActionResult.SUCCESS;
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
