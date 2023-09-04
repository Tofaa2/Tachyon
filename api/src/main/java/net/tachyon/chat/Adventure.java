package net.tachyon.chat;

import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;

public final class Adventure {
    private Adventure() {
    }

    public static final GsonComponentSerializer COMPONENT_SERIALIZER = GsonComponentSerializer.builder()
                    .downsampleColors()
                    .emitLegacyHoverEvent()
                    .build();
}
