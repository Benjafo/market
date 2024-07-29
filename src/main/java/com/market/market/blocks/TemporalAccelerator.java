package com.market.market.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TemporalAccelerator extends Block {
    public TemporalAccelerator(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient) {
            // On client side, just return success
            return ActionResult.SUCCESS;
        }

        // Do something
        player.sendMessage(Text.literal("The Temporal Accelerator does nothing."));

        return ActionResult.PASS;
    }
}
