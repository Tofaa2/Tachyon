package net.tachyon.extras;

import net.tachyon.Tachyon;
import net.tachyon.block.Block;
import net.tachyon.block.BlockManager;
import net.tachyon.block.rule.vanilla.AxisPlacementRule;
import net.tachyon.block.rule.vanilla.RedstonePlacementRule;

public final class PlacementRules {

	public static void init() {
		BlockManager blockManager = Tachyon.getServer().getBlockmanager();
		blockManager.registerBlockPlacementRule(new RedstonePlacementRule());
		blockManager.registerBlockPlacementRule(new AxisPlacementRule(Block.HAY_BLOCK));
		blockManager.registerBlockPlacementRule(new AxisPlacementRule(Block.LOG));
		blockManager.registerBlockPlacementRule(new AxisPlacementRule(Block.LOG2));
	}
}
