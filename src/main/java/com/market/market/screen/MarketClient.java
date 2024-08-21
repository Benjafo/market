package com.market.market.screen;

import com.market.market.Market;
import net.minecraft.client.gui.screen.ingame.Generic3x3ContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

import net.fabricmc.api.ClientModInitializer;

public class MarketClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		HandledScreens.register(Market.BAG_SCREEN_HANDLER, Generic3x3ContainerScreen::new);
		HandledScreens.register(Market.MARKET_BLOCK_SCREEN_HANDLER, MarketScreen::new);
	}
}
