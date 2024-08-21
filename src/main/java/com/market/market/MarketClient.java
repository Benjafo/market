package com.market.market;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

@Environment(EnvType.CLIENT)
public class MarketClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(Market.MARKET_SCREEN_HANDLER, MarketScreen::new);
    }
}