package net.tachyon.chat;

import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public final class Adventure {
    private Adventure() {
    }

    public static final char COLOR_CHAR = '\u00A7';

//    public static final LegacyComponentSerializer COMPONENT_SERIALIZER = LegacyComponentSerializer.builder()
//            .character(COLOR_CHAR)
//            .hexCharacter('#')
//            .build();

    public static final LegacyComponentSerializer COMPONENT_SERIALIZER = LegacyComponentSerializer.builder()
            .extractUrls()
            .build();
//    public static final GsonComponentSerializer COMPONENT_SERIALIZER = GsonComponentSerializer.builder()
//                    .downsampleColors()
//                    .emitLegacyHoverEvent()
//                    .build();
}
