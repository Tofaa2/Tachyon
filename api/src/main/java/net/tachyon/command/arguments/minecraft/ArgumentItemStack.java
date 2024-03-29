package net.tachyon.command.arguments.minecraft;

import net.tachyon.Tachyon;
import net.tachyon.command.arguments.Argument;
import net.tachyon.command.exception.ArgumentSyntaxException;
import net.tachyon.item.ItemStack;
import net.tachyon.item.Material;
import org.jetbrains.annotations.NotNull;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;
import org.jglrxavpok.hephaistos.nbt.NBTException;
import org.jglrxavpok.hephaistos.nbt.SNBTParser;

import java.io.StringReader;

/**
 * Argument which can be used to retrieve an {@link ItemStack} from its material and with NBT data.
 * <p>
 * It is the same type as the one used in the /give command.
 * <p>
 * Example: diamond_sword{display:{Name:"{\"text\":\"Sword of Power\"}"}}
 */
public class ArgumentItemStack extends Argument<ItemStack> {

    public static final int NO_MATERIAL = 1;
    public static final int INVALID_NBT = 2;

    public ArgumentItemStack(String id) {
        super(id, true);
    }

    @NotNull
    @Override
    public ItemStack parse(@NotNull String input) throws ArgumentSyntaxException {
        final int nbtIndex = input.indexOf("{");

        if (nbtIndex == 0)
            throw new ArgumentSyntaxException("The item needs a material", input, NO_MATERIAL);

        if (nbtIndex == -1) {
            // Only item name
            final Material material = Tachyon.getUnsafe().getMaterial(input);
            return ItemStack.of(material, (byte) 1);
        } else {
            final String materialName = input.substring(0, nbtIndex);
            final Material material = Tachyon.getUnsafe().getMaterial(materialName);
            final String sNBT = input.substring(nbtIndex).replace("\\\"", "\"");

            NBTCompound compound;
            try {
                compound = (NBTCompound) new SNBTParser(new StringReader(sNBT)).parse();
            } catch (NBTException e) {
                throw new ArgumentSyntaxException("Item NBT is invalid", input, INVALID_NBT);
            }

            return ItemStack.fromNBT(material, compound);
        }
    }

}
