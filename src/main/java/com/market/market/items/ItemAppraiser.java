package com.market.market.items;

import com.google.common.collect.ImmutableMap;
import com.market.market.blocks.Blocks;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registry;

import java.util.*;

public class ItemAppraiser {

    private static final Map<Item, Integer> ITEM_VALUES;

    static {
        // Create and store static dictionary of items and associated values
        Map<Item, Integer> itemValues = new HashMap<>();
        itemValues.put(Items.DIAMOND_SWORD, 10);
        itemValues.put(Items.DIAMOND_PICKAXE, 10);
        itemValues.put(Items.DIAMOND_SHOVEL, 10);
        itemValues.put(Items.DIAMOND_HELMET, 10);
        itemValues.put(Items.DIAMOND_CHESTPLATE, 10);
        itemValues.put(Items.DIAMOND_LEGGINGS, 10);
        itemValues.put(Items.DIAMOND_BOOTS, 10);
        itemValues.put(Items.IRON_SWORD, 5);
        itemValues.put(Items.IRON_PICKAXE, 5);
        itemValues.put(Items.IRON_SHOVEL, 5);
        itemValues.put(Items.IRON_HELMET, 5);
        itemValues.put(Items.IRON_CHESTPLATE, 5);
        itemValues.put(Items.IRON_LEGGINGS, 5);
        itemValues.put(Items.IRON_BOOTS, 5);
        ITEM_VALUES = Collections.unmodifiableMap(itemValues);
    }

    public static boolean itemIsUnsellable(ItemStack heldItem) {
        // Item is unsellable if it does not have an associated value with it
        Item item = heldItem.getItem();
        Integer value = ITEM_VALUES.get(item);
        return value == null;
    }

    public static Integer calculateValue(ItemStack item) {
        // Simulate a random item
        return new Random().nextInt(10) + 1;
    }
}
