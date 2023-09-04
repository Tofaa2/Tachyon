package net.tachyon.block.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.tachyon.block.BlockVariation;

public final class SnowLayer {
    public static BlockVariation ONE_LAYER_2_PIXELS_THICK = new BlockVariation((byte) 0, "One layer, 2 pixels thick");

    public static BlockVariation TWO_LAYERS_4_PIXELS_THICK = new BlockVariation((byte) 1, "Two layers, 4 pixels thick");

    public static BlockVariation THREE_LAYERS_6_PIXELS_THICK = new BlockVariation((byte) 2, "Three layers, 6 pixels thick");

    public static BlockVariation FOUR_LAYERS_8_PIXELS_THICK = new BlockVariation((byte) 3, "Four layers, 8 pixels thick");

    public static BlockVariation FIVE_LAYERS_10_PIXELS_THICK = new BlockVariation((byte) 4, "Five layers, 10 pixels thick");

    public static BlockVariation SIX_LAYERS_12_PIXELS_THICK = new BlockVariation((byte) 5, "Six layers, 12 pixels thick");

    public static BlockVariation SEVEN_LAYERS_14_PIXELS_THICK = new BlockVariation((byte) 6, "Seven layers, 14 pixels thick");

    public static BlockVariation EIGHT_LAYERS_16_PIXELS_THICK = new BlockVariation((byte) 7, "Eight layers, 16 pixels thick");

    public static List<BlockVariation> variations;

    public static BlockVariation[] variationsArray = new BlockVariation[16];

    static {
        List<BlockVariation> list = new ArrayList<BlockVariation>(8);
        list.add(ONE_LAYER_2_PIXELS_THICK);
        variationsArray[0]= ONE_LAYER_2_PIXELS_THICK;
        list.add(TWO_LAYERS_4_PIXELS_THICK);
        variationsArray[1]= TWO_LAYERS_4_PIXELS_THICK;
        list.add(THREE_LAYERS_6_PIXELS_THICK);
        variationsArray[2]= THREE_LAYERS_6_PIXELS_THICK;
        list.add(FOUR_LAYERS_8_PIXELS_THICK);
        variationsArray[3]= FOUR_LAYERS_8_PIXELS_THICK;
        list.add(FIVE_LAYERS_10_PIXELS_THICK);
        variationsArray[4]= FIVE_LAYERS_10_PIXELS_THICK;
        list.add(SIX_LAYERS_12_PIXELS_THICK);
        variationsArray[5]= SIX_LAYERS_12_PIXELS_THICK;
        list.add(SEVEN_LAYERS_14_PIXELS_THICK);
        variationsArray[6]= SEVEN_LAYERS_14_PIXELS_THICK;
        list.add(EIGHT_LAYERS_16_PIXELS_THICK);
        variationsArray[7]= EIGHT_LAYERS_16_PIXELS_THICK;
        variations = Collections.unmodifiableList(list);
    }
}
