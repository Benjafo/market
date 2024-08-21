package com.market.market;

import com.market.market.block.BoxBlock;
import com.market.market.block.BoxBlockEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import com.market.market.item.BagItem;
import com.market.market.screen.BagScreenHandler;
import com.market.market.screen.BoxScreenHandler;

public class ScreenHandlerTest implements ModInitializer {
	public static final String ID = "fabric-screen-handler-api-v1-testmod";

	public static final Item BAG = new BagItem(new Item.Settings().maxCount(1));
	public static final Block BOX = new BoxBlock(AbstractBlock.Settings.copy(Blocks.OAK_WOOD));
	public static final Item BOX_ITEM = new BlockItem(BOX, new Item.Settings());
	public static final BlockEntityType<BoxBlockEntity> BOX_ENTITY = FabricBlockEntityTypeBuilder.create(BoxBlockEntity::new, BOX).build();
	public static final ScreenHandlerType<BagScreenHandler> BAG_SCREEN_HANDLER = new ScreenHandlerType<>(BagScreenHandler::new, FeatureFlags.VANILLA_FEATURES);
	public static final ScreenHandlerType<BoxScreenHandler> BOX_SCREEN_HANDLER = new ExtendedScreenHandlerType<>(BoxScreenHandler::new, BlockPos.PACKET_CODEC.cast());

	public static Identifier id(String path) {
		return Identifier.of(ID, path);
	}

	@Override
	public void onInitialize() {
		Registry.register(Registries.ITEM, id("bag"), BAG);
		Registry.register(Registries.BLOCK, id("box"), BOX);
		Registry.register(Registries.ITEM, id("box"), BOX_ITEM);
		Registry.register(Registries.BLOCK_ENTITY_TYPE, id("box"), BOX_ENTITY);
		Registry.register(Registries.SCREEN_HANDLER, id("bag"), BAG_SCREEN_HANDLER);
		Registry.register(Registries.SCREEN_HANDLER, id("box"), BOX_SCREEN_HANDLER);
	}
}
