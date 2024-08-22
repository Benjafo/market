package com.market.market.item;

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

public class Items {
    public static final Logger LOGGER = LoggerFactory.getLogger("market");

    public static final RegistryKey<ItemGroup> MARKET_COMPONENTS_KEY = RegistryKey.of(
            Registries.ITEM_GROUP.getKey(), Identifier.of(Market.ID, "market_components"));

    public static final ItemGroup MARKET_COMPONENTS = FabricItemGroup.builder()
            .icon(() -> new ItemStack(Items.DOLLAR))
            .displayName(Text.translatable("itemGroup.market_components"))
            .build();

    public static final Item DOLLAR = register( new Coin(),"dollar" );
    public static final Item QUARTER = register( new Coin(),"quarter" );
    public static final Item DIME = register( new Coin(),"dime" );
    public static final Item NICKEL = register( new Coin(),"nickel" );
    public static final Item PENNY = register( new Coin(),"penny" );

    public static Item register(Item item, String id) {
        // Create the identifier for the item.
        Identifier itemID = Identifier.of(Market.ID, id);

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
                    itemGroup.add(Items.DOLLAR);
                    itemGroup.add(Items.QUARTER);
                    itemGroup.add(Items.DIME);
                    itemGroup.add(Items.NICKEL);
                    itemGroup.add(Items.PENNY);
                });


    }
}