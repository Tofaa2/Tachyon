package net.tachyon.entity.type;

import net.tachyon.namespace.NamespaceID;
import org.jetbrains.annotations.NotNull;

public interface EntityType {


    @NotNull String getName();

    @NotNull NamespaceID getNamespace();

}
