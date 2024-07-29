package com.market.market;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ItemAppraiser {

    private static final Random RANDOM = new Random();

    private static final List<Item> unsellableItems = Arrays.asList(
            Items.END_ENERGY_SHARD,
            Items.END_ENERGY_CRYSTAL
        );

    private static final List<Block> unsellableBlocks = Arrays.asList(
            Blocks.MARKET_BLOCK
    );

    public static boolean itemIsUnsellable(Object obj) {
        // Check if item is "sellable"
        // Unsellable items:
        //    - End energy shards/crystals
        //    - Market blocks
        //    - TODO: netherite tools/armor
        //    - TODO: tools/armor players have previously used

        if (obj instanceof ItemStack itemStack) {
            Item item = itemStack.getItem();
            return unsellableItems.contains(item) ||
                    (item instanceof BlockItem blockItem && unsellableBlocks.contains(blockItem.getBlock()));
        }

        return false;
    }

    public static Integer calculateValue(ItemStack item) {
        // Simulate a random item
        return RANDOM.nextInt(10) + 1;
    }
}
