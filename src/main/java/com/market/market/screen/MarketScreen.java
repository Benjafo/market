package com.market.market.screen;

import java.util.Optional;

import com.market.market.item.ItemAppraiser;
import com.market.market.item.Items;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.gui.widget.ButtonWidget;

public class MarketScreen extends HandledScreen<MarketScreenHandler> {
    private static final Identifier TEXTURE = Identifier.ofVanilla("textures/gui/container/dispenser.png");

    public ButtonWidget submitButton;

    public MarketScreen(MarketScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, getPositionText(handler).orElse(title));
    }

    private static Optional<Text> getPositionText(ScreenHandler handler) {
        if (handler instanceof MarketScreenHandler) {
            BlockPos pos = ((MarketScreenHandler) handler).getPos();
            return pos != null ? Optional.of(Text.literal("(" + pos.toShortString() + ")")) : Optional.empty();
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void render(DrawContext drawContext, int mouseX, int mouseY, float delta) {
        renderBackground(drawContext, mouseX, mouseY, delta);
        super.render(drawContext, mouseX, mouseY, delta);
        drawMouseoverTooltip(drawContext, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        // Center the title
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;

        // Add submit functionality
        drawButton();
    }

    private void drawButton() {
        // Create button
        submitButton = ButtonWidget.builder(Text.translatable("client.submit_button"), button -> submit())
                .dimensions(width / 2, 20, 200, 20)
                .tooltip(Tooltip.of(Text.translatable("client.submit_button.tooltip")))
                .build();

        // Add to screen
        addDrawableChild(submitButton);
    }

    private void submit() {
        MarketScreenHandler handler = this.getScreenHandler();

        // Loop through all slots in the table
        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = handler.getSlot(i).getStack();
            if (!itemStack.isEmpty()) {
                // Sell the item
                exchangeItem(handler, itemStack);
                System.out.println("Slot " + i + ": " + itemStack.getCount() + "x " + itemStack.getItem().getName().getString());
            }
        }
    }

    private void exchangeItem(MarketScreenHandler handler, ItemStack item) {
        // Ensure item is eligible to sell
        if (ItemAppraiser.itemIsUnsellable(item)) {
            return;
        }

        // Generate the value of the item
        Double value = Math.round(ItemAppraiser.calculateValue(item) * 100.0) / 100.0;

        // Define coin types and their values
        Item[] coinTypes = {Items.DOLLAR, Items.QUARTER, Items.DIME, Items.NICKEL, Items.PENNY};
        double[] coinValues = {1.0, 0.25, 0.1, 0.05, 0.01};

        // Calculate coin counts
        int[] coinCounts = new int[coinTypes.length];
        double remainingValue = value;

        for (int i = 0; i < coinTypes.length; i++) {
            coinCounts[i] = (int) (remainingValue / coinValues[i]);
            remainingValue -= coinCounts[i] * coinValues[i];
        }

        // Give coins to player
//        PlayerInventory inventory = handler.getInventory();
//        PlayerEntity player = inventory.player;
//        measureChange(player, inventory, coinTypes, coinCounts);

        // Remove exchanged item from player's inventory and display message to player
        Text message = Text.translatable("market.exchange_message", item.getName(), String.format("%.2f", value));
        item.decrement(1);
//        player.sendMessage(message);
    }

    private static void measureChange(PlayerEntity player, PlayerInventory inventory, Item[] coinTypes, int[] coinCounts) {
        // Determine
        for (int i = 0; i < coinTypes.length; i++) {
            if (coinCounts[i] > 0) {
                ItemStack coinStack = new ItemStack(coinTypes[i], coinCounts[i]);
                if (!inventory.insertStack(coinStack)) {
                    ItemStack leftoverStack = coinStack.copy();
                    inventory.offer(leftoverStack, false);
                    if (!leftoverStack.isEmpty()) {
                        player.dropItem(leftoverStack, false);
                    }
                }
            }
        }
    }

    @Override
    protected void drawBackground(DrawContext drawContext, float delta, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawContext.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
    }
}