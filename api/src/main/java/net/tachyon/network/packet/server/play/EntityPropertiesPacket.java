package net.tachyon.network.packet.server.play;

import net.tachyon.attribute.Attribute;
import net.tachyon.attribute.AttributeInstance;
import net.tachyon.attribute.AttributeModifier;
import net.tachyon.binary.BinaryWriter;
import net.tachyon.network.packet.server.ServerPacket;
import net.tachyon.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public record EntityPropertiesPacket(int entityId, Property[] properties) implements ServerPacket {

    @Override
    public void write(@NotNull BinaryWriter writer) {
        writer.writeVarInt(entityId);
        writer.writeInt(properties.length);
        for (Property property : properties) {
            property.write(writer);
        }
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.ENTITY_PROPERTIES;
    }

    public static class Property {

        public Attribute attribute;
        public double value;
        public AttributeInstance instance;

        private void write(BinaryWriter writer) {
            float maxValue = attribute.getMaxValue();

            // Bypass vanilla limit client-side if needed (by sending the max value allowed)
            final double v = value > maxValue ? maxValue : value;

            writer.writeSizedString(attribute.getKey());
            writer.writeDouble(v);

            {
                Collection<AttributeModifier> modifiers = instance.getModifiers();
                writer.writeVarInt(modifiers.size());

                for (var modifier : modifiers) {
                    writer.writeUuid(modifier.getId());
                    writer.writeDouble(modifier.getAmount());
                    writer.writeByte((byte) modifier.getOperation().getId());
                }
            }
        }
    }

}
