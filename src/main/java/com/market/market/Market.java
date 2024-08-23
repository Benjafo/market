package com.market.market;

import com.market.market.block.MarketBlock;
import com.market.market.block.MarketBlockEntity;
import com.market.market.item.ItemAppraiser;
import com.market.market.screen.MarketExchangePacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import com.market.market.item.BagItem;
import com.market.market.screen.BagScreenHandler;
import com.market.market.screen.MarketScreenHandler;

public class Market implements ModInitializer {
	public static final String ID = "market";

	public static final Item BAG = new BagItem(new Item.Settings().maxCount(1));
	public static final Block MARKET_BLOCK = new MarketBlock(AbstractBlock.Settings.copy(Blocks.OAK_WOOD));
	public static final Item MARKET_BLOCK_ITEM = new BlockItem(MARKET_BLOCK, new Item.Settings());
	public static final BlockEntityType<MarketBlockEntity> MARKET_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(MarketBlockEntity::new, MARKET_BLOCK).build();
	public static final ScreenHandlerType<BagScreenHandler> BAG_SCREEN_HANDLER = new ScreenHandlerType<>(BagScreenHandler::new, FeatureFlags.VANILLA_FEATURES);
	public static final ScreenHandlerType<MarketScreenHandler> MARKET_BLOCK_SCREEN_HANDLER = new ExtendedScreenHandlerType<>(MarketScreenHandler::new, BlockPos.PACKET_CODEC.cast());

	public static Identifier id(String path) {
		return Identifier.of(ID, path);
	}

	@Override
	public void onInitialize() {
		Registry.register(Registries.ITEM, id("bag"), BAG);
		Registry.register(Registries.BLOCK, id("market_block"), MARKET_BLOCK);
		Registry.register(Registries.ITEM, id("market_block"), MARKET_BLOCK_ITEM);
		Registry.register(Registries.BLOCK_ENTITY_TYPE, id("market_block"), MARKET_BLOCK_ENTITY);
		Registry.register(Registries.SCREEN_HANDLER, id("bag"), BAG_SCREEN_HANDLER);
		Registry.register(Registries.SCREEN_HANDLER, id("market_block"), MARKET_BLOCK_SCREEN_HANDLER);

		// Server to client exchange
//		MarketExchangePacket packet = new Ma
//		MinecraftClient.getInstance().getNetworkHandler().sendPacket()
	}

	private void handleMarketExchange(ServerPlayerEntity player, MarketExchangePacket packet) {
		MarketScreenHandler handler = (MarketScreenHandler) player.currentScreenHandler;
		if (handler == null) return;

		for (int slotIndex : packet.getSlotIndices()) {
			ItemStack itemStack = handler.getSlot(slotIndex).getStack();
			if (!itemStack.isEmpty() && !ItemAppraiser.itemIsUnsellable(itemStack)) {
				double value = Math.round(ItemAppraiser.calculateValue(itemStack) * 100.0) / 100.0;

				// Perform the exchange logic here
				// This includes removing the item, giving coins, and sending messages

				// Example:
				itemStack.decrement(1);
//				giveCoinsToPlayer(player, value);

				Text message = Text.translatable("market.exchange_message", itemStack.getName(), String.format("%.2f", value));
				player.sendMessage(message, false);
			}
		}
	}
}
