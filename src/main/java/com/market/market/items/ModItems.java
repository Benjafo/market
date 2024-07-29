package com.market.market.items;

import com.market.market.Market;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModItems {
    public static final Logger LOGGER = LoggerFactory.getLogger("market");

    public static final RegistryKey<ItemGroup> MARKET_COMPONENTS_KEY = RegistryKey.of(
            Registries.ITEM_GROUP.getKey(), Identifier.of(Market.MOD_ID, "market_components"));

    public static final ItemGroup MARKET_COMPONENTS = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.END_ENERGY_CRYSTAL))
            .displayName(Text.translatable("market_components"))
            .build();

    public static final Item END_ENERGY_FRAGMENT = register(
            new Item(new Item.Settings().maxCount(99)),
            "end_energy_fragment"
    );

    public static final Item END_ENERGY_SHARD = register(
            new Item(new Item.Settings().maxCount(99)),
            "end_energy_shard"
    );

    public static final Item END_ENERGY_CRYSTAL = register(
            new Item(new Item.Settings().maxCount(99)),
            "end_energy_crystal"
    );

    public static final Item COMPACTED_END_ENERGY = register(
            new Item(new Item.Settings()),
            "compacted_end_energy"
    );

    public static Item register(Item item, String id) {
        // Create the identifier for the item.
        Identifier itemID = Identifier.of(Market.MOD_ID, id);

        // Register and return the item.
        return Registry.register(Registries.ITEM, itemID, item);
    }

    // Method to statically initialize class
    public static void initialize() {
        LOGGER.info("Registering items...");

        // Add items to item groups to show in creative GUI
        Registry.register(Registries.ITEM_GROUP, MARKET_COMPONENTS_KEY, MARKET_COMPONENTS);
        ItemGroupEvents.modifyEntriesEvent(MARKET_COMPONENTS_KEY)
                .register((itemGroup) -> {
                    itemGroup.add(ModItems.END_ENERGY_FRAGMENT);
                    itemGroup.add(ModItems.END_ENERGY_SHARD);
                    itemGroup.add(ModItems.END_ENERGY_CRYSTAL);
                    itemGroup.add(ModItems.COMPACTED_END_ENERGY);
                });


    }
}
