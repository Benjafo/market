package com.market.market.screen;

import java.util.Optional;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.tooltip.Tooltip;
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
        submitButton = ButtonWidget.builder(Text.translatable("client.submit.button"), button -> submit())
                .dimensions(width / 2, 20, 200, 20)
                .tooltip(Tooltip.of(Text.translatable("client.submit.button.tooltip")))
                .build();

        // Add to screen
        addDrawableChild(submitButton);
    }

    private void submit() {
        MarketScreenHandler handler = this.getScreenHandler();

        // Loop through all items in the table
        for (int i = 0; i < 9; i++) {
            ItemStack itemStack = handler.getSlot(i).getStack();
            if (!itemStack.isEmpty()) {
                // Sell each item in the interface
                Item item = itemStack.getItem();
                int count = itemStack.getCount();
                // You can now use 'item' and 'count' as needed
                System.out.println("Slot " + i + ": " + count + "x " + item.getName().getString());
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