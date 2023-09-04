package net.tachyon.entity.metadata.animal;

import net.tachyon.entity.metadata.AgeableMobMeta;
import org.jetbrains.annotations.NotNull;

public interface HorseMeta extends AgeableMobMeta {

    boolean isTamed();

    void setTamed(boolean value);

    boolean isSaddled();

    void setSaddled(boolean value);

    boolean isHasChest();

    void setHasChest(boolean value);

    boolean isHasBred();

    void setHasBred(boolean value);

    boolean isEating();

    void setEating(boolean value);

    boolean isRearing();

    void setRearing(boolean value);

    boolean isMouthOpen();

    void setMouthOpen(boolean value);

    @NotNull Type getType();

    void setType(@NotNull Type type);

    Variant getVariant();

    void setVariant(@NotNull Variant variant);

    Armor getArmor();

    void setArmor(@NotNull Armor armor);

    enum Type {
        HORSE,
        DONKEY,
        MULE,
        ZOMBIE,
        SKELETON;

        protected final static Type[] VALUES = values();
    }

    class Variant {

        protected Marking marking;
        protected Color color;

        public Variant(@NotNull Marking marking, @NotNull Color color) {
            this.marking = marking;
            this.color = color;
        }

        @NotNull
        public Marking getMarking() {
            return this.marking;
        }

        public void setMarking(@NotNull Marking marking) {
            this.marking = marking;
        }

        @NotNull
        public Color getColor() {
            return this.color;
        }

        public void setColor(@NotNull Color color) {
            this.color = color;
        }

    }

    enum Marking {
        NONE,
        WHITE,
        WHITE_FIELD,
        WHITE_DOTS,
        BLACK_DOTS;

        protected final static Marking[] VALUES = values();
    }

    enum Color {
        WHITE,
        CREAMY,
        CHESTNUT,
        BROWN,
        BLACK,
        GRAY,
        DARK_BROWN;

        protected final static Color[] VALUES = values();
    }

    enum Armor {
        NONE,
        IRON,
        GOLD,
        DIAMOND;

        protected final static Armor[] VALUES = values();
    }

}
