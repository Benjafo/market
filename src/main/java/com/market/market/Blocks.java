package com.market.market;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Blocks {
    public static final Logger LOGGER = LoggerFactory.getLogger("market");

    public static final Block MARKET_BLOCK = register(
            new MarketBlock(AbstractBlock.Settings.create()
                    .sounds(BlockSoundGroup.ANVIL)
                    .strength(3.0f, 3.0f)
            ),
            "market_block",
            true
    );

    public static Block register(Block block, String name, boolean shouldRegisterItem) {
        // Register the block and its item.
        Identifier id = Identifier.of(Market.MOD_ID, name);

        // Sometimes, you may not want to register an item for the block.
        // Eg: if it's a technical block like `minecraft:air` or `minecraft:end_gateway`
        if (shouldRegisterItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Settings());
            Registry.register(Registries.ITEM, id, blockItem);
        }

        return Registry.register(Registries.BLOCK, id, block);
    }

    // Method to statically initialize class
    public static void initialize() {
        LOGGER.info("Registering blocks...");

        // Add items to item groups to show in creative GUI
        ItemGroupEvents.modifyEntriesEvent(Items.MARKET_COMPONENTS_KEY).register(
                (itemGroup -> itemGroup.add(Blocks.MARKET_BLOCK.asItem()))
        );
    }
}
