package net.tachyon.item.attribute;

import net.tachyon.attribute.Attribute;
import net.tachyon.attribute.AttributeOperation;

import java.util.UUID;

public class ItemAttribute {

    private final UUID uuid;
    private final String internalName;
    private final Attribute attribute;
    private final AttributeOperation operation;
    private final double value;

    public ItemAttribute(UUID uuid, String internalName, Attribute attribute, AttributeOperation operation, double value) {
        this.uuid = uuid;
        this.internalName = internalName;
        this.attribute = attribute;
        this.operation = operation;
        this.value = value;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getInternalName() {
        return internalName;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public AttributeOperation getOperation() {
        return operation;
    }

    public double getValue() {
        return value;
    }
}
