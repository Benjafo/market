package com.market.market.item;

import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ItemAppraiser {
    public static final Logger LOGGER = LoggerFactory.getLogger("market");

    private static final Map<Item, Integer> BASE_ITEM_VALUES;
    private static final Map<RegistryKey<Enchantment>, List<Double>> ENCHANTMENT_VALUES;

    static {
        // Static dictionary of base item values
        Map<Item, Integer> baseItemValues = new HashMap<>();
        baseItemValues.put(Items.DIAMOND_SWORD, 8);
        baseItemValues.put(Items.DIAMOND_PICKAXE, 8);
        baseItemValues.put(Items.DIAMOND_SHOVEL, 8);
        baseItemValues.put(Items.DIAMOND_HELMET, 7);
        baseItemValues.put(Items.DIAMOND_CHESTPLATE, 7);
        baseItemValues.put(Items.DIAMOND_LEGGINGS, 7);
        baseItemValues.put(Items.DIAMOND_BOOTS, 7);
        baseItemValues.put(Items.IRON_SWORD, 5);
        baseItemValues.put(Items.IRON_PICKAXE, 5);
        baseItemValues.put(Items.IRON_SHOVEL, 5);
        baseItemValues.put(Items.IRON_HELMET, 4);
        baseItemValues.put(Items.IRON_CHESTPLATE, 4);
        baseItemValues.put(Items.IRON_LEGGINGS, 4);
        baseItemValues.put(Items.IRON_BOOTS, 4);
        BASE_ITEM_VALUES = Collections.unmodifiableMap(baseItemValues);

        // Static dictionary of enchantment values
        Map<RegistryKey<Enchantment>, List<Double>> enchantmentItemValues = new HashMap<>();
        // General
        enchantmentItemValues.put(Enchantments.MENDING, List.of(1.5));
        enchantmentItemValues.put(Enchantments.UNBREAKING, List.of(1.1, 1.2, 1.3));
        // Sword
        enchantmentItemValues.put(Enchantments.SHARPNESS, List.of(1.1, 1.2, 1.3, 1.4, 1.5));
        enchantmentItemValues.put(Enchantments.SMITE, List.of(1.05, 1.1, 1.15, 1.2, 1.25));
        enchantmentItemValues.put(Enchantments.SWEEPING_EDGE, List.of(1.05, 1.1, 1.15));
        enchantmentItemValues.put(Enchantments.BANE_OF_ARTHROPODS, List.of(1.03, 1.06, 1.09, 1.12, 1.15));
        enchantmentItemValues.put(Enchantments.LOOTING, List.of(1.2, 1.3, 1.4));
        enchantmentItemValues.put(Enchantments.FIRE_ASPECT, List.of(1.15, 1.25));
        enchantmentItemValues.put(Enchantments.KNOCKBACK, List.of(1.05, 1.1));
        // Pickaxe/Shovel
        enchantmentItemValues.put(Enchantments.EFFICIENCY, List.of(1.1, 1.2, 1.3, 1.4, 1.5));
        enchantmentItemValues.put(Enchantments.SILK_TOUCH, List.of(1.3));
        enchantmentItemValues.put(Enchantments.FORTUNE, List.of(1.2, 1.3, 1.4));
        // Armor
        enchantmentItemValues.put(Enchantments.BLAST_PROTECTION, List.of(1.1, 1.15, 1.2, 1.25));
        enchantmentItemValues.put(Enchantments.FIRE_PROTECTION, List.of(1.1, 1.15, 1.2, 1.25));
        enchantmentItemValues.put(Enchantments.PROJECTILE_PROTECTION, List.of(1.1, 1.15, 1.2, 1.25));
        enchantmentItemValues.put(Enchantments.PROTECTION, List.of(1.15, 1.25, 1.35, 1.45));
        enchantmentItemValues.put(Enchantments.THORNS, List.of(1.1, 1.15, 1.2));
        // Helmet
        enchantmentItemValues.put(Enchantments.AQUA_AFFINITY, List.of(1.15));
        enchantmentItemValues.put(Enchantments.RESPIRATION, List.of(1.1, 1.2, 1.3));
        // Leggings
        enchantmentItemValues.put(Enchantments.SWIFT_SNEAK, List.of(1.2, 1.3, 1.4));
        // Boots
        enchantmentItemValues.put(Enchantments.DEPTH_STRIDER, List.of(1.15, 1.25, 1.35));
        enchantmentItemValues.put(Enchantments.FEATHER_FALLING, List.of(1.1, 1.2, 1.3, 1.4));
        enchantmentItemValues.put(Enchantments.FROST_WALKER, List.of(1.2, 1.3));
        enchantmentItemValues.put(Enchantments.SOUL_SPEED, List.of(1.15, 1.25, 1.35));

        ENCHANTMENT_VALUES = Collections.unmodifiableMap(enchantmentItemValues);
    }

    public static boolean itemIsUnsellable(ItemStack heldItem) {
        // Item is unsellable if it does not have an associated value with it
        Item item = heldItem.getItem();
        Integer value = BASE_ITEM_VALUES.get(item);
        return value == null;
    }

    public static Double calculateValue(ItemStack item) {
        // Calculate and return the value of the item stack's item
        Double itemValue = 0.0;

        // Base item value
        itemValue += BASE_ITEM_VALUES.get(item.getItem());
        LOGGER.info("Base item value: {}", itemValue);

        // Enchantments
        ItemEnchantmentsComponent enchantmentsComponent = EnchantmentHelper.getEnchantments(item);
        Set<RegistryEntry<Enchantment>> enchantmentSet = enchantmentsComponent.getEnchantments();
        for (RegistryEntry<Enchantment> enchantment : enchantmentSet) {
            // Get enchantment level
            int enchantmentLevel = EnchantmentHelper.getLevel(enchantment, item);
            System.out.println("Item " + item.getItem().getName() + " has enchantment level " + enchantmentLevel
                    + " " + enchantment.value().toString());

            // Calculate enchantment value from list
            Double enchantmentValue = enchantment.getKey()
                    .map(ENCHANTMENT_VALUES::get)
                    .map(list -> list.get(enchantmentLevel - 1))
                    .orElse(1.0);

            // Apply value multiplier
            LOGGER.info("  -Applying multiplier: {} * {} = {}", itemValue, enchantmentValue, itemValue * enchantmentValue);
            itemValue *= enchantmentValue;
        }

        return itemValue;
    }
}