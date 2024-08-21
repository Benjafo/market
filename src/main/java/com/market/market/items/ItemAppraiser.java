package com.market.market.items;

import com.google.common.collect.ImmutableMap;
import com.market.market.blocks.Blocks;
import net.minecraft.block.Block;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.*;

public class ItemAppraiser {

    private static final Map<Item, Integer> BASE_ITEM_VALUES;
    private static final Map<Enchantment, Integer> ENCHANTMENT_VALUES;

    static {
        // Static dictionary of base item values
        Map<Item, Integer> baseItemValues = new HashMap<>();
        baseItemValues.put(Items.DIAMOND_SWORD, 10);
        baseItemValues.put(Items.DIAMOND_PICKAXE, 10);
        baseItemValues.put(Items.DIAMOND_SHOVEL, 10);
        baseItemValues.put(Items.DIAMOND_HELMET, 10);
        baseItemValues.put(Items.DIAMOND_CHESTPLATE, 10);
        baseItemValues.put(Items.DIAMOND_LEGGINGS, 10);
        baseItemValues.put(Items.DIAMOND_BOOTS, 10);
        baseItemValues.put(Items.IRON_SWORD, 5);
        baseItemValues.put(Items.IRON_PICKAXE, 5);
        baseItemValues.put(Items.IRON_SHOVEL, 5);
        baseItemValues.put(Items.IRON_HELMET, 5);
        baseItemValues.put(Items.IRON_CHESTPLATE, 5);
        baseItemValues.put(Items.IRON_LEGGINGS, 5);
        baseItemValues.put(Items.IRON_BOOTS, 5);
        BASE_ITEM_VALUES = Collections.unmodifiableMap(baseItemValues);

        // Static dictionary of enchantment values
        Map<Enchantment, Integer> enchantmentItemValues = new HashMap<>();
//        enchantmentItemValues.put(MendingEnchantment, 15);
        ENCHANTMENT_VALUES = Collections.unmodifiableMap(enchantmentItemValues);

    }

    public static boolean itemIsUnsellable(ItemStack heldItem) {
        // Item is unsellable if it does not have an associated value with it
        Item item = heldItem.getItem();
        Integer value = BASE_ITEM_VALUES.get(item);
        return value == null;
    }

    public static Integer calculateValue(ItemStack item) {
        // Calculate and return the value of the item stack's item
        Integer itemValue = 0;

        // Base item value
        itemValue += BASE_ITEM_VALUES.get(item.getItem());

        // Enchantments
        ItemEnchantmentsComponent enchantments = item.getEnchantments();
        for (RegistryEntry<Enchantment> registryEntry: enchantments.getEnchantments()) {
            Enchantment enchantment = registryEntry.value();
            // ???
        }

        return BASE_ITEM_VALUES.get(item.getItem());
    }
}
