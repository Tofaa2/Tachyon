package net.tachyon.block.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.tachyon.block.BlockVariation;

public final class MonsterEgg {
    public static BlockVariation STONE_MONSTER_EGG = new BlockVariation((byte) 0, "Stone Monster Egg");

    public static BlockVariation COBBLESTONE_MONSTER_EGG = new BlockVariation((byte) 1, "Cobblestone Monster Egg");

    public static BlockVariation STONE_BRICK_MONSTER_EGG = new BlockVariation((byte) 2, "Stone Brick Monster Egg");

    public static BlockVariation MOSSY_STONE_BRICK_MONSTER_EGG = new BlockVariation((byte) 3, "Mossy Stone Brick Monster Egg");

    public static BlockVariation CRACKED_STONE_BRICK_MONSTER_EGG = new BlockVariation((byte) 4, "Cracked Stone Brick Monster Egg");

    public static BlockVariation CHISELED_STONE_BRICK_MONSTER_EGG = new BlockVariation((byte) 5, "Chiseled Stone Brick Monster Egg");

    public static List<BlockVariation> variations;

    public static BlockVariation[] variationsArray = new BlockVariation[16];

    static {
        List<BlockVariation> list = new ArrayList<BlockVariation>(6);
        list.add(STONE_MONSTER_EGG);
        variationsArray[0]= STONE_MONSTER_EGG;
        list.add(COBBLESTONE_MONSTER_EGG);
        variationsArray[1]= COBBLESTONE_MONSTER_EGG;
        list.add(STONE_BRICK_MONSTER_EGG);
        variationsArray[2]= STONE_BRICK_MONSTER_EGG;
        list.add(MOSSY_STONE_BRICK_MONSTER_EGG);
        variationsArray[3]= MOSSY_STONE_BRICK_MONSTER_EGG;
        list.add(CRACKED_STONE_BRICK_MONSTER_EGG);
        variationsArray[4]= CRACKED_STONE_BRICK_MONSTER_EGG;
        list.add(CHISELED_STONE_BRICK_MONSTER_EGG);
        variationsArray[5]= CHISELED_STONE_BRICK_MONSTER_EGG;
        variations = Collections.unmodifiableList(list);
    }
}
