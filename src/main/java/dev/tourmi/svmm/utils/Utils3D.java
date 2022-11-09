package dev.tourmi.svmm.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;
import java.util.stream.Stream;

public class Utils3D {
    public static Stream<BlockPos> getSameBlocksConnectedToPos(String blockName, BlockPos pos, Level level) {
        return BlockPos.betweenClosedStream(pos.offset(-1, -1, -1), pos.offset(1, 1, 1))
                .filter(bpos -> !bpos.equals(pos) && MinecraftUtils.getBlockName(level.getBlockState(bpos)).equals(blockName));
    }

    public static Collection<BlockPos> getVeinBlocks(BlockState s, BlockPos p, Level l, int limit) {
        HashSet<BlockPos> visitedBlocks = new HashSet<>();
        HashSet<BlockPos> newPositions = new HashSet<>();
        String blockName = MinecraftUtils.getBlockName(s);
        newPositions.add(p);

        while (!newPositions.isEmpty() && visitedBlocks.size() < limit) {
            BlockPos currPos = newPositions.iterator().next();

            if (visitedBlocks.size() + newPositions.size() < limit) {
                Stream<BlockPos> potentialBlocks = getSameBlocksConnectedToPos(blockName, currPos, l);
                newPositions.addAll(potentialBlocks.filter(pos -> !visitedBlocks.contains(pos)).map(BlockPos::immutable).toList());
            }

            visitedBlocks.add(currPos);
            newPositions.remove(currPos);
        }

        visitedBlocks.remove(p);
        return visitedBlocks;
    }
}
